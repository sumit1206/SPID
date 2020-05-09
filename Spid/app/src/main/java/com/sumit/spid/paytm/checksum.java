package com.sumit.spid.paytm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.sumit.spid.R;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.RemoteDataUpload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.mydelivery.MyDeliveryDetails;
import com.sumit.spid.mydelivery.data.MyDeliveryData;
import com.sumit.spid.wallet.Wallet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Checksum;

//import android.support.v7.app.AppCompatActivity;

/**
 * Paytm payment gateway
 */
public class checksum extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    String custid="", orderId="",amount="", mid="";
    String eventId;
    TextView transactionStatus,tryAgainBtn;
    ImageView transactionActionImage;
    Boolean payingForDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checksum_layout);
        //initOrderId();

        payingForDelivery = false;

        transactionStatus = findViewById(R.id.transaction_status);
        transactionActionImage = findViewById(R.id.action_transaction);

        tryAgainBtn = findViewById(R.id.try_again_btn);
        tryAgainBtn.setVisibility(View.GONE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent intent = getIntent();

        if(intent.hasExtra("eventId")) {
            eventId = intent.getExtras().getString("eventId");
            payingForDelivery = true;
        }
        orderId = intent.getExtras().getString("orderid");
        custid = intent.getExtras().getString("custid");
        amount = intent.getExtras().getString("amount");
//        mid = "QxlxwF29799600365210"; /// your marchant id
        mid = "TreGqr85522609535577";
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
// vollye , retrofit, asynch
    }
    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {
        private ProgressDialog dialog = new ProgressDialog(checksum.this);
        //private String orderId , mid, custid, amt;
        String url ="http://197.189.202.8:8080/courserv/Paytm_Checksum/generateChecksum.php";
//        String varifyurl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
//        String callBackUrl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        String callBackUrl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        String CHECKSUMHASH ="";
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }
        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParserPaytm jsonParser = new JSONParserPaytm(checksum.this);
            String param=
                    "MID="+mid+
                            "&ORDER_ID=" + orderId+
                            "&CUST_ID="+custid+
                            "&CHANNEL_ID=WAP&TXN_AMOUNT="+amount+
                            "&WEBSITE=WEBSTAGING"+
                            "&CALLBACK_URL="+ callBackUrl+"&INDUSTRY_TYPE_ID=Retail";
            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum ke saht order id or status receive hoga..
            Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
                Log.e("CheckSum result >>",jsonObject.toString());
                try {
                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("CheckSum result >>",CHECKSUMHASH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
//            PaytmPGService Service = PaytmPGService.getStagingService();
            PaytmPGService Service = PaytmPGService.getProductionService();
            // when app is ready to publish use production service
            // PaytmPGService  Service = PaytmPGService.getProductionService();
            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", amount);
            paramMap.put("WEBSITE", "WEBSTAGING");
//            WEBSTAGING
            paramMap.put("CALLBACK_URL" ,callBackUrl);
            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
            // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            // start payment service call here
            Service.startPaymentTransaction(checksum.this, true, true,
                    checksum.this  );
        }
    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
        Log.e("checksum ", " respon true " + bundle.toString());
        String transactionStatusValue = bundle.getString("STATUS");
//        Toast.makeText(getApplicationContext(),"onTransactionResponse" + bundle.getString("STATUS"),Toast.LENGTH_LONG).show();
        if(transactionStatusValue.equalsIgnoreCase("TXN_FAILURE")){
//            R.layout.checksum_layout.
            transactionStatus = findViewById(R.id.transaction_status);
            transactionActionImage = findViewById(R.id.action_transaction);

            transactionStatus.setText("Transaction Failed");
            transactionStatus.setTextColor(Color.RED);
            transactionActionImage.setImageResource(R.drawable.error_black);
        }
        else if(transactionStatusValue.equalsIgnoreCase("TXN_SUCCESS")){
            if(payingForDelivery) {
                updatePaymentStatus();
            }else {
                addMoneyOnWallet();
            }

            transactionStatus.setText("Transaction Successful");
            transactionStatus.setTextColor(Color.BLACK);
            transactionActionImage.setImageResource(R.drawable.check_circle);
        }
        else{
            transactionStatus.setText("Transaction Incomplete");
            transactionStatus.setTextColor(Color.YELLOW);
            transactionActionImage.setImageResource(R.drawable.warning);
        }
    }
    @Override
    public void networkNotAvailable() {
    }
    @Override
    public void clientAuthenticationFailed(String s) {
    }
    @Override
    public void someUIErrorOccurred(String s) {
        Log.e("checksum ", " ui fail respond  "+ s );
    }
    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
    }
    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum ", " cancel call back respond  " );
    }
    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.e("checksum ", "  transaction cancel " );
    }

    void updatePaymentStatus(){
        new RemoteDataDownload(checksum.this).notificationsFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                MyDeliveryDetails.myDeliveryData.setPaidStatus("1");
                transactionStatus.setText("Transaction Successful.");
                transactionStatus.setTextColor(Color.BLACK);
                tryAgainBtn.setVisibility(View.VISIBLE);
                tryAgainBtn.setText("Go my delivery");
                tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
                transactionActionImage.setImageResource(R.drawable.check_circle);
            }

            @Override
            public void noDataFound() {
                transactionStatus.setText("Transaction suspended due to network error,add a screen shot of this page in the bug report!\n" + orderId);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(transactionActionImage);
                Glide.with(checksum.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                tryAgainBtn.setVisibility(View.VISIBLE);
                tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }

            @Override
            public void onCatch(JSONException e) {
                transactionStatus.setText("Transaction suspended due to network error,add a screen shot of this page in the bug report!\n" + orderId);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(transactionActionImage);
                Glide.with(checksum.this).load(R.raw.slow_internet_error).into(imageViewTarget);

                tryAgainBtn.setVisibility(View.VISIBLE);
                tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }

            @Override
            public void onError(VolleyError e) {
                transactionStatus.setText("Transaction suspended due to network error,add a screen shot of this page in the bug report!\n" + orderId);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(transactionActionImage);
                Glide.with(checksum.this).load(R.raw.slow_internet_error).into(imageViewTarget);

                tryAgainBtn.setVisibility(View.VISIBLE);
                tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }
        },"","024",eventId,"","");
    }

    void addMoneyOnWallet(){
        new RemoteDataUpload(checksum.this).addMoneyOnWallet(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                transactionStatus.setText("Transaction Successful.");
                transactionStatus.setTextColor(Color.BLACK);
                tryAgainBtn.setVisibility(View.VISIBLE);
                tryAgainBtn.setText("Return to Wallet");
                tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(checksum.this, Wallet.class));
                    }
                });
                transactionActionImage.setImageResource(R.drawable.check_circle);

            }

            @Override
            public void noDataFound()
            {
                transactionStatus.setText("Transaction suspended due to network error,add a screen shot of this page in the bug report!\n" + orderId);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(transactionActionImage);
                Glide.with(checksum.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                tryAgainBtn.setVisibility(View.VISIBLE);
                tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }

            @Override
            public void onCatch(JSONException e) {
                transactionStatus.setText("Transaction suspended due to network error,add a screen shot of this page in the bug report!\n" + orderId);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(transactionActionImage);
                Glide.with(checksum.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                tryAgainBtn.setVisibility(View.VISIBLE);
                tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }

            @Override
            public void onError(VolleyError e) {
                transactionStatus.setText("Transaction suspended due to network error,add a screen shot of this page in the bug report!\n" + orderId);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(transactionActionImage);
                Glide.with(checksum.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                tryAgainBtn.setVisibility(View.VISIBLE);
                tryAgainBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

            }
        },amount,custid,"045");
    }
}
