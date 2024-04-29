package com.ridematefinder.controllers;

import com.ridematefinder.dtos.MessageDto;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@RestController
public class PrivateController {

//    Logger logger = LoggerFactory.getLogger(PrivateController.class);

    @GetMapping("/private/messages")
    public ResponseEntity<MessageDto> privateMessages(
            @AuthenticationPrincipal(expression = "name") String name){
        return ResponseEntity.ok(new MessageDto("private content "+ name));
    }

}
