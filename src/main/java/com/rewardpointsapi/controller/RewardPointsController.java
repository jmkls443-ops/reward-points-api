package com.rewardpointsapi.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rewardpointsapi.dto.CustomerRewardPointsSummaryDTO;
import com.rewardpointsapi.dto.RewardPointsTransactionDTO;
import com.rewardpointsapi.entity.RewardPointsTransaction;
import com.rewardpointsapi.service.RewardPointsService;

@RestController
@RequestMapping("/transactions")
public class RewardPointsController {

	private final RewardPointsService rewardPointsService;

	public RewardPointsController(RewardPointsService rewardPointsService) {
		this.rewardPointsService = rewardPointsService;
	}

	/**
	 * Add a new transaction. Returns a DTO including rewardPoints.
	 */
	@PostMapping
	public RewardPointsTransactionDTO addTransaction(@RequestBody RewardPointsTransaction transaction) {
		return rewardPointsService.saveTransaction(transaction);
	}

	/**
	 * Get transactions by date range. Supports either startDate + endDate OR
	 * startDate + months. Returns DTOs including rewardPoints.
	 */
	@GetMapping("/dateRange")
	public List<RewardPointsTransactionDTO> getTransactionsByDateRange(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(value = "months", required = false) Integer months) {

		if (months != null && endDate != null) {
			throw new IllegalArgumentException("Provide either endDate or months, not both.");
		}
		if (months != null) {
			endDate = startDate.plusMonths(months);
		}

		return rewardPointsService.getTransactionsByDateRange(startDate, endDate);
	}

	/**
	 * Get reward points summary for a customer.
	 */
	@GetMapping("/summary")
	public CustomerRewardPointsSummaryDTO getCustomerSummary(@RequestParam("customerId") String customerId) {
		return rewardPointsService.getCustomerRewardSummary(customerId);
	}
}
