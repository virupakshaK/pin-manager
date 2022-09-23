/**
 *
 */
package com.docomo.pinmanager.controllers;

import com.docomo.pinmanager.dto.DataDto;
import com.docomo.pinmanager.dto.Message;
import com.docomo.pinmanager.dto.UserPinDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docomo.pinmanager.services.UserPinService;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author virupaksha.kuruva
 *
 */
@RestController
@RequestMapping("api/v1")
public class PinManageController {

    @Autowired
    UserPinService userPinService;

    private static final Logger logger = LoggerFactory.getLogger(PinManageController.class);

    @GetMapping("/greeting/{name}")
    public String getGreeting(@PathVariable String name) {
        return "Hello " + name + " welcome to java world";
    }

    @PostMapping("/generatepin")
    public ResponseEntity<UserPinDTO> generatePin(@Valid @RequestBody DataDto data) {

        UserPinDTO generatedPin = userPinService.generateUserPin(data.getMsisdn());

        return new ResponseEntity<>(generatedPin, HttpStatus.CREATED);
    }

    @PostMapping("/validatepin")
    public ResponseEntity<Message> validatePin(@Valid @RequestBody UserPinDTO userPin) {
        
        Message message = userPinService.validateUserPin(userPin);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

}
