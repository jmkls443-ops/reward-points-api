package com.rewardpointsapi.dto;

import java.time.LocalDate;

public class RewardPointsTranscationDTO {

    private Long id;
    private String customerId;
    private String customerName;
    private double amount;
    private LocalDate transactionDate;

    // Empty constructor
    public RewardPointsTranscationDTO() {
    }

    // Parameterized constructor
    public RewardPointsTranscationDTO(Long id, String customerId, String customerName, double amount, LocalDate transactionDate) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    // Getters and Setters
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
