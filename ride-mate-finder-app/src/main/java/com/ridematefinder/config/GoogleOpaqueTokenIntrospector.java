package com.ridematefinder.config;

import com.ridematefinder.controllers.PrivateController;
import com.ridematefinder.dtos.UserInfo;
import com.ridematefinder.service.UserService;
import com.ridematefinder.sql.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GoogleOpaqueTokenIntrospector  implements OpaqueTokenIntrospector {

    private final WebClient userInfoClient;

    private UserService userService;

    Logger logger = LoggerFactory.getLogger(PrivateController.class);

    public GoogleOpaqueTokenIntrospector(WebClient userInfoClient, UserService userService) {
        this.userInfoClient = userInfoClient;
        this.userService = userService;
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        UserInfo user = userInfoClient.get()
                .uri(uriBuilder -> uriBuilder.path("/oauth2/v3/userinfo").queryParam("access_token", token).build())
                .retrieve()
                .bodyToMono(UserInfo.class)
                .block();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", user.sub());
        attributes.put("name", user.name());
        attributes.put("email", user.email());
        logger.info(user.email());
        logger.info(user.name());
        User newUser = new User();
        newUser.setId(UUID.randomUUID());
        newUser.setName(user.name());
        newUser.setEmail(user.email());
        newUser.setAge(0);
        try {
            userService.addUser(newUser);
        } catch (IllegalStateException e) {
            logger.info("this email is already in database "+newUser.getEmail());
        }

        return new OAuth2IntrospectionAuthenticatedPrincipal(user.email(), attributes, null);
    }
}
