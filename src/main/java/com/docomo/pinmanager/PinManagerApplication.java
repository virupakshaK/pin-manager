package com.docomo.pinmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author virupaksha.kuruva
 *
 */
@SpringBootApplication
public class PinManagerApplication {

    private static final Logger logger = LoggerFactory.getLogger(PinManagerApplication.class);

    public static void main(String[] args) {
        logger.info("-----------It is starting ----------------");
        SpringApplication.run(PinManagerApplication.class, args);
    }

}
