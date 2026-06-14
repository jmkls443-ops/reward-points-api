package com.rewardpointsapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rewardpointsapi.dto.CustomerRewardPointsSummaryDTO;
import com.rewardpointsapi.entity.RewardPointsTransaction;
import com.rewardpointsapi.repository.RewardPointsRepository;

@ExtendWith(MockitoExtension.class)
class RewardPointsServiceTest {

    @Mock
    private RewardPointsRepository repository;

    @InjectMocks
    private RewardPointsService service;

    @Test
    void testCalculateRewardPoints_Under50() {
        assertEquals(0, service.calculateRewardPoints(45));
    }

    @Test
    void testCalculateRewardPoints_Between50And100() {
        assertEquals(25, service.calculateRewardPoints(75)); // 75-50 = 25
    }

    @Test
    void testCalculateRewardPoints_Over100() {
        assertEquals(90, service.calculateRewardPoints(120)); // 50 + (20*2) = 90
        assertEquals(250, service.calculateRewardPoints(200)); // 50 + (100*2) = 250
    }

    @Test
    void testSaveTransaction() {
        RewardPointsTransaction tx = new RewardPointsTransaction(null, "CUST001", "Alice", 120.0, LocalDate.now());
        when(repository.save(tx)).thenReturn(tx);

        RewardPointsTransaction saved = service.saveTransaction(tx);
        assertNotNull(saved);
        assertEquals("Alice", saved.getCustomerName());
        verify(repository, times(1)).save(tx);
    }

    @Test
    void testGetCustomerRewardSummary() {
        RewardPointsTransaction tx1 = new RewardPointsTransaction(1L, "CUST001", "Alice", 120.0, LocalDate.of(2026, 4, 10));
        RewardPointsTransaction tx2 = new RewardPointsTransaction(2L, "CUST001", "Alice", 75.0, LocalDate.of(2026, 5, 15));

        when(repository.findByCustomerId("CUST001")).thenReturn(Arrays.asList(tx1, tx2));

        CustomerRewardPointsSummaryDTO summary = service.getCustomerRewardSummary("CUST001");

        assertEquals("CUST001", summary.getCustomerId());
        assertEquals("Alice", summary.getCustomerName());
        assertEquals(115, summary.getTotalRewards()); // 90 + 25
        assertTrue(summary.getMonthlyRewards().containsKey("Apr"));
        assertTrue(summary.getMonthlyRewards().containsKey("May"));
    }
}
