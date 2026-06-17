package com.rewardpointsapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Transaction")
public class RewardPointsTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;

    @Column(name = "rewardPoints")
    private BigDecimal rewardPoints;

    // Empty constructor
    public RewardPointsTransaction() {
    }

    // Parameterized constructor

    public RewardPointsTransaction(Long id, String customerId, String customerName, BigDecimal amount,
                                   LocalDate transactionDate) {
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

    @PrePersist
    @PreUpdate

    public void ensureRewardPointsNotNull() {
        if (rewardPoints == null) {
            rewardPoints = BigDecimal.ZERO; // default value
        }
    }
}
