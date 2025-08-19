package com.eprcuen.userservice.services.impl;

import com.eprcuen.commons.exceptions.BadRequestException;
import com.eprcuen.commons.helpers.ValidationHelper;
import com.eprcuen.commons.logs.WriteLog;
import com.eprcuen.commons.models.HighMsg;
import com.eprcuen.userservice.api.models.requests.UserRequest;
import com.eprcuen.userservice.api.models.responses.UserResponse;
import com.eprcuen.userservice.persistence.entities.UserApp;
import com.eprcuen.userservice.persistence.entities.ValidationToken;
import com.eprcuen.userservice.persistence.repositories.UserRepository;
import com.eprcuen.userservice.persistence.repositories.ValidationTokenRepository;
import com.eprcuen.userservice.services.contracts.UserService;
import com.eprcuen.userservice.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the UserService interface.
 * This service provides methods to manage users, including creating a user and retrieving all users.
 * It uses a reactive repository for non-blocking data access.
 *
 * @author eprcuen
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidationTokenRepository validationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, Object> userTemplate;

    /**
     * Creates a new user in the repository and maps it to a UserResponse DTO.
     *
     * @param request the UserRequest containing user details
     * @return a Mono of UserResponse containing the created user
     */
    @Override
    @Transactional
    public Mono<UserResponse> createUser(UserRequest request) {
        log.info(WriteLog.logInfo("--> creating user service"));
        this.validateUser(request);
        var user = UserMapper.mapTOEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Mono<UserApp> userNew =  userRepository.save(user)
                .doOnSuccess(nu -> {
                    log.info(WriteLog.logInfo("--> user created successfully with ID: " + nu.getId()));
                    var token = this.generateValidationToken(user.getEmail());

                    CompletableFuture<SendResult<String, Object>> future = userTemplate.send("user-topic",
                            HighMsg.builder()
                                    .email(user.getEmail())
                                    .username(user.getUsername())
                                    .validationToken(token.getToken())
                                    .build());
                    future.whenCompleteAsync((result, throwable) -> {
                        if (throwable != null) {
                            log.error(WriteLog.logError("--> Error sending message to broker: " + throwable.getMessage()));
                        } else {
                            log.info(WriteLog.logInfo("--> Message sent to broker successfully"));
                        }
                });

        });
        return userNew
                .map(UserMapper::mapToDto)
                .doOnError(err -> log.error(WriteLog.logError("Error creating user: " + err.getMessage())))
                .flatMap(userResponse -> {
                    ValidationToken token = generateValidationToken(user.getEmail());
                    return validationTokenRepository.save(token)
                            .thenReturn(userResponse);
                });
    }

    /**
     * Retrieves all users from the repository and maps them to UserResponse DTOs.
     *
     * @return a Flux of UserResponse containing all users
     */
    @Override
    @Transactional(readOnly = true)
    public Flux<List<UserResponse>> getAllUsers() {
        log.info(WriteLog.logInfo("--> retrieving all users service"));
        return userRepository.findAll()
                .map(UserMapper::mapToDto)
                .collectList()
                .flux()
                .doOnError(err -> log.error(WriteLog.logError("Error retrieving users: " + err.getMessage())));
    }

    /**
     * Validates the user request data.
     * Checks for required fields, email format, password strength, and uniqueness of email.
     *
     * @param request the UserRequest containing user details to validate
     * @throws BadRequestException if any validation errors are found
     */
    @Transactional(readOnly = true)
    private void validateUser(UserRequest request){
        log.info(WriteLog.logInfo("--> validating user..."));
        List<String> errors = new ArrayList<>();
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            errors.add("Username is required");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            errors.add("Email is required");
        } else if (!ValidationHelper.validateEmail(request.getEmail())) {
            errors.add("Email format is invalid");
        }
       userRepository.existsByEmail(request.getEmail())
               .flatMap(exists -> {
                   if (exists) {
                       errors.add("Email is already in use");
                   }
                   return Mono.empty();
               });


        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            errors.add("Password is required");
        } else if (!request.getPassword().equals(request.getConfirmPassword())) {
            errors.add("Password and Confirm Password do not match");
        } else if (!ValidationHelper.validatePassword(request.getPassword())) {
            errors.add("Password must be at least 8 characters long, contain at least one uppercase letter, " +
                    "one lowercase letter, one digit, and one special character");

        }
        if(!errors.isEmpty()){
            log.error(WriteLog.logError("Validation errors: " + errors));
            throw new BadRequestException(errors);
        }
    }

    /**
     * Generates a validation token for the user.
     * The token is a UUID string and has an expiry date set to 1 day from now.
     *
     * @param email the email of the user for whom the token is generated
     * @return a ValidationToken object containing the token and expiry date
     */
    private ValidationToken generateValidationToken(String email) {
        log.info(WriteLog.logInfo("--> Generating validation Token..."));
        return ValidationToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusDays(1)) // Token valid for 1 day
                .email(email)
                .build();
    }

    private void  respaldo(){
       /* Mono<UserApp> userNew =  userRepository.save(user)
                .doOnSuccess(nu -> {
                    log.info(WriteLog.logInfo("--> user created successfully with ID: " + nu.getId()));
                    var token = this.generateValidationToken(user.getEmail());

                    CompletableFuture<SendResult<String, Object>> future = userTemplate.send("user-topic",
                            HighMsg.builder()
                                    .email(user.getEmail())
                                    .username(user.getUsername())
                                    .validationToken(token.getToken())
                                    .build());
                    future.whenCompleteAsync((result, throwable) -> {
                        if (throwable != null) {
                            log.error(WriteLog.logError("--> Error sending message to broker: " + throwable.getMessage()));
                        } else {
                            log.info(WriteLog.logInfo("--> Message sent to broker successfully"));
                        }
                    });

                });*/
    }
}
