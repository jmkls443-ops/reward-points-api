package com.rewardpointsapi.dto;

import java.math.BigDecimal;
import java.util.Map;

public class CustomerRewardPointsSummaryDTO {

    private String customerId;
    private String customerName;
    private Map<String, BigDecimal> monthlyRewards;
    private BigDecimal totalRewards;

    // Empty constructor
    public CustomerRewardPointsSummaryDTO() {
    }

    // Parameterized constructor
    public CustomerRewardPointsSummaryDTO(String customerId, String customerName,
                                          Map<String, BigDecimal> monthlyRewards, BigDecimal totalRewards) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.monthlyRewards = monthlyRewards;
        this.totalRewards = totalRewards;
    }

    // Getters and Setters
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

    public Map<String, BigDecimal> getMonthlyRewards() {
        return monthlyRewards;
    }

    public void setMonthlyRewards(Map<String, BigDecimal> monthlyRewards) {
        this.monthlyRewards = monthlyRewards;
    }

    public BigDecimal getTotalRewards() {
        return totalRewards;
    }

    public void setTotalRewards(BigDecimal totalRewards) {
        this.totalRewards = totalRewards;
    }
}
