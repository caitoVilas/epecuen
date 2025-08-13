package com.eprcuen.userservice.services.impl;

import com.eprcuen.commons.exceptions.BadRequestException;
import com.eprcuen.commons.helpers.ValidationHelper;
import com.eprcuen.commons.logs.WriteLog;
import com.eprcuen.userservice.api.models.requests.UserRequest;
import com.eprcuen.userservice.api.models.responses.UserResponse;
import com.eprcuen.userservice.persistence.repositories.UserRepository;
import com.eprcuen.userservice.services.contracts.UserService;
import com.eprcuen.userservice.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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
        return userRepository.save(user)
                .doOnSuccess(nu -> log.info(WriteLog.logInfo("User created with ID: " + nu.getId())))
                .map(UserMapper::mapToDto);
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

    private void validateUser(UserRequest request){
        log.info(WriteLog.logInfo("--> validating user..."));
        List<String> errors = new ArrayList<>();
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            errors.add("Username is required");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            errors.add("Email is required");
        }else if(Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()).block())){
            errors.add("Email already exists");
        } else if (!ValidationHelper.validateEmail(request.getEmail())) {
            errors.add("Email format is invalid");
        }
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
}
