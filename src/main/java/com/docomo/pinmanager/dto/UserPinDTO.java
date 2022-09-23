package com.docomo.pinmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author virupaksha.kuruva
 */
public class UserPinDTO {

    @NotNull
    @Size(min = 12, max = 15)
    @ApiModelProperty(required = true, name = "msisdn", dataType = "String", example = "+918105404025")
    private String msisdn;

    @NotNull
    @Size(min = 4, max = 4)
    @ApiModelProperty(required = true, name = "pin", dataType = "String", example = "6323")
    
    private String pin;

    public UserPinDTO() {
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

}
