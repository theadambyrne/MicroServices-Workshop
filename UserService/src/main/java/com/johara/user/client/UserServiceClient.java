package com.johara.user.client;

import com.johara.user.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceClient {
    private static final String USER_SERVICE_NAME = "user-service";
    private static final String USERS_ENDPOINT = "/users";
    private static final String USER_ID_PARAM = "/{id}";

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = new RestTemplate();
    }

    public UserDTO createUser(UserDTO userDTO) {
        ServiceInstance userInstance = getServiceInstance();
        String userUrl = userInstance.getUri().toString() + USERS_ENDPOINT;
        ResponseEntity<UserDTO> response = restTemplate.postForEntity(userUrl, userDTO, UserDTO.class);
        return response.getBody();
    }

    public UserDTO getUserById(Long userId) {
        ServiceInstance userInstance = getServiceInstance();
        String userUrl = userInstance.getUri().toString() + USERS_ENDPOINT + USER_ID_PARAM;
        ResponseEntity<UserDTO> response = restTemplate.getForEntity(userUrl, UserDTO.class, userId);
        return response.getBody();
    }

    public UserDTO updateUser(UserDTO userDTO) {
        ServiceInstance userInstance = getServiceInstance();
        String userUrl = userInstance.getUri().toString() + USERS_ENDPOINT + USER_ID_PARAM;
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(userDTO);
        ResponseEntity<UserDTO> response = restTemplate.exchange(userUrl, HttpMethod.PUT, requestEntity,
                UserDTO.class, userDTO.getId());
        return response.getBody();
    }

    public void deleteUser(Long userId) {
        ServiceInstance userInstance = getServiceInstance();
        String userUrl = userInstance.getUri().toString() + USERS_ENDPOINT + USER_ID_PARAM;
        restTemplate.delete(userUrl, userId);
    }

    private ServiceInstance getServiceInstance() {
        return discoveryClient.getInstances(USER_SERVICE_NAME)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User Service not found"));
    }
}
