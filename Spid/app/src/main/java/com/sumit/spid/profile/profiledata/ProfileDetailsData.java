package com.sumit.spid.profile.profiledata;

import android.os.Parcelable;

import java.io.Serializable;

public class ProfileDetailsData implements Serializable {
    String profileFieldValue;
    String profileFieldName;
    int itemThumbnail;
    int profileDetailsActionIcon;

    String tableAttribute;

    public ProfileDetailsData(String profileFieldValue, String profileFieldName, int itemThumbnail, String tableAttribute) {
        this.profileFieldValue = profileFieldValue;
        this.profileFieldName = profileFieldName;
        this.itemThumbnail = itemThumbnail;
//        this.profileDetailsActionIcon = profileDetailsActionIcon;
        this.tableAttribute = tableAttribute;
    }

    public String getProfileFieldValue() {
        return profileFieldValue;
    }

    public void setProfileFieldValue(String profileFieldValue) {
        this.profileFieldValue = profileFieldValue;
    }

    public String getProfileFieldName() {
        return profileFieldName;
    }

    public void setProfileFieldName(String profileFieldName) {
        profileFieldName = profileFieldName;
    }

    public int getItemThumbnail() {
        return itemThumbnail;
    }

    public void setItemThumbnail(int itemThumbnail) {
        this.itemThumbnail = itemThumbnail;
    }

    public int getProfileDetailsActionIcon() {
        return profileDetailsActionIcon;
    }

    public void setProfileDetailsActionIcon(int profileDetailsActionIcon) {
        this.profileDetailsActionIcon = profileDetailsActionIcon;
    }
    public String getTableAttribute() {
        return tableAttribute;
    }

    public void setTableAttribute(String tableAttribute) {
        this.tableAttribute = tableAttribute;
    }

}
