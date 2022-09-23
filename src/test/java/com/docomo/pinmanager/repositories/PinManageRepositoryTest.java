package com.docomo.pinmanager.repositories;

import com.docomo.pinmanager.entities.UserPin;
import java.util.Date;
import java.util.List;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 *
 * @author virupaksha.kuruva
 */
@DataJpaTest
public class PinManageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PinManageRepository repository;

    private String MSISDN = "+918105222233";

    @Test
    public void testSave() {

    }

    @Test
    public void should_find_no_userPins_if_repository_is_empty() {

        List<UserPin> listOfUserPins = repository.findAll();
        assertThat(listOfUserPins).isEmpty();
    }

    @Test
    public void should_store_a_userPinData() {

        UserPin userPinBeforeSave = getUserPinData();

        UserPin userPin = repository.save(userPinBeforeSave);

        assertThat(userPin).hasFieldOrPropertyWithValue("msisdn", MSISDN);
        assertThat(userPin).hasFieldOrPropertyWithValue("pin", userPinBeforeSave.getPin());
        assertThat(userPin).hasFieldOrPropertyWithValue("pinVaidatedStatus", userPinBeforeSave.isPinVaidatedStatus());

        assertThat(userPin).hasFieldOrPropertyWithValue("badTryCount", userPinBeforeSave.getBadTryCount());
        assertThat(userPin).hasFieldOrPropertyWithValue("pinMaxInvalidCount", userPinBeforeSave.getPinMaxInvalidCount());
        assertThat(userPin).hasFieldOrPropertyWithValue("pinRemovalInHour", userPinBeforeSave.getPinRemovalInHour());

        assertThat(userPin).hasFieldOrPropertyWithValue("created_date", userPinBeforeSave.getCreated_date());
        assertThat(userPin).hasFieldOrPropertyWithValue("pinBlokedStatus", userPinBeforeSave.getPinBlokedStatus());

    }

    @Test
    public void should_find_userPinData_by_id() {
        
        UserPin userPinBeforeSave = getUserPinData();
        entityManager.persist(userPinBeforeSave);

        UserPin foundUserPin = repository.findById(userPinBeforeSave.getMsisdn()).get();

        assertThat(foundUserPin).isEqualTo(userPinBeforeSave);
    }

    public UserPin getUserPinData() {

        Random random = new Random();
        int pin = random.nextInt(10000);

        UserPin userPin = new UserPin();
        userPin.setMsisdn(MSISDN);
        userPin.setPin(String.format("%04d", pin));
        userPin.setPinVaidatedStatus(false);
        userPin.setBadTryCount(0);
        userPin.setPinMaxInvalidCount(3);
        userPin.setPinRemovalInHour(1);
        userPin.setCreated_date(new Date());
        userPin.setPinBlokedStatus(0);

        return userPin;

    }
}
