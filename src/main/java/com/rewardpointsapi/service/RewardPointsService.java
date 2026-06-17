package com.rewardpointsapi.service;

import com.rewardpointsapi.dto.CustomerRewardPointsSummaryDTO;
import com.rewardpointsapi.dto.RewardPointsTransactionDTO;
import com.rewardpointsapi.entity.RewardPointsTransaction;
import com.rewardpointsapi.repository.RewardPointsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardPointsService {

	private final RewardPointsRepository rewardPointsRepository;

	// Constructor injection
	public RewardPointsService(RewardPointsRepository rewardPointsRepository) {
		this.rewardPointsRepository = rewardPointsRepository;
	}

	// Save a transaction and calculate reward points
	public RewardPointsTransactionDTO saveTransaction(RewardPointsTransaction transaction) {
		RewardPointsTransaction saved = rewardPointsRepository.save(transaction);
		BigDecimal rewardPoints = calculateRewardPoints(saved.getAmount());
		return new RewardPointsTransactionDTO(saved.getId(), saved.getCustomerId(), saved.getCustomerName(),
				saved.getAmount(), saved.getTransactionDate(), rewardPoints);

	}

	// Fetch all transactions
	public List<RewardPointsTransaction> getAllTransactions() {
		return rewardPointsRepository.findAll();
	}

	// Fetch transactions by customerId
	public List<RewardPointsTransaction> getTransactionsByCustomerId(String customerId) {
		return rewardPointsRepository.findByCustomerId(customerId);
	}

	public List<RewardPointsTransactionDTO> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
		List<RewardPointsTransaction> transactions = rewardPointsRepository.findByTransactionDateBetween(startDate,
				endDate);

		return transactions.stream().map(tx -> {
			BigDecimal points = calculateRewardPoints(tx.getAmount());
			return new RewardPointsTransactionDTO(tx.getId(), tx.getCustomerId(), tx.getCustomerName(), tx.getAmount(),
					tx.getTransactionDate(), points);
		}).collect(Collectors.toList());
	}

	public CustomerRewardPointsSummaryDTO getCustomerRewardSummary(String customerId) {
		List<RewardPointsTransaction> transactions = rewardPointsRepository.findByCustomerId(customerId);

		Map<String, BigDecimal> monthlyRewards = new HashMap<>();
		BigDecimal totalRewards = BigDecimal.ZERO;
		String customerName = transactions.isEmpty() ? "" : transactions.get(0).getCustomerName();

		for (RewardPointsTransaction tx : transactions) {
			BigDecimal points = calculateRewardPoints(tx.getAmount());
			totalRewards = totalRewards.add(points);

			// Include both month and year in the key
			String monthYear = tx.getTransactionDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " "
					+ tx.getTransactionDate().getYear();

			monthlyRewards.put(monthYear, monthlyRewards.getOrDefault(monthYear, BigDecimal.ZERO).add(points));
		}

		return new CustomerRewardPointsSummaryDTO(customerId, customerName, monthlyRewards, totalRewards);
	}

	public BigDecimal calculateRewardPoints(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.valueOf(50)) <= 0) {
			return BigDecimal.ZERO;
		} else if (amount.compareTo(BigDecimal.valueOf(100)) <= 0) {
			// 1 point per dollar above 50
			return amount.subtract(BigDecimal.valueOf(50));
		} else {
			// 2 points per dollar above 100, plus 50 points for the 50–100 range
			return amount.subtract(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(2)).add(BigDecimal.valueOf(50));
		}
	}

}
