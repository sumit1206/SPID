package com.sumit.spid.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.sumit.spid.MainActivity;
import com.sumit.spid.ParseImage;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.RemoteDataUpload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.offers.OfferDataConfirmOrder;
import com.sumit.spid.offers.OffersViewConfirmOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmationPageSearch extends AppCompatActivity {

    /**Request codes*/
    private static final int CAMERA_REQUEST_ONE = 1888;
    private static final int CAMERA_REQUEST_TWO = 1889;
    private static final int MY_CAMERA_PERMISSION_CODE = 110;
    private static final int PROMO_CODE_REQUEST = 100;

    Bitmap photo;
    Boolean firstImageEmpty;
    Boolean secondImageEmpty;
    String rawPrice;

    OfferDataConfirmOrder offerDataConfirmOrder;


    /**Bottom sheet values*/
    TextView from;
    TextView to;
    TextView totalPrice;
    TextView type;
    TextView dimension;
    TextView weight;
    CheckBox insurence;
    TextView insurancePrice;
    EditText value;
    CheckBox promocode;
//    TextView promocodePrice;
    TextView seeOffersConfirmOrder;
    EditText promocodeValue;
    EditText description, noteForCarrier;
    ImageView image1, image2;
    EditText receiverName, receiverNumber, receiverAddress, receiverPincode;
    TextView promocodeApply;
    TextView conformButton;
    LinearLayout promo_code_layout;
    /***/

    ParcelData parcelData;
    ProgressDialog loading;

    /**Parcel details stored in these variables*/
    String valueInsuranceOpted;
    String valueInsuranceCharge;
    String valueDescription;
    String valueNotesForCarrier;
    String valueImageOne;
    String valueImageTwo;
    String valueReceiverPhoneNumber;
    String valueReceiverName;
    String valueReceiverAddress;
    /***/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page_search);

        /**toolbar setup*/

        Toolbar toolbar  = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Confirm Order");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /**progress dialog initialization*/
        loading = new ProgressDialog(ConfirmationPageSearch.this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        initializeUiFields();
        firstImageEmpty = true;
        secondImageEmpty = true;
        parcelData = (ParcelData) getIntent().getSerializableExtra("parcelData");
        rawPrice = parcelData.valuePrice;
//        parcelData = getIntent().getParcelableArrayExtra("parcelData");

        type.setText(parcelData.valueType);
        dimension.setText(parcelData.valueDimention);
        weight.setText(parcelData.valueWeight);
        from.setText(parcelData.valueFromAddress);
        to.setText(parcelData.valueToAddress);
        totalPrice.setText(parcelData.valuePrice);

        /**Insurance price*/

        insurence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(insurence.isChecked()) {
                    updatePrice();
                    value.setVisibility(View.VISIBLE);
                }else{
                    parcelData.valuePrice = rawPrice;
                    insurancePrice.setText("");
                    totalPrice.setText(String.valueOf(parcelData.valuePrice));
                    value.setVisibility(View.GONE);
                }
            }
        });

        value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatePrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**Fetch promocode*/

        promocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(promocode.isChecked()) {
                    promo_code_layout.setVisibility(View.VISIBLE);
                }else{
                    promo_code_layout.setVisibility(View.GONE);
                }
            }
        });

        /**Capturing image*/

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_ONE);
                    }
                }
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_TWO);
                    }
                }
            }
        });

        /**Automatic profile fetch*/

        receiverNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(receiverNumber.getText().toString().trim().length() == 10){
                    if(receiverNumber.getText().toString().trim().equals(new User(ConfirmationPageSearch.this).getPhoneNumber())){
                        receiverNumber.setError("You cannot be receiver");
                    }else {
                        searchNameFromNumber(receiverNumber.getText().toString().trim());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**On conforming delivery request*/

        conformButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conformButton.setEnabled(false);
                conformButton.setVisibility(View.GONE);
                loading.show();
                boolean fieldsOK = allDataValid(new EditText[] { description, noteForCarrier, receiverName, receiverNumber, receiverAddress, receiverPincode });
                if(fieldsOK){
                    pickUpAllData();
                    conform();
                }else{
                    conformButton.setVisibility(View.VISIBLE);
                    conformButton.setEnabled(true);
                    loading.dismiss();
                    Toast.makeText(ConfirmationPageSearch.this,"Please fill all data",Toast.LENGTH_LONG).show();
                }
            }
        });

        /**View promoCodes**/

        seeOffersConfirmOrder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent offerIntent = new Intent (ConfirmationPageSearch.this, OffersViewConfirmOrder.class);
                startActivityForResult(offerIntent, PROMO_CODE_REQUEST);
            }
        });

        promocodeApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPromoPrice();
            }
        });
    }

    void conform(){
//        loading.show();
        new RemoteDataUpload(ConfirmationPageSearch.this).confirmingDelivery(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmationPageSearch.this);
//                alertDialog.setTitle("Request sent");
//                alertDialog.setMessage("Your delivery request has been sent, sid back and relax.We will conform you within 5 minutes.");
//                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        startActivity(new Intent(ConfirmationPageSearch.this,MainActivity.class));
//                        finish();
//                        SearchActivity.searchActivity.finish();
//                        parcelData.searchActivity.finish();
                loading.dismiss();
                startActivity(new Intent(ConfirmationPageSearch.this,WaitingPage.class));
//                SearchActivity.searchActivity.finish();
                finish();
//                    }
//                });

//                alertDialog.show();
            }

            @Override
            public void noDataFound() {
                loading.dismiss();
                conformButton.setVisibility(View.VISIBLE);
                conformButton.setEnabled(true);
                Toast.makeText(ConfirmationPageSearch.this,"Oops!server error occurred", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
                conformButton.setVisibility(View.VISIBLE);
                conformButton.setEnabled(true);
                Toast.makeText(ConfirmationPageSearch.this,"Oops!server error occurred", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                conformButton.setVisibility(View.VISIBLE);
                conformButton.setEnabled(true);
                Toast.makeText(ConfirmationPageSearch.this,"Oops!Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        },parcelData.serverDropTime,parcelData.serverDeliveryTime,parcelData.serverToStationList,parcelData.serverFromStationList,
                new User(ConfirmationPageSearch.this).getPhoneNumber(),valueDescription, parcelData.valueType,
                parcelData.valueWeight,valueReceiverPhoneNumber,parcelData.valuePrice,parcelData.valueDimention,valueInsuranceOpted,
                valueInsuranceCharge,valueNotesForCarrier,valueReceiverName,valueReceiverAddress,valueImageOne,valueImageTwo,
                parcelData.valueFromAddress,parcelData.valueToAddress,parcelData.toLatitude,parcelData.toLongitude);
    }

    void initializeUiFields(){

        from = findViewById(R.id.parcel_from);
        to = findViewById(R.id.parcel_to);
        totalPrice = findViewById(R.id.parcel_price);
        type = findViewById(R.id.parcel_type);
        dimension = findViewById(R.id.parcel_dimension);
        weight = findViewById(R.id.parcel_weight);
        insurence = findViewById(R.id.parcel_insurance_checkBox);
        insurancePrice = findViewById(R.id.parcel_insurance_price);
        value = findViewById(R.id.parcel_value);
        promocode = findViewById(R.id.parcel_promocode_checkBox);
        promo_code_layout = findViewById(R.id.layout_promo_code);
//        promocodePrice = findViewById(R.id.parcel_promocode_price);
        promocodeValue = findViewById(R.id.parcel_promocode);
        description = findViewById(R.id.parcel_description);
        noteForCarrier = findViewById(R.id.parcel_note_for_carrier);
        image1 = findViewById(R.id.parcel_image_one);
        image2 = findViewById(R.id.parcel_image_two);
        receiverNumber = findViewById(R.id.parcel_receiver_phone_no);
        receiverName = findViewById(R.id.parcel_receiver_name);
        receiverAddress = findViewById(R.id.parcel_receiver_address);
        receiverPincode = findViewById(R.id.parcel_receiver_pincode);
        promocodeApply = findViewById(R.id.bottom_sheet_promocode_apply_buttom);
        conformButton = findViewById(R.id.bottom_sheet_confirm_buttom);
        seeOffersConfirmOrder = findViewById(R.id.see_offers_confirm_order);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_ONE:
                    photo = (Bitmap) data.getExtras().get("data");
                    image1.setImageBitmap(photo);
                    firstImageEmpty = false;
                    break;
                case CAMERA_REQUEST_TWO:
                    photo = (Bitmap) data.getExtras().get("data");
                    image2.setImageBitmap(photo);
                    secondImageEmpty = false;
                    break;
                case PROMO_CODE_REQUEST:
                    offerDataConfirmOrder = (OfferDataConfirmOrder) data.getSerializableExtra("codeData");
                    promocodeValue.setText(offerDataConfirmOrder.getOfferPromoCode());
                    break;

            }
        }else
            Toast.makeText(ConfirmationPageSearch.this,"Photo capturing failed",Toast.LENGTH_LONG).show();

    }

    /**Picking up all data provided*/
    void pickUpAllData(){
//        loading.show();
            valueInsuranceOpted = (insurence.isChecked() ? "1" : "0");
            valueInsuranceCharge = (insurence.isChecked() ? insurancePrice.getText().toString().trim() : "");
            valueDescription = description.getText().toString().trim();
            valueNotesForCarrier = noteForCarrier.getText().toString().trim();
            valueReceiverPhoneNumber = receiverNumber.getText().toString().trim();
            valueReceiverName = receiverName.getText().toString().trim();
            valueReceiverAddress = receiverAddress.getText().toString().trim() +" "+ receiverPincode.getText().toString().trim();
//            valueDeliveryCharge = totalPrice.getText().toString().trim();
            valueImageOne = new ParseImage(image1).getImageString();
            valueImageTwo = new ParseImage(image2).getImageString();
        Geocoder geocoder = new Geocoder(getBaseContext());
        try {
            // Getting a maximum of 3 Address that matches the input
            // text
            Address address = geocoder.getFromLocationName(valueReceiverAddress, 1).get(0);
            if (address != null && !address.equals("")) {
                parcelData.toLatitude = String.valueOf(address.getLatitude());
                parcelData.toLongitude = String.valueOf(address.getLongitude());
            }else {
            }
//            loading.dismiss();
        } catch (Exception e) {
//            loading.dismiss();
            Log.println(Log.ERROR,"Catch exception:",e.getMessage());
        }

    }

    void checkPromoPrice(){
        loading.show();
        new RemoteDataDownload(ConfirmationPageSearch.this).promoCodePriceFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                JSONObject jsonObject = (JSONObject) result;
                try {
                    String discountAmount = jsonObject.getString("discount_amount");
                    Double discountPrice = Double.valueOf(discountAmount);
                    if(discountPrice > Double.valueOf(parcelData.valuePrice)){
                        discountPrice = Double.valueOf(parcelData.valuePrice);
                    }
                    parcelData.valuePrice = String.valueOf(Double.valueOf(parcelData.valuePrice) - discountPrice);
                    rawPrice = parcelData.valuePrice;
                    promocodeApply.setVisibility(View.GONE);
                    totalPrice.setText(String.valueOf(parcelData.valuePrice));
//                    totalPrice.setText(totalPrice.getText().toString()+" - "+discountAmount);
                    Toast.makeText(ConfirmationPageSearch.this, "Promocode applied", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(ConfirmationPageSearch.this, "success catch"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
                loading.dismiss();
            }

            @Override
            public void noDataFound() {
                loading.dismiss();
                Toast.makeText(ConfirmationPageSearch.this,"PromoCode invalid",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
                Toast.makeText(ConfirmationPageSearch.this,"Server error",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                Toast.makeText(ConfirmationPageSearch.this,"Internet error",Toast.LENGTH_LONG).show();
            }
        },"055","","",promocodeValue.getText().toString().trim());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_ONE);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean allDataValid(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().length() <= 0){
                currentField.setError("please fill this!");
                currentField.requestFocus();
                return false;
            }
        }
        if(firstImageEmpty || secondImageEmpty) {
            image1.requestFocus();
            Toast.makeText(ConfirmationPageSearch.this,"Give image",Toast.LENGTH_LONG).show();
            return false;
        }
        if(receiverNumber.getText().toString().length() != 10){
            receiverNumber.setError("Mobile number must be 10 digits");
            receiverNumber.requestFocus();
            return false;
        }
        if(receiverPincode.getText().toString().length() != 6){
            receiverPincode.setError("Pincode must be 6 digits");
            receiverPincode.requestFocus();
            return false;
        }
        if(insurence.isChecked() && value.getText().toString().trim().equals("")){
            value.setError("Value needed");
            value.requestFocus();
            return false;
        }
        Geocoder geocoder = new Geocoder(getBaseContext());
        try {
            // Getting a maximum of 3 Address that matches the input
            // text
            Address address = geocoder.getFromLocationName(parcelData.valueToAddress, 1).get(0);
            if (address != null && !address.equals("")) {
                String tempPin = address.getPostalCode();
                if(!tempPin.substring(0,1).equals(receiverPincode.getText().toString().substring(0,1))){
                    receiverPincode.setError("Pincode not in "+address.getAdminArea());
                    receiverPincode.requestFocus();
                    return false;
                }
            }else {
            }
        } catch (Exception e) {
            Log.println(Log.ERROR,"Catch exception:",e.getMessage());
        }
        return true;
    }

    void updatePrice(){
        Log.println(Log.ASSERT,"CALLED","updatePrice");
        String tValue = value.getText().toString().trim();
        Log.println(Log.ASSERT,"tValue",tValue);
        if(tValue.equals("")){
            Log.println(Log.ASSERT,"In if","setting price null");
            value.setError("Value cannot be empty");
            value.requestFocus();
            insurancePrice.setText("");
            parcelData.valuePrice = rawPrice;
            totalPrice.setText(String.valueOf(parcelData.valuePrice));
        }else {
            Double v = Double.valueOf(tValue);
            Double insPrice = 0.01 * v;
            Double newPrice = Double.valueOf(rawPrice) + insPrice;
            Log.println(Log.ASSERT,"v",String.valueOf(v));
            Log.println(Log.ASSERT,"insPrice",String.valueOf(insPrice));
            Log.println(Log.ASSERT,"newPrice",String.valueOf(newPrice));
            parcelData.valuePrice = String.valueOf(newPrice);
            totalPrice.setText(String.valueOf(parcelData.valuePrice));
            insurancePrice.setText(getString(R.string.rupees_symbol)+String.valueOf(insPrice));
        }

    }

    void searchNameFromNumber(String number){
        loading.show();
        new RemoteDataDownload(ConfirmationPageSearch.this).profileFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                loading.dismiss();
                JSONObject jsonObject = (JSONObject) result;
                try {
                    String name = jsonObject.getString("user_name");
                    String address = jsonObject.getString("User_address");
                    if(name != null && !name.equals("null") && !name.equals("")){
                        receiverName.setText(name);
                    }if(address != null && !address.equals("null") && !name.equals("")){
                        receiverAddress.setText(address);
                    }
                }catch (Exception e){

                }
                }

            @Override
            public void noDataFound() {
                loading.dismiss();
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
            }
        },"008",number);
    }

}
