package com.eprcuen.userservice.utils.mappers;

import com.eprcuen.userservice.api.models.requests.UserRequest;
import com.eprcuen.userservice.api.models.responses.UserResponse;
import com.eprcuen.userservice.persistence.entities.UserApp;

/**
 * UserMapper class for converting between UserRequest, UserResponse, and UserApp entities.
 * Provides static methods to map data transfer objects to entity objects and vice versa.
 *
 * @author caito
 *
 */
public class UserMapper {

    /**
     * Maps a UserRequest object to a UserApp entity.
     *
     * @param request the UserRequest object to map
     * @return a UserApp entity with the mapped fields
     */
    public static UserApp mapTOEntity(UserRequest request){
        return UserApp.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .password(request.getPassword())
                .role("ROLE_USER") // Default role, can be changed as needed
                .build();
    }

    /**
     * Maps a UserApp entity to a UserResponse object.
     *
     * @param user the UserApp entity to map
     * @return a UserResponse object with the mapped fields
     */
    public static UserResponse mapToDto(UserApp user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .role(user.getRole())
                .build();
    }
}
