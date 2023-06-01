package com.johara.email.client;

import com.johara.email.model.UserDTO;
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

    public UserDTO createUser(UserDTO orderDTO) {
        ServiceInstance orderInstance = getServiceInstance();
        String orderUrl = orderInstance.getUri().toString() + USERS_ENDPOINT;
        ResponseEntity<UserDTO> response = restTemplate.postForEntity(orderUrl, orderDTO, UserDTO.class);
        return response.getBody();
    }

    public UserDTO getUserById(Long orderId) {
        ServiceInstance orderInstance = getServiceInstance();
        String orderUrl = orderInstance.getUri().toString() + USERS_ENDPOINT + USER_ID_PARAM;
        ResponseEntity<UserDTO> response = restTemplate.getForEntity(orderUrl, UserDTO.class, orderId);
        return response.getBody();
    }

    public UserDTO updateUser(UserDTO orderDTO) {
        ServiceInstance orderInstance = getServiceInstance();
        String orderUrl = orderInstance.getUri().toString() + USERS_ENDPOINT + USER_ID_PARAM;
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(orderDTO);
        ResponseEntity<UserDTO> response = restTemplate.exchange(orderUrl, HttpMethod.PUT, requestEntity,
                UserDTO.class, orderDTO.getId());
        return response.getBody();
    }

    public void deleteUser(Long orderId) {
        ServiceInstance orderInstance = getServiceInstance();
        String orderUrl = orderInstance.getUri().toString() + USERS_ENDPOINT + USER_ID_PARAM;
        restTemplate.delete(orderUrl, orderId);
    }

    private ServiceInstance getServiceInstance() {
        return discoveryClient.getInstances(USER_SERVICE_NAME)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("user Service not found"));
    }
}