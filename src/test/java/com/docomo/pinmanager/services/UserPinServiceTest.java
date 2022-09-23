package com.docomo.pinmanager.services;

import com.docomo.pinmanager.dto.UserPinDTO;
import com.docomo.pinmanager.entities.UserPin;
import com.docomo.pinmanager.repositories.PinManageRepository;
import java.util.Date;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 *
 * @author virupaksha.kuruva
 */
@WebMvcTest(UserPinService.class)
public class UserPinServiceTest {

    @MockBean
    PinManageRepository pinManageRepository;

    @Autowired
    UserPinService userPinService;

    private String MSISDN = "+918105222233";

    @Test
    public void testGenerateUserPin() {

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

        when(pinManageRepository.save(userPin)).thenReturn(userPin);

        UserPinDTO userPinDTO = userPinService.generateUserPin(MSISDN);

        assertThat(userPinDTO).hasFieldOrPropertyWithValue("msisdn", MSISDN);
    }

}
