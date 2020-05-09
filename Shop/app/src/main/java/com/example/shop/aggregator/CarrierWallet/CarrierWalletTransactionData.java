package com.example.shop.aggregator.CarrierWallet;

public class CarrierWalletTransactionData {

    String walletTransaction_id;
    int walletTransactionMethod;
    String walletMoneyAddStatus;
    String walletMoneyAddTimeStamp;
    String walletMoneyAmount;

    public CarrierWalletTransactionData(String walletTransaction_id, int walletTransactionMethod, String walletMoneyAddStatus, String walletMoneyAddTimeStamp, String walletMoneyAmount) {
        this.walletTransaction_id = walletTransaction_id;
        this.walletTransactionMethod = walletTransactionMethod;
        this.walletMoneyAddStatus = walletMoneyAddStatus;
        this.walletMoneyAddTimeStamp = walletMoneyAddTimeStamp;
        this.walletMoneyAmount = walletMoneyAmount;
    }

    public String getWalletTransaction_id() {
        return walletTransaction_id;
    }

    public void setWalletTransaction_id(String walletTransaction_id) {
        this.walletTransaction_id = walletTransaction_id;
    }

    public int getWalletTransactionMethod() {
        return walletTransactionMethod;
    }

    public void setWalletTransactionMethod(int walletTransactionMethod) {
        this.walletTransactionMethod = walletTransactionMethod;
    }

    public String getWalletMoneyAddStatus() {
        return walletMoneyAddStatus;
    }

    public void setWalletMoneyAddStatus(String walletMoneyAddStatus) {
        this.walletMoneyAddStatus = walletMoneyAddStatus;
    }

    public String getWalletMoneyAddTimeStamp() {
        return walletMoneyAddTimeStamp;
    }

    public void setWalletMoneyAddTimeStamp(String walletMoneyAddTimeStamp) {
        this.walletMoneyAddTimeStamp = walletMoneyAddTimeStamp;
    }

    public String getWalletMoneyAmount() {
        return walletMoneyAmount;
    }

    public void setWalletMoneyAmount(String walletMoneyAmount) {
        this.walletMoneyAmount = walletMoneyAmount;
    }
}
