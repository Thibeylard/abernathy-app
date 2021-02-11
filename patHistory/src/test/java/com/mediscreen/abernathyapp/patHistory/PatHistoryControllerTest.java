package com.mediscreen.abernathyapp.patHistory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathyapp.patHistory.dtos.BadRequestErrorDTO;
import com.mediscreen.abernathyapp.patHistory.services.PatHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class PatHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PatHistoryService patHistoryService;

    @Test
    public void validRequest() throws Exception {

        long count = 5;
        when(patHistoryService.terminologySearch(anyString(), anySet())).thenReturn(count);

        MvcResult result = mockMvc.perform(get("/patHistory/assess")
                .param("patientId", "1")
                .param("terminology", "MOT1")
                .param("terminology", "MOT2")
                .param("terminology", "MOT3")
                .param("terminology", "MOT4")
                .param("terminology", "MOT5"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(mapper.writeValueAsString(count));
    }

    @Test
    public void invalidPatientId() throws Exception {
        NoSuchElementException e = new NoSuchElementException("error message");

        when(patHistoryService.terminologySearch(anyString(), anySet()))
                .thenThrow(e);

        MvcResult result = mockMvc.perform(get("/patHistory/assess")
                .param("patientId", "1")
                .param("terminology", "MOT1")
                .param("terminology", "MOT2")
                .param("terminology", "MOT3")
                .param("terminology", "MOT4")
                .param("terminology", "MOT5"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();

        BadRequestErrorDTO badRequestErrorDTO =
                mapper.convertValue(mapper.readTree(jsonBody), BadRequestErrorDTO.class);

        assertThat(badRequestErrorDTO.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(badRequestErrorDTO.getFaultyParameter())
                .isEqualTo("patientId");
        assertThat(badRequestErrorDTO.getMessage())
                .isEqualTo(e.getMessage());

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void invalidTerminology() throws Exception {

        MvcResult result = mockMvc.perform(get("/patHistory/assess")
                .param("patientId", "1")
                .param("terminology", ""))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();

        BadRequestErrorDTO badRequestErrorDTO =
                mapper.convertValue(mapper.readTree(jsonBody), BadRequestErrorDTO.class);

        assertThat(badRequestErrorDTO.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(badRequestErrorDTO.getFaultyParameter())
                .isEqualTo("terminology");
        assertThat(badRequestErrorDTO.getMessage())
                .isNotBlank();

        System.out.println(result.getResponse().getContentAsString());
    }
}
