package com.rewardpointsapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RewardPointsTransactionDTO {
    private Long id;
    private String customerId;
    private String customerName;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private BigDecimal rewardPoints;

    public RewardPointsTransactionDTO() {
        // Default constructor for Jackson
    }

    public RewardPointsTransactionDTO(Long id, String customerId, String customerName, BigDecimal amount,
                                      LocalDate transactionDate, BigDecimal rewardPoints) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.rewardPoints = rewardPoints;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(BigDecimal rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}
