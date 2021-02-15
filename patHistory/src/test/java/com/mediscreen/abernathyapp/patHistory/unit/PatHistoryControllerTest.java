package com.mediscreen.abernathyapp.patHistory.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathyapp.patHistory.dtos.ErrorDTO;
import com.mediscreen.abernathyapp.patHistory.dtos.PatHistoryTermsCountDTO;
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
import java.util.Set;

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

        PatHistoryTermsCountDTO patHistoryTermsCountDTO = new PatHistoryTermsCountDTO(
                "1",
                Set.of("MOT1","MOT2","MOT3","MOT4","MOT5"),
                5
        );
        when(patHistoryService.terminologySearch(anyString(), anySet())).thenReturn(patHistoryTermsCountDTO);

        MvcResult result = mockMvc.perform(get("/patHistory/countTerms")
                .param("patientId", "1")
                .param("terminology", "MOT1")
                .param("terminology", "MOT2")
                .param("terminology", "MOT3")
                .param("terminology", "MOT4")
                .param("terminology", "MOT5"))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mapper.writeValueAsString(patHistoryTermsCountDTO));

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(mapper.writeValueAsString(patHistoryTermsCountDTO));
    }

    @Test
    public void invalidPatientId() throws Exception {
        NoSuchElementException e = new NoSuchElementException("error message");

        when(patHistoryService.terminologySearch(anyString(), anySet()))
                .thenThrow(e);

        MvcResult result = mockMvc.perform(get("/patHistory/countTerms")
                .param("patientId", "1")
                .param("terminology", "MOT1")
                .param("terminology", "MOT2")
                .param("terminology", "MOT3")
                .param("terminology", "MOT4")
                .param("terminology", "MOT5"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();

        ErrorDTO errorDTO =
                mapper.convertValue(mapper.readTree(jsonBody), ErrorDTO.class);

        assertThat(errorDTO.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(errorDTO.getMessages())
                .contains(e.getMessage());

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void invalidTerminology() throws Exception {

        MvcResult result = mockMvc.perform(get("/patHistory/countTerms")
                .param("patientId", "1")
                .param("terminology", ""))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();

        ErrorDTO errorDTO =
                mapper.convertValue(mapper.readTree(jsonBody), ErrorDTO.class);

        assertThat(errorDTO.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(errorDTO.getMessages())
                .isNotEmpty();

        System.out.println(result.getResponse().getContentAsString());
    }
}
