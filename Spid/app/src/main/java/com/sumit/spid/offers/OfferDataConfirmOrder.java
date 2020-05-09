package com.sumit.spid.offers;

import java.io.Serializable;

public class OfferDataConfirmOrder implements Serializable
{
    String offerTitle;
    String offerPromoCode;
    String offerDetails;
//    String termsOffers;

    public OfferDataConfirmOrder(String offerTitle, String offerPromoCode, String offerDetails) {
        this.offerTitle = offerTitle;
        this.offerPromoCode = offerPromoCode;
        this.offerDetails = offerDetails;
//        this.termsOffers = termsOffers;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getOfferPromoCode() {
        return offerPromoCode;
    }

    public void setOfferPromoCode(String offerPromoCode) {
        this.offerPromoCode = offerPromoCode;
    }

    public String getOfferDetails() {
        return offerDetails;
    }

    public void setOfferDetails(String offerDetails) {
        this.offerDetails = offerDetails;
    }

}
