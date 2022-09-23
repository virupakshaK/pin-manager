package com.docomo.pinmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author virupaksha.kuruva
 */
@JsonInclude(Include.NON_NULL)
@ApiModel
public class DataDto implements Serializable {

    @NotNull
    @Size(min = 12, max = 15)
    @ApiModelProperty(required = true,name = "msisdn", dataType = "String", example = "+918105404025")
    private String msisdn;

    public DataDto() {
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public DataDto(String msisdn) {
        this.msisdn = msisdn;
    }

}
