package com.rewardpointsapi.controller;

import com.rewardpointsapi.dto.CustomerRewardPointsSummaryDTO;
import com.rewardpointsapi.entity.RewardPointsTransaction;
import com.rewardpointsapi.service.RewardPointsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class RewardPointsController {

    private final RewardPointsService rewardPointsService;

    public RewardPointsController(RewardPointsService rewardPointsService) {
        this.rewardPointsService = rewardPointsService;
    }

    // Save a new transaction
    @PostMapping
    public RewardPointsTransaction saveTransaction(@RequestBody RewardPointsTransaction transaction) {
        return rewardPointsService.saveTransaction(transaction);
    }

    // Get all transactions
    @GetMapping
    public List<RewardPointsTransaction> getAllTransactions() {
        return rewardPointsService.getAllTransactions();
    }

    // Get transactions by customerId
    @GetMapping("/customer/{customerId}")
    public List<RewardPointsTransaction> getTransactionsByCustomerId(@PathVariable String customerId) {
        return rewardPointsService.getTransactionsByCustomerId(customerId);
    }

    // Get transactions by date range
    @GetMapping("/dateRange")
    public List<RewardPointsTransaction> getTransactionsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return rewardPointsService.getTransactionsByDateRange(startDate, endDate);
    }

    // Get reward summary for a customer
    @GetMapping("/summary/{customerId}")
    public CustomerRewardPointsSummaryDTO getCustomerRewardSummary(@PathVariable String customerId) {
        return rewardPointsService.getCustomerRewardSummary(customerId);
    }
}
