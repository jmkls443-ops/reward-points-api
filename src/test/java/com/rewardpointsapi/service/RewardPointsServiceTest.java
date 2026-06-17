package com.rewardpointsapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rewardpointsapi.dto.CustomerRewardPointsSummaryDTO;
import com.rewardpointsapi.entity.RewardPointsTransaction;
import com.rewardpointsapi.repository.RewardPointsRepository;

public class RewardPointsServiceTest {

    private final RewardPointsRepository repository = Mockito.mock(RewardPointsRepository.class);
    private final RewardPointsService service = new RewardPointsService(repository);

    private ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    // Helper method to compare BigDecimals ignoring scale
    private void assertBigDecimalEquals(BigDecimal expected, BigDecimal actual) {
        assertEquals(0, expected.compareTo(actual),
            () -> "Expected " + expected + " but was " + actual);
    }

    @Test
    void testCalculateRewardPoints_fromJsonFixture() throws Exception {
        ObjectMapper mapper = getMapper();

        RewardPointsTransaction transaction = mapper.readValue(
                Files.readAllBytes(Paths.get("src/test/resources/docs/request-sample.json")),
                RewardPointsTransaction.class);

        BigDecimal points = service.calculateRewardPoints(transaction.getAmount());
        assertBigDecimalEquals(BigDecimal.valueOf(25), points); // adjust based on fixture
    }

    @Test
    void testCustomerSummary_fromJsonFixture() throws Exception {
        ObjectMapper mapper = getMapper();

        RewardPointsTransaction[] transactions = mapper.readValue(
                Files.readAllBytes(Paths.get("src/test/resources/docs/response-sample.json")),
                RewardPointsTransaction[].class);

        Mockito.when(repository.findByCustomerId("CUST001"))
               .thenReturn(Arrays.asList(transactions[0], transactions[1]));

        CustomerRewardPointsSummaryDTO summary = service.getCustomerRewardSummary("CUST001");

        assertBigDecimalEquals(BigDecimal.valueOf(115), summary.getTotalRewards());
        assertBigDecimalEquals(BigDecimal.valueOf(90), summary.getMonthlyRewards().get("Apr 2026"));
        assertBigDecimalEquals(BigDecimal.valueOf(25), summary.getMonthlyRewards().get("May 2026"));
    }

    @Test
    void testCustomerSummary_multipleTransactionsAcrossYears() throws Exception {
        ObjectMapper mapper = getMapper();

        CustomerRewardPointsSummaryDTO summary = mapper.readValue(
                Files.readAllBytes(Paths.get("src/test/resources/docs/summary-response.json")),
                CustomerRewardPointsSummaryDTO.class);

        assertBigDecimalEquals(BigDecimal.valueOf(340), summary.getTotalRewards());
        assertBigDecimalEquals(BigDecimal.valueOf(150), summary.getMonthlyRewards().get("Jun 2025"));
        assertBigDecimalEquals(BigDecimal.valueOf(190), summary.getMonthlyRewards().get("Jun 2026"));
    }

    @Test
    void testCustomerSummary_multipleCustomersAndMonths() {
        RewardPointsTransaction t1 = new RewardPointsTransaction();
        t1.setCustomerId("CUST001");
        t1.setCustomerName("Alice");
        t1.setAmount(BigDecimal.valueOf(120.75));
        t1.setTransactionDate(LocalDate.of(2026, 4, 10));

        RewardPointsTransaction t2 = new RewardPointsTransaction();
        t2.setCustomerId("CUST001");
        t2.setCustomerName("Alice");
        t2.setAmount(BigDecimal.valueOf(75.90));
        t2.setTransactionDate(LocalDate.of(2026, 5, 15));

        RewardPointsTransaction t3 = new RewardPointsTransaction();
        t3.setCustomerId("CUST002");
        t3.setCustomerName("Bob");
        t3.setAmount(BigDecimal.valueOf(200.25));
        t3.setTransactionDate(LocalDate.of(2025, 6, 20));

        List<RewardPointsTransaction> cust1Tx = Arrays.asList(t1, t2);
        List<RewardPointsTransaction> cust2Tx = Arrays.asList(t3);

        Mockito.when(repository.findByCustomerId("CUST001")).thenReturn(cust1Tx);
        Mockito.when(repository.findByCustomerId("CUST002")).thenReturn(cust2Tx);

        CustomerRewardPointsSummaryDTO summary1 = service.getCustomerRewardSummary("CUST001");
        CustomerRewardPointsSummaryDTO summary2 = service.getCustomerRewardSummary("CUST002");

        assertBigDecimalEquals(BigDecimal.valueOf(117.40), summary1.getTotalRewards()); // 91.5 + 25.9
        assertBigDecimalEquals(BigDecimal.valueOf(91.5), summary1.getMonthlyRewards().get("Apr 2026"));
        assertBigDecimalEquals(BigDecimal.valueOf(25.9), summary1.getMonthlyRewards().get("May 2026"));

        assertBigDecimalEquals(BigDecimal.valueOf(250.5), summary2.getTotalRewards()); // (100.25*2 + 50)
        assertBigDecimalEquals(BigDecimal.valueOf(250.5), summary2.getMonthlyRewards().get("Jun 2025"));
    }
}
