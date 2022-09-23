package com.docomo.pinmanager.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author  virupaksha.kuruva
 */
public class Message {
    
    @ApiModelProperty(required = true,name = "message", dataType = "String", example = "Pin validation is successfully.")
    private String message;

    public Message() {
    }
    
    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
