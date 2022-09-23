package com.docomo.pinmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.docomo.pinmanager.entities.UserPin;

/**
 * @author virupaksha.kuruva
 *
 */
public interface PinManageRepository extends JpaRepository<UserPin, String>{

}
