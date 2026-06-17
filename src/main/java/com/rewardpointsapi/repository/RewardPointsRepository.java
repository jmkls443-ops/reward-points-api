package com.rewardpointsapi.repository;

import com.rewardpointsapi.entity.RewardPointsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RewardPointsRepository extends JpaRepository<RewardPointsTransaction, Long> {

    // Find all transactions by customerId
    List<RewardPointsTransaction> findByCustomerId(String customerId);

    // Find all transactions between two dates
    List<RewardPointsTransaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
}
