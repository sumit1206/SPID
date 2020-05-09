//package com.sumit.spid.offers;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AnimationUtils;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.sumit.spid.R;
//import com.sumit.spid.history.HistoryAdapter;
//import com.sumit.spid.history.HistoryData;
//
//import java.util.List;
//
//public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {
//
//    private Context offerContext;
//    private List<OfferDataConfirmOrder> offerArrayList;
//    public OfferAdapter(Context offerContext, List<OfferDataConfirmOrder> offerArrayList) {
//        this.offerContext = offerContext;
//        this.offerArrayList = offerArrayList;
//    }
//
//    @NonNull
//    @Override
//    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_offers_view_confirm_order, parent, false);
//        return new OfferAdapter.OfferViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
//
//        OfferDataConfirmOrder offerDataConfirmOrder = offerArrayList.get(position);
//        holder.offer_card.setAnimation(AnimationUtils.loadAnimation(offerContext, R.anim.fade_in));
//        holder.offerTitle.setText(offerDataConfirmOrder.getOfferTitle());
//        holder.offerPromoCode.setText(offerDataConfirmOrder.getOfferPromoCode());
//        holder.offerDetails.setText(offerDataConfirmOrder.offerDetails);
//    }
//
//    @Override
//    public int getItemCount() {
//        return offerArrayList.size();
//    }
//
//    public static class OfferViewHolder extends RecyclerView.ViewHolder {
//
//        TextView offerTitle,offerPromoCode,offerDetails,termsOffer;
//        CardView offer_card;
//
//        public OfferViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            offerTitle = itemView.findViewById(R.id.offer_title);
//            offerPromoCode = itemView.findViewById(R.id.promo_code);
//            offerDetails = itemView.findViewById(R.id.offer_details_confirm_order);
//            termsOffer = itemView.findViewById(R.id.terms_offer);
//            offer_card = itemView.findViewById(R.id.offer_card);
//
//        }
//    }
//}
