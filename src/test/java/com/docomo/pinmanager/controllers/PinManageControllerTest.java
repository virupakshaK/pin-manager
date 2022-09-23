package com.docomo.pinmanager.controllers;

import com.docomo.pinmanager.dto.DataDto;
import com.docomo.pinmanager.dto.UserPinDTO;
import com.docomo.pinmanager.services.UserPinService;
import com.docomo.pinmanager.util.TestUtil;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 *
 * @author virupaksha.kuruva
 */
@WebMvcTest(PinManageController.class)
public class PinManageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserPinService userPinService;

    private final String URL = "/api/v1";

    @Test
    public void getGreeting() throws Exception {

        String name = "Viru";

        String apiURL = "/greeting/" + name;

        this.mockMvc.perform(get(URL.concat(apiURL))).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello " + name + " welcome to java world")));
    }

    @Test
    public void generatePinValidation() throws Exception {

        DataDto data = new DataDto();

        String resouce = "/generatepin";

        mockMvc.perform(post(URL.concat(resouce)).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(data)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[0].field", is("msisdn")))
                .andExpect(jsonPath("$.fieldErrors[0].message", is(
                        "MSISDN can not be null"
                )));

    }

    @Test
    public void generatePinValidationOnLength() throws Exception {

        DataDto data = new DataDto();
        data.setMsisdn("+918105302");

        String resouce = "/generatepin";

        mockMvc.perform(post(URL.concat(resouce)).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(data)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[0].field", is("msisdn")))
                .andExpect(jsonPath("$.fieldErrors[0].message", is(
                        "MSISDN Me must be between 12 and 15 characters"
                )));

    }

    @Test
    public void generatePinValidationSuccess() throws Exception {

        DataDto data = new DataDto();
        data.setMsisdn("+918105302025");

        String resouce = "/generatepin";

        UserPinDTO userPinDTO = new UserPinDTO();
        userPinDTO.setPin(String.format("%04d", 5645));
        userPinDTO.setMsisdn(data.getMsisdn());

        //mocking
        when(userPinService.generateUserPin(data.getMsisdn())).thenReturn(userPinDTO);

        mockMvc.perform(post(URL.concat(resouce)).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(data)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.msisdn", is(data.getMsisdn())))
                .andExpect(jsonPath("$.pin", is(userPinDTO.getPin())));

    }

    @Test
    public void pinValidation() throws Exception {

        UserPinDTO data = new UserPinDTO();
        data.setPin("1234");

        String resouce = "/validatepin";

        mockMvc.perform(post(URL.concat(resouce)).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(data)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[0].field", is("msisdn")))
                .andExpect(jsonPath("$.fieldErrors[0].message", is(
                        "MSISDN can not be null"
                )));

    }

    @Test
    public void pinValidation2() throws Exception {

        UserPinDTO data = new UserPinDTO();
        data.setMsisdn("+918105302025");

        String resouce = "/validatepin";

        mockMvc.perform(post(URL.concat(resouce)).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(data)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[0].field", is("pin")))
                .andExpect(jsonPath("$.fieldErrors[0].message", is(
                        "PIN can not be null"
                )));

    }

    @Test
    public void pinValidationOnLenght() throws Exception {

        UserPinDTO data = new UserPinDTO();
        data.setMsisdn("+918105302025");
        data.setPin("12345");

        String resouce = "/validatepin";

        mockMvc.perform(post(URL.concat(resouce)).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(data)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[0].field", is("pin")))
                .andExpect(jsonPath("$.fieldErrors[0].message", is(
                        "PIN  must be 4 characters"
                )));

    }

}
