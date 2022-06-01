package com.dbot.client.main.profile.refer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RcAndRhData {
    @SerializedName("my_referral_code")
    @Expose
    private String myReferralCode;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("claimed")
    @Expose
    private Integer claimed;
    @SerializedName("history")
    @Expose
    private List<ReferalHistory> history = null;

    public String getMyReferralCode() {
        return myReferralCode;
    }

    public void setMyReferralCode(String myReferralCode) {
        this.myReferralCode = myReferralCode;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getClaimed() {
        return claimed;
    }

    public void setClaimed(Integer claimed) {
        this.claimed = claimed;
    }

    public List<ReferalHistory> getReferalHistory() {
        return history;
    }

    public void setReferalHistory(List<ReferalHistory> history) {
        this.history = history;
    }
}
