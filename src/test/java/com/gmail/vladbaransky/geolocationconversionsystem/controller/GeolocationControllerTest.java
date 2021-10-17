package com.gmail.vladbaransky.geolocationconversionsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.geolocationconversionsystem.service.GeolocationService;
import com.gmail.vladbaransky.geolocationconversionsystem.service.model.Geolocation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GeolocationController.class)
@AutoConfigureMockMvc
public class GeolocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GeolocationService geolocationService;

    @Test
    void getGeolocation_returnStatusOk() throws Exception {
        mockMvc.perform(get("/api")
                .param("parameter", "50, 60"))
                .andExpect(status().isOk());
    }


    @Test
    void getGeolocation_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/api")
                .param("parameter", "50, 60"))
                .andExpect(status().isOk());

        verify(geolocationService, times(1)).
                getGeolocation(eq("50, 60"));
    }

    @Test
    void getGeolocation_returnGeolocationResult() throws Exception {
        Geolocation geolocation = getGeolocation();

        when(geolocationService.getGeolocation("50, 60")).thenReturn(geolocation);
        MvcResult result = mockMvc.perform(get("/api")
                .param("parameter", "50, 60"))
                .andReturn();

        verify(geolocationService, times(1)).
                getGeolocation(eq("50, 60"));

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(geolocation);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    private Geolocation getGeolocation() {
        Geolocation geolocation = new Geolocation();
        geolocation.setCoordinates("50, 60");
        geolocation.setAddress("string");
        return geolocation;
    }
}
