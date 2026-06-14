package com.rewardpointsapi.service;

import com.rewardpointsapi.dto.CustomerRewardPointsSummaryDTO;
import com.rewardpointsapi.entity.RewardPointsTransaction;
import com.rewardpointsapi.repository.RewardPointsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class RewardPointsService {

	private final RewardPointsRepository rewardPointsRepository;

	// Constructor injection
	public RewardPointsService(RewardPointsRepository rewardPointsRepository) {
		this.rewardPointsRepository = rewardPointsRepository;
	}

	// Save a transaction and calculate reward points
	public RewardPointsTransaction saveTransaction(RewardPointsTransaction transaction) {
		int rewardPoints = calculateRewardPoints(transaction.getAmount());
		// You could log or persist rewardPoints separately if needed
		System.out.println("Reward Points earned: " + rewardPoints);
		return rewardPointsRepository.save(transaction);
	}

	// Fetch all transactions
	public List<RewardPointsTransaction> getAllTransactions() {
		return rewardPointsRepository.findAll();
	}

	// Fetch transactions by customerId
	public List<RewardPointsTransaction> getTransactionsByCustomerId(String customerId) {
		return rewardPointsRepository.findByCustomerId(customerId);
	}

	// Fetch transactions between two dates
	public List<RewardPointsTransaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
		return rewardPointsRepository.findByTransactionDateBetween(startDate, endDate);
	}

	// Build a summary DTO for a customer
	public CustomerRewardPointsSummaryDTO getCustomerRewardSummary(String customerId) {
		List<RewardPointsTransaction> transactions = rewardPointsRepository.findByCustomerId(customerId);

		Map<String, Integer> monthlyRewards = new HashMap<>();
		int totalRewards = 0;
		String customerName = "";

		for (RewardPointsTransaction tx : transactions) {
			customerName = tx.getCustomerName(); // assume consistent name
			int points = calculateRewardPoints(tx.getAmount());
			totalRewards += points;

			String month = tx.getTransactionDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

			monthlyRewards.put(month, monthlyRewards.getOrDefault(month, 0) + points);
		}

		return new CustomerRewardPointsSummaryDTO(customerId, customerName, monthlyRewards, totalRewards);
	}

	// Calculate reward points based on amount
	public int calculateRewardPoints(double amount) {
		int points = 0;
		if (amount > 100) {
			points += (int) (amount - 100) * 2; // 2 points per dollar over 100
			points += 50; // 1 point per dollar between 50–100
		} else if (amount > 50) {
			points += (int) (amount - 50); // 1 point per dollar between 50–100
		}
		return points;
	}

}
