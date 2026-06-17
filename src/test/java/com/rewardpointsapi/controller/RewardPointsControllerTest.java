package com.rewardpointsapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rewardpointsapi.dto.RewardPointsTransactionDTO;
import com.rewardpointsapi.service.RewardPointsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardPointsController.class)
public class RewardPointsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardPointsService rewardPointsService;

    private ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Test
    void testGetTransactionsByDateRange_usingJsonFixture() throws Exception {
        ObjectMapper mapper = getMapper();

        // Load expected DTOs from response-sample.json
        RewardPointsTransactionDTO[] expectedDtos = mapper.readValue(
                Files.readAllBytes(Paths.get("src/test/resources/docs/response-sample.json")),
                RewardPointsTransactionDTO[].class
        );

        LocalDate startDate = LocalDate.of(2026, 4, 1);
        LocalDate endDate = LocalDate.of(2026, 5, 31);

        Mockito.when(rewardPointsService.getTransactionsByDateRange(startDate, endDate))
                .thenReturn(Arrays.asList(expectedDtos));

        String expectedJson = new String(
                Files.readAllBytes(Paths.get("src/test/resources/docs/response-sample.json")),
                StandardCharsets.UTF_8
        );

        mockMvc.perform(get("/transactions/dateRange")
                        .param("startDate", "2026-04-01")
                        .param("endDate", "2026-05-31"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, false)); // lenient comparison
    }

    @Test
    void testGetTransactionsByDateRange_withBothParams_shouldFail() throws Exception {
        mockMvc.perform(get("/transactions/dateRange")
                        .param("startDate", "2026-04-01")
                        .param("endDate", "2026-05-31")
                        .param("months", "2"))
                .andExpect(status().isBadRequest());
    }
}
