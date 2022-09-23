/**
 * 
 */
package com.docomo.pinmanager.services;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docomo.pinmanager.dto.Message;
import com.docomo.pinmanager.dto.UserPinDTO;
import com.docomo.pinmanager.entities.UserPin;
import com.docomo.pinmanager.exceptions.UserPinNotFoundException;
import com.docomo.pinmanager.repositories.PinManageRepository;


/**
 * @author virupaksha.kuruva
 *
 */
@Service
public class UserPinService {
	 private static final Logger logger = LoggerFactory.getLogger(UserPinService.class);
	@Autowired
	PinManageRepository pinManageRepository;

	public UserPinDTO generateUserPin(String MSISDN) {
		int pin = 0;

		Random random = new Random();
		pin = random.nextInt(10000);

		UserPin userPin = new UserPin();
		userPin.setMsisdn(MSISDN);
		userPin.setPin(String.format("%04d", pin));
		userPin.setPinVaidatedStatus(false);
		userPin.setBadTryCount(0);
		userPin.setPinMaxInvalidCount(3);
		userPin.setPinRemovalInHour(1);
		userPin.setCreated_date(new Date());
		userPin.setPinBlokedStatus(0);
		logger.debug("Pin is generated for MSISDN:" + MSISDN);
		pinManageRepository.save(userPin);
		logger.debug("Pin is store in the DB for MSISDN:" + MSISDN);
		if (pin > 0) {
			logger.debug("Pin is generated for the given MSISDN:" + pin);
		} else {
			logger.debug("Pin is not generated for the given MSISDN:" + pin + ", somethig is wrong in service layer");
		}
		UserPinDTO userPinDTO = new UserPinDTO();
		userPinDTO.setPin(String.format("%04d", pin));
		userPinDTO.setMsisdn(MSISDN);
		return userPinDTO;
	}

	@SuppressWarnings("unused")
	public Message validateUserPin(UserPinDTO passedUserPinData) {
		Message message = new Message();

        UserPin userPin = pinManageRepository.getById(passedUserPinData.getMsisdn());
        if (isMsisdnExistInDB(userPin, passedUserPinData)) {

            if (isPinExpired(userPin, passedUserPinData)) {
                message.setMessage("Pin Expired for MSISDN".concat(passedUserPinData.getMsisdn()));
                logger.debug("user pin will be removed due to validation expiry. MSISDN:" + userPin.getMsisdn()
                        + "| Pin created date & time:" + userPin.getCreated_date());
                pinManageRepository.delete(userPin);
                return message;
            } else {
                if (userPin.getPinBlokedStatus() == 0) {
                    //logger.debug("given pin:" + passedUserPinData.getPin() + " stored pin: " + userPin.getPin());
                    if (validatePinAgainstDB(userPin, passedUserPinData, message)) {
                        return message;
                    } else {
                        updateFailedStatusInDB(userPin, message);
                    }
                } else {
                    message.setMessage("Pin is validated already or pin is in blocked status.");
                    return message;
                }
            }
        }
        return message;
			}

/*	public UserPin getUserPinByMSISDN(String MSISDN) {

		return pinManageRepository.getById(MSISDN);
	}

	public boolean updatePinValidationStatus(UserPin user) {
		return false;
	}*/
	
    private boolean validatePinAgainstDB(UserPin userPin, UserPinDTO passedUserPinData, Message message) {
        if (userPin.getPin().equals(passedUserPinData.getPin())) {
            // pin matched updating in DB
            userPin.setBadTryCount(0);
            userPin.setPinVaidatedStatus(true);
            userPin.setPinValidated_date(new Date());
            logger.debug("User pin is validate successfully for MSISDN :" + userPin.getMsisdn());
            message.setMessage("User pin is validate successfully for MSISDN :" + userPin.getMsisdn());
            pinManageRepository.save(userPin);
            return true;
        } else {
            return false;
        }
    }
	
    private void updateFailedStatusInDB(UserPin userPin, Message message) {
        int invalidCount = userPin.getBadTryCount();
        if (invalidCount + 1 > userPin.getPinMaxInvalidCount()) {
            // blocking the pin
            userPin.setPinBlokedStatus(1);
            logger.debug(
                    "User pin is blocked after " + userPin.getPinMaxInvalidCount() + " invalid attempts.");
            message.setMessage("User pin is blocked after " + userPin.getPinMaxInvalidCount() + " invalid attempts.");
        } else {
            // increasing invalid counter
            logger.warn("Invalid pin entered. count:" + (invalidCount + 1));
            message.setMessage("Invalid pin entered. count:" + (invalidCount + 1));
            userPin.setBadTryCount(++invalidCount);
        }
        pinManageRepository.save(userPin);
    }
	
    public boolean isPinExpired(UserPin userPin, UserPinDTO passedUserPinData) {

        boolean valid = false;
        Date currentDate = new Date();

        if (userPin != null) {

            Calendar cal = Calendar.getInstance();
            // remove next line if you're always using the current time.
            cal.setTime(currentDate);
            cal.add(Calendar.HOUR, -userPin.getPinRemovalInHour());
            Date oneHourBack = cal.getTime();
            valid =  userPin.getCreated_date().before(oneHourBack);

            // check validated Status
            return valid;
        }
        return false;
    }
	
	   public boolean isMsisdnExistInDB(UserPin userPin, UserPinDTO passedUserPinData) {

	        if (userPin != null) {
	            return true;
	        } else {
	            logger.debug("User pin not found in DB for the given MSISDN: " + passedUserPinData.getMsisdn());
	            throw new UserPinNotFoundException(404, "User pin not found for give MSISDN.");
	        }

	    }

}
