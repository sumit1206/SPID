package com.example.shop.aggregator.profile.profiledata;

public class BankDetailsData
{
    String profileBankFieldValue;
    String profileBankFieldName;
    int itemBankThumbnail;
    int profileBankDetailsActionIcon;

    public BankDetailsData(String profileBankFieldValue, String profileBankFieldName, int itemBankThumbnail, int profileBankDetailsActionIcon) {
        this.profileBankFieldValue = profileBankFieldValue;
        this.profileBankFieldName = profileBankFieldName;
        this.itemBankThumbnail = itemBankThumbnail;
        this.profileBankDetailsActionIcon = profileBankDetailsActionIcon;
    }

    public String getProfileBankFieldValue() {
        return profileBankFieldValue;
    }

    public void setProfileBankFieldValue(String profileBankFieldValue) {
        this.profileBankFieldValue = profileBankFieldValue;
    }

    public String getProfileBankFieldName() {
        return profileBankFieldName;
    }

    public void setProfileBankFieldName(String profileBankFieldName) {
        this.profileBankFieldName = profileBankFieldName;
    }

    public int getItemBankThumbnail() {
        return itemBankThumbnail;
    }

    public void setItemBankThumbnail(int itemBankThumbnail) {
        this.itemBankThumbnail = itemBankThumbnail;
    }

    public int getProfileBankDetailsActionIcon() {
        return profileBankDetailsActionIcon;
    }

    public void setProfileBankDetailsActionIcon(int profileBankDetailsActionIcon) {
        this.profileBankDetailsActionIcon = profileBankDetailsActionIcon;
    }
}
