package com.example.shop.store.scanfunctionality;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.shop.ParseImage;
import com.example.shop.R;
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;
import com.example.shop.store.StoreUser;
import com.example.shop.store.loginregister.WheelerRegistration;
import com.example.shop.store.remote.DbHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemDetaisEdit extends AppCompatActivity {

    ArrayAdapter<String> adapterType;

    Double oldPriceInt, newPriceInt;
    Double oldWeightPrice, newWeightPrice;
    Double excessPriceValue;
    String message;
    ProgressDialog loading;

    /**Wallet request code*/
    String WALLET_REQUEST_CODE = "00";

    /**Pricing rates*/
    String perDistance;
    String perWeight;
    String pricePerDistance;
    String pricePerWeight;

    /**Final values*/
    String finalDimention;
    String finalType;
    String finalweight;
    String finalPrice;

    String dimensionString, typeString, weightString, costString, packetIdString, descriptionString, imageString;
    String dimensionUnitString, lengthString, heightString, widthString;
    ImageView image;
    TextView price, excessPrice, orderId;
    EditText description;
    Spinner type;
    TextView weightUnit, dimentionUnit;
    EditText weight, length, width, height;
    Button actionButton;

    Toolbar itemDetailsToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_edit);

        itemDetailsToolbar = findViewById(R.id.toolbar);
        itemDetailsToolbar.setTitle("Item Details");
        setSupportActionBar(itemDetailsToolbar);
        itemDetailsToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        itemDetailsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initializeUi();

        /**progress dialog initialization*/
        loading = new ProgressDialog(ItemDetaisEdit.this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        /**Initializing type spinner*/

        final List<String> typeList = new ArrayList<String>();
        typeList.add("Select type");
        typeList.add("Document");
        typeList.add("Electronic devices");
        typeList.add("Medicines");
        typeList.add("Garments");
//        typeList.add("Item x");
//        typeList.add("Item y");
//        typeList.add("Item z");

        adapterType = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, typeList);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterType);

        String response = getIntent().getStringExtra("response");
        try {
            JSONObject jsonObject = new JSONObject(response);
            dimensionString = jsonObject.getString("dimentions");
            typeString = jsonObject.getString("type");
            weightString = jsonObject.getString("weight");
            costString = jsonObject.getString("cost");
            packetIdString = jsonObject.getString("packet_id");
            descriptionString = jsonObject.getString("about");
            imageString = jsonObject.getString("image");

            oldPriceInt = Double.valueOf(costString);

            String dim[] = dimensionString.split(" ");
            dimensionUnitString = dim[1];
            dim = dim[0].split("\\*");
            heightString = dim[0];
            widthString = dim[1];
            lengthString = dim[2];

        }catch (Exception e){

        }
        /**Setting original values*/
        setValues();

        /**On selecting type*/

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedType = type.getSelectedItem().toString();
                if(selectedType.equals("Select type")){
                    Toast.makeText(ItemDetaisEdit.this,"Please select a type", Toast.LENGTH_LONG).show();
                }else{
                    priceFetch(selectedType);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**On filling weight*/
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newWeightPrice = calculatePrice(weight.getText().toString().trim());
                excessPriceValue = newWeightPrice - oldWeightPrice;
                Log.println(Log.ASSERT,"calculated values:", String.valueOf(excessPriceValue)+"="+String.valueOf(newWeightPrice)+"-"+String.valueOf(oldWeightPrice));
                if(excessPriceValue < 0.0){
                    actionButton.setText("Pay Sender and confirm");
                    excessPrice.setText(String.valueOf(excessPriceValue));
                    message = "Pay sender an amount of Rs."+String.valueOf(excessPriceValue)+".It will be added on your wallet.";
                    WALLET_REQUEST_CODE = "045";
                }else if(excessPriceValue > 0.0){
                    actionButton.setText("Pay and confirm");
                    excessPrice.setText("+"+String.valueOf(excessPriceValue));
                    message = "Collect an amount of Rs."+String.valueOf(excessPriceValue)+" from sender.It will be deducted from your wallet.";
                    WALLET_REQUEST_CODE = "046";
                }else if(excessPriceValue == 0.0){
                    actionButton.setText("confirm");
                    excessPrice.setText("+"+String.valueOf(excessPriceValue));
                    message = "By clicking 'ok' you accept all the details provided is verified by you.";
                    WALLET_REQUEST_CODE = "00";
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ItemDetaisEdit.this);
                alertDialog.setTitle("Alert!");
                alertDialog.setMessage(message);
                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!WALLET_REQUEST_CODE.equals("00")){
                            addDeductFromWallet(WALLET_REQUEST_CODE);
                        }
                        updateParcelDetails();
                    }
                });
                alertDialog.show();

            }
        });
    }

    private void setValues() {
        new ParseImage(image).setImageString(imageString);
        price.setText(costString);
        excessPrice.setText("+0.00");
        orderId.setText(packetIdString);
        description.setText(descriptionString);
        type.setSelection(adapterType.getPosition(typeString));
        weight.setText(weightString);
        weightUnit.setText("gm");
        dimentionUnit.setText(dimensionUnitString);
        length.setText(lengthString);
        height.setText(heightString);
        width.setText(widthString);
        actionButton.setText("Confirm");
        priceFetch(typeString);
    }

    void initializeUi(){
        message = "By clicking 'ok' you accept all the details provided is verified by you.";
        image = findViewById(R.id.edit_image);
        price = findViewById(R.id.edit_price);
        excessPrice = findViewById(R.id.edit_excess_price);
        orderId = findViewById(R.id.edit_order_id);
        description = findViewById(R.id.edit_description);
        type = findViewById(R.id.edit_type);
        weightUnit = findViewById(R.id.edit_weight_unit);
        dimentionUnit = findViewById(R.id.edit_dimension_unit);
        weight = findViewById(R.id.edit_weight);
        length = findViewById(R.id.edit_length);
        width = findViewById(R.id.edit_width);
        height = findViewById(R.id.edit_height);
        actionButton = findViewById(R.id.edit_action_button);
    }

    /**Price fetch*/

    void priceFetch(String type){
        loading.show();
        new DbHelper(ItemDetaisEdit.this).priceFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                height.setText(height.getText().toString());
                width.setText(width.getText().toString());
                length.setText(length.getText().toString());
                JSONObject jsonObject = (JSONObject) result;
                try {
                    perDistance = jsonObject.getString("distance");
                    perWeight = jsonObject.getString("weight");
                    pricePerDistance = jsonObject.getString("price_distance");
                    pricePerWeight = jsonObject.getString("price_weight");
                    oldWeightPrice = calculatePrice(weightString);
                    Log.println(Log.ASSERT,"Values: ",perDistance+" "+perWeight+" "+pricePerDistance+" "+pricePerWeight);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading.dismiss();
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
        },type,"041");
    }

    /**Price Calculate*/
    Double calculatePrice(String wt){
        Double price = 0.00;
        try {
            int unitWeight =  (int) Math.ceil(Double.valueOf(wt) / Double.valueOf(perWeight));
            price = price + Double.valueOf(pricePerWeight) * unitWeight;
        }catch (Exception e){}
        return price;
    }

    /**Update parcel details*/
    void updateParcelDetails(){
        finalType = type.getSelectedItem().toString();
        finalDimention = height.getText().toString().trim()+"*"+width.getText().toString().trim()+"*"+
                length.getText().toString().trim()+" "+dimentionUnit.getText().toString().trim();
        finalweight = weight.getText().toString().trim();
        if(excessPriceValue != null) {
            Log.println(Log.ASSERT,"ITEM DETAILS PAGE:", "costString"+costString+"excessPriceValue"+excessPriceValue);
            newPriceInt = Double.valueOf(costString) + excessPriceValue;
        }else {
            newPriceInt = Double.valueOf(costString);
        }

        loading.show();
        new DbHelper(ItemDetaisEdit.this).updateParcelDetails(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                loading.dismiss();
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ItemDetaisEdit.this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Alert!");
                alertDialog.setMessage("Please scan QR code again.");
                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(ItemDetaisEdit.this, ScanQrCamera.class));
                        finish();
                    }
                });
                alertDialog.show();

            }

            @Override
            public void noDataFound() {
                loading.dismiss();
                Toast.makeText(ItemDetaisEdit.this,"Failed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
                Toast.makeText(ItemDetaisEdit.this,"Failed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                Toast.makeText(ItemDetaisEdit.this,"Failed",Toast.LENGTH_LONG).show();
            }
        },"045",finalType,finalDimention,finalweight,String.valueOf(newPriceInt),packetIdString,String.valueOf(excessPriceValue));
    }

    void addDeductFromWallet(String requestCode){
        new DbHelper(ItemDetaisEdit.this).addDeductFromWallet(String.valueOf(excessPriceValue),new StoreUser(ItemDetaisEdit.this).getPhone(),requestCode);
    }

}
