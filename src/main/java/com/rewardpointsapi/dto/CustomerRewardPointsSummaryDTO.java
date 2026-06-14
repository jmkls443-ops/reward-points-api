package com.rewardpointsapi.dto;

import java.util.Map;

public class CustomerRewardPointsSummaryDTO {

    private String customerId;
    private String customerName;
    private Map<String, Integer> monthlyRewards;
    private int totalRewards;

    // Empty constructor
    public CustomerRewardPointsSummaryDTO() {
    }

    // Parameterized constructor
    public CustomerRewardPointsSummaryDTO(String customerId, String customerName, 
                                          Map<String, Integer> monthlyRewards, int totalRewards) {
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

    public Map<String, Integer> getMonthlyRewards() {
        return monthlyRewards;
    }

    public void setMonthlyRewards(Map<String, Integer> monthlyRewards) {
        this.monthlyRewards = monthlyRewards;
    }

    public int getTotalRewards() {
        return totalRewards;
    }

    public void setTotalRewards(int totalRewards) {
        this.totalRewards = totalRewards;
    }
}
