package com.docomo.pinmanager.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author virupaksha.kuruva
 *
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPin implements Serializable {

    @Id
    @Column(name = "MSISDN")
    @NotNull
    @NotBlank
    @Size(min = 12, max = 15)
    private String msisdn;
    private Date created_date;
    private Date pinValidated_date;
    @NotNull
    @NotBlank
    @Size(min = 4, max = 4)
    private String pin;
    private int pinMaxInvalidCount;
    private int badTryCount;
    private int pinRemovalInHour;
    private boolean pinVaidatedStatus;
    //0-means non-blocked & 1- means blocked
    private int pinBlokedStatus;

    public UserPin() {
    }

    public UserPin(String MSISDN, Date created_date, Date pinValidated_date, String pin, int pinMaxInvalidCount, int badTryCount, int pinRemovalInHour, boolean pinVaidatedStatus, int pinBlokedStatus) {
        this.msisdn = MSISDN;
        this.created_date = created_date;
        this.pinValidated_date = pinValidated_date;
        this.pin = pin;
        this.pinMaxInvalidCount = pinMaxInvalidCount;
        this.badTryCount = badTryCount;
        this.pinRemovalInHour = pinRemovalInHour;
        this.pinVaidatedStatus = pinVaidatedStatus;
        this.pinBlokedStatus = pinBlokedStatus;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getPinValidated_date() {
        return pinValidated_date;
    }

    public void setPinValidated_date(Date pinValidated_date) {
        this.pinValidated_date = pinValidated_date;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getPinMaxInvalidCount() {
        return pinMaxInvalidCount;
    }

    public void setPinMaxInvalidCount(int pinMaxInvalidCount) {
        this.pinMaxInvalidCount = pinMaxInvalidCount;
    }

    public int getBadTryCount() {
        return badTryCount;
    }

    public void setBadTryCount(int badTryCount) {
        this.badTryCount = badTryCount;
    }

    public int getPinRemovalInHour() {
        return pinRemovalInHour;
    }

    public void setPinRemovalInHour(int pinRemovalInHour) {
        this.pinRemovalInHour = pinRemovalInHour;
    }

    public boolean isPinVaidatedStatus() {
        return pinVaidatedStatus;
    }

    public void setPinVaidatedStatus(boolean pinVaidatedStatus) {
        this.pinVaidatedStatus = pinVaidatedStatus;
    }
    

    public int getPinBlokedStatus() {
        return pinBlokedStatus;
    }

    public void setPinBlokedStatus(int pinBlokedStatus) {
        this.pinBlokedStatus = pinBlokedStatus;
    }

}
