package com.sumit.spid.search;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sumit.spid.ParseDateTimeStamp;
import com.sumit.spid.ParseImage;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.RemoteDataUpload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity implements OnMapReadyCallback {

    /**progress dialog*/
    ProgressDialog loading;

    /**permission codes*/
    final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 100;
    final int fromLocationRequestCode = 1;
    final int toLocationRequestCode = 2;
    /***/

    /**Pricing rates*/
    String perDistance;
    String perWeight;
    String pricePerDistance;
    String pricePerWeight;

    public static Activity searchActivity;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    Location mLastKnownLocation;
    private BottomSheetBehavior sheetBehavior;
    private LinearLayout buttom_sheet;
    Bitmap photo;
    BottomSheetDialog dialog;
    LocationManager locationManager;
    TextView from_address_search, to_address_search;
    Geocoder geocoder;
    ProgressBar progressBar;
    LatLng fromLocation;
    LatLng toLocation;
    boolean firstTime = true;
    GoogleMap googleMap;
    LatLongManager latLongManager;
    RemoteDataDownload remoteDataDownload;
    int totalPriceValue;
    RemoteDataUpload remoteDataUpload;
    User user;
    TextWatcher weightCalculater;
    double distance;
    ParcelData parcelData;

    /**Bottom sheet values*/
    LinearLayout inputLayout;
    TextView totalPrice;
    Spinner type;
    EditText height, width, length;
    Spinner dimensionUnit;
    EditText weight;
    Spinner weightUnit;
    TextView apply_botton_search;
    /***/

    /**Values to return to server*/
    String serverDeliveryTime;
    String serverDropTime;
    String serverFromStationList;
    String serverToStationList;
    /***/

    private List<SearchData> searchArryList = new ArrayList<>();
    private RecyclerView searchRecyclerView;
    private SearchAdapter searchAdapter;

    PriceEstimator priceEstimator;

    /**Parcel details stored in these variables*/
    String valueType;
    String valueDimention;
    String valueWeight;
    String valueInsuranceOpted;
    String valueInsuranceCharge;
    String valueDescription;
    String valueNotesForCarrier;
    String valueImageOne;
    String valueImageTwo;
    String valueReceiverPhoneNumber;
    String valueReceiverName;
    String valueReceiverAddress;
    String valueDeliveryCharge;
    String valueFromAddress;
    String valueToAddress;
    /***/


    /** error page credentials*/
    LinearLayout errorLinearLayout;
    ImageView errorImage;
    TextView errorMessageText,action_text_error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchActivity = this;

        /**progress dialog initialization*/
        loading = new ProgressDialog(SearchActivity.this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        /**Connecting map*/

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /**Initializing fused location api*/

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(SearchActivity.this);

        /**Initialize bottom sheet*/

        initializeVariables();
        initializeWeightCalculator();

        /**Where to*/

        from_address_search = findViewById(R.id.from_address);
        to_address_search = findViewById(R.id.to_address);
        progressBar = findViewById(R.id.from_address_progress);

        /**Where to progress bar*/

        progressBar.setVisibility(View.VISIBLE);

        /**error page credentials*/
        errorLinearLayout = findViewById(R.id.error_layout);
        errorImage = findViewById(R.id.error_image);
        errorMessageText = findViewById(R.id.message_error_loading);
        action_text_error = findViewById(R.id.action_text_error);

        /**Search filter setting*/
        if(getIntent().hasExtra("place")){
            String placeName = getIntent().getStringExtra("place");
            to_address_search.setHint("Where in "+placeName+"?");
        }



        /**Initializing spinners*/

        final List<String> typeList = new ArrayList<String>();
        typeList.add("Select type");
        typeList.add("Document");
        typeList.add("Electronic devices");
        typeList.add("Medicines");
        typeList.add("Garments");
//        typeList.add("Item x");
//        typeList.add("Item y");
//        typeList.add("Item z");

        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, typeList);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterType);

        final List<String> weightUnitList = new ArrayList<String>();
        weightUnitList.add("gm");
//        weightUnitList.add("Kg");

        ArrayAdapter<String> adapterWeightUnit = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, weightUnitList);
        adapterWeightUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightUnit.setAdapter(adapterWeightUnit);

        final List<String> dimensionUnitList = new ArrayList<String>();
        dimensionUnitList.add("mm");
        dimensionUnitList.add("cm");
        dimensionUnitList.add("inch");

        ArrayAdapter<String> adapterDimensionUnit = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dimensionUnitList);
        adapterDimensionUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dimensionUnit.setAdapter(adapterDimensionUnit);


        /**Initializing values*/

        remoteDataDownload = new RemoteDataDownload(SearchActivity.this);
        remoteDataUpload = new RemoteDataUpload(SearchActivity.this);
        user = new User(SearchActivity.this);
        priceEstimator = new PriceEstimator(totalPrice);
        latLongManager = new LatLongManager();
        fromLocation = null;
        toLocation = null;

        /**Search result recycler view*/

        searchRecyclerView = findViewById(R.id.search_result_recycler_view);
        searchAdapter = new SearchAdapter(SearchActivity.this, searchArryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setAdapter(searchAdapter);

        /**Address search events*/

        from_address_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchIntent.class);
                startActivityForResult(intent, fromLocationRequestCode);
            }
        });

        to_address_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchIntent.class);
                startActivityForResult(intent, toLocationRequestCode);
            }
        });

        /**On selecting type*/

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedType = type.getSelectedItem().toString();
                if(selectedType.equals("Select type")){
                    inputLayout.setVisibility(View.GONE);
//                    Toast.makeText(SearchActivity.this,"Please select a type",Toast.LENGTH_LONG).show();
                }else{
                    priceFetch(selectedType);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**On filling dimensions*/

        height.addTextChangedListener(weightCalculater);
        width.addTextChangedListener(weightCalculater);
        length.addTextChangedListener(weightCalculater);

        /**On selecting unit*/

        dimensionUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                height.setText(height.getText().toString());
                width.setText(width.getText().toString());
                length.setText(length.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**On applying values*/

        apply_botton_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(priceFieldsEmpty()){
                    Toast.makeText(SearchActivity.this,"Please fill all details",Toast.LENGTH_LONG).show();
                }else{
                    Intent conformingIntent = new Intent(SearchActivity.this, ConfirmationPageSearch.class);
                    conformingIntent.putExtra("parcelData", parcelData);
                    startActivity(conformingIntent);
                    /**Showing price when parcel is defined*/
                }
            }
        });

        /**On filling weight*/
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                priceCalculator();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

    }

    /**Bottom sheet variable initialization*/

    void initializeVariables(){

        buttom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(buttom_sheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if(i == BottomSheetBehavior.STATE_DRAGGING)
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
        totalPrice = findViewById(R.id.total_price);
        type = findViewById(R.id.parcel_type_spinner);
        inputLayout = findViewById(R.id.input_layout);
        height = findViewById(R.id.parcel_height);
        width = findViewById(R.id.parcel_width);
        length = findViewById(R.id.parcel_length);
        dimensionUnit = findViewById(R.id.parcel_dimension_unit);
        weight = findViewById(R.id.parcel_weight);
        weightUnit = findViewById(R.id.parcel_weight_unit);
        apply_botton_search = findViewById(R.id.apply_button_search);
    }

    /**Automatic location fetching part*/

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        try {
                            mLastKnownLocation = task.getResult();
                            geocoder = new Geocoder(SearchActivity.this, Locale.getDefault());
                            try {
                                Address myAddress = geocoder.getFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), 1).get(0);
                                if (fromLocation == null) {
                                    from_address_search.setText(myAddress.getSubLocality() + "," + myAddress.getLocality() + "," + myAddress.getAdminArea());
                                    fromLocation = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                                    addMarker();
                                    if (fromLocation != null && toLocation != null) {
                                        search();
                                    }
                                }
                                progressBar.setVisibility(View.INVISIBLE);
                            } catch (IOException e) {
                                progressBar.setVisibility(View.INVISIBLE);
                                e.printStackTrace();
                            }

                            LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            Log.println(Log.ASSERT, "If", latLng.latitude + "+" + latLng.longitude);
                        }catch (Exception e){
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchActivity.this);
                            alertDialog.setTitle("Sorry!");
                            alertDialog.setMessage("A problem occurred while fetching your location, please open Google map and calibrate");
                            alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            SearchActivity.super.onBackPressed();
                            alertDialog.show();
                        }
                    } else {
                        Log.println(Log.ASSERT, "else","task not successful");
                    }
                }
            });
        } catch (SecurityException e)  {
            Log.println(Log.ASSERT, "Catch",Log.getStackTraceString(e));
        }
    }


    /**Manual address searching*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Address address;
            Double lat, lon;
            switch (requestCode) {
                case fromLocationRequestCode:
                    address = data.getParcelableExtra("address");
                    lat = address.getLatitude();
                    lon = address.getLongitude();
                    fromLocation = new LatLng(lat, lon);
                    from_address_search.setText(address.getAddressLine(0));
                    addMarker();
                    if (fromLocation != null && toLocation != null) {
                        search();
                    }
                    break;
                case toLocationRequestCode:
                    address = data.getParcelableExtra("address");
                    lat = address.getLatitude();
                    lon = address.getLongitude();
                    toLocation = new LatLng(lat, lon);
                    to_address_search.setText(address.getAddressLine(0));
                    addMarker();
                    if (fromLocation != null && toLocation != null) {
                        search();
                    }
                    break;
                default:
                    Toast.makeText(SearchActivity.this, "default", Toast.LENGTH_LONG).show();
            }
          }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**Initializing Map*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                getDeviceLocation();
                return false;
            }
        });
        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(new LatLng(20.5937,78.9629), 4);
        googleMap.animateCamera(cameraPosition);
        googleMap.moveCamera(cameraPosition);
        this.googleMap = googleMap;
        getDeviceLocation();
    }

    /**Adding marker on map*/

    void addMarker() {
        int zoom = 15;
        googleMap.clear();
        LatLng latLng;

        if (fromLocation == null && toLocation == null) {
            latLng = new LatLng(0, 0);
        } else if (fromLocation == null) {
            latLng = toLocation;
        } else if (toLocation == null) {
            latLng = fromLocation;
        } else {
            Double lat = (fromLocation.latitude + toLocation.latitude) / 2;
            Double lon = (fromLocation.longitude + toLocation.longitude) / 2;
            latLng = new LatLng(lat, lon);
            Double distance = latLongManager.findDistance(fromLocation.latitude, toLocation.latitude,
                    fromLocation.longitude, toLocation.longitude, 0, 0);
            double scale = (distance / 2) / 500;
            zoom = (int) (16 - Math.log(scale) / Math.log(2));

            PolylineOptions polyline = new PolylineOptions();
            polyline.geodesic(true)
                    .color(R.color.green_color)
                    .width(20)
                    .startCap(new RoundCap())
                    .endCap(new RoundCap())
                    .jointType(JointType.ROUND)
                    .add(fromLocation, toLocation);
            googleMap.addPolyline(polyline);
        }
        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        googleMap.animateCamera(cameraPosition);
        googleMap.moveCamera(cameraPosition);

        if (fromLocation != null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(fromLocation);
            markerOptions.title("from here");

            googleMap.addMarker(markerOptions);
        }
        if (toLocation != null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(toLocation);
            markerOptions.title("to here");

            googleMap.addMarker(markerOptions);
        }
    }

    /**Searching and adding search result on recycler view adapter*/

    void search() {
        distance = latLongManager.findDistance(fromLocation.latitude, toLocation.latitude,
                fromLocation.longitude, toLocation.longitude, 0, 0);
        searchArryList.clear();
        loading.show();
        errorLinearLayout.setVisibility(View.GONE);
        remoteDataDownload.carrierSearch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);
                        String deliveryTime = post.getString("duration_h");
                        String dropTimeGroup = post.getString("departure_group");
                        String dropTimeGroupArray[] = dropTimeGroup.split(",");
                        for (String dropTime: dropTimeGroupArray) {
                            SearchData searchData = new SearchData(deliveryTime, dropTime);
                            searchArryList.add(searchData);
                            searchAdapter.notifyDataSetChanged();
                        }
                    }
                    serverFromStationList = jsonObject.getString("from_stn_comma_seperated");
                    serverToStationList = jsonObject.getString("to_stn_comma_seperated");
                    loading.dismiss();
                } catch (Exception e) {
                    Toast.makeText(SearchActivity.this, "success catch", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {
                loading.dismiss();
                searchAdapter.notifyDataSetChanged();
//                Toast.makeText(SearchActivity.this, "no data found", Toast.LENGTH_LONG).show();
                errorMessageText.setText(R.string.no_carrier);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(SearchActivity.this).load(R.raw.walking).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_carrier);
//                action_text_error.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        search();
//                    }
//                });
                errorLinearLayout.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
                searchAdapter.notifyDataSetChanged();
//                Toast.makeText(SearchActivity.this, "catch", Toast.LENGTH_LONG).show();

                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(SearchActivity.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                searchAdapter.notifyDataSetChanged();
                e.printStackTrace();
//                Toast.makeText(SearchActivity.this, "error" + e.getMessage() + e.getCause(), Toast.LENGTH_LONG).show();

                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(SearchActivity.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);
            }
        }, fromLocation.latitude, fromLocation.longitude, toLocation.latitude, toLocation.longitude);
    }

    /**Validate data provided*/

    boolean priceFieldsEmpty(){

        valueType = type.getSelectedItem().toString().trim();
        valueDimention = height.getText().toString().trim() + "*" + width.getText().toString().trim() + "*" + length.getText().toString().trim() +
                " "+dimensionUnit.getSelectedItem().toString();
        valueWeight = weight.getText().toString() + " " + weightUnit.getSelectedItem().toString().trim();
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            Address myAddress = geocoder.getFromLocation(fromLocation.latitude, fromLocation.longitude, 1).get(0);
            valueFromAddress = myAddress.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        try {
            Address myAddress = geocoder.getFromLocation(toLocation.latitude, toLocation.longitude, 1).get(0);
            valueToAddress = myAddress.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        valueDeliveryCharge = totalPrice.getText().toString().trim();
        if(weight.getText().toString().isEmpty() || weight.getText().toString().trim().equals(""))
        {return true;}

        parcelData = new ParcelData(serverDropTime,serverDeliveryTime,serverToStationList,serverFromStationList,valueDeliveryCharge,valueType,
                valueDimention, valueWeight,valueFromAddress,valueToAddress,String.valueOf(toLocation.latitude),String.valueOf(toLocation.longitude));

        return false;
    }

    /**When swap button pressed*/

    public void swap(View view) {
            String tempString;
            LatLng tempLatLng;

            tempLatLng = fromLocation;
            fromLocation = toLocation;
            toLocation = tempLatLng;

            tempString = from_address_search.getText().toString();
            from_address_search.setText(to_address_search.getText().toString());
            to_address_search.setText(tempString);

            if (fromLocation != null && toLocation != null) {
                search();
            }
    }

    /**Weight Calculator*/

    void initializeWeightCalculator(){
        weightCalculater = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateWeight();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculateWeight();
//                Toast.makeText(SearchActivity.this,"set",Toast.LENGTH_LONG).show();
            }
        };
    }

    void calculateWeight(){
        try {
            Double d = 0.00; //Density in g/cm^3
            Double h = Double.valueOf(height.getText().toString().trim()); //height in cm
            Double w = Double.valueOf(width.getText().toString().trim()); // width in cm
            Double l = Double.valueOf(length.getText().toString().trim()); // length in cm

            String t = type.getSelectedItem().toString();
            String dU = dimensionUnit.getSelectedItem().toString();

            if (dU.equals("cm")) {

            } else if (dU.equals("mm")) {
                h = h / 10;
                w = w / 10;
                l = l / 10;
            } else if (dU.equals("inch")) {
                h = h * 2.54;
                w = w * 2.54;
                l = l * 2.54;
            }

            if (t.equals("Document")) {
                d = 1.201; //Density in g/cm^3
            } else if (t.equals("Electronic devices")) {
                d = 2.00; //Density in g/cm^3
            } else if (t.equals("Medicines")) {
                d = 1.014; //Density in g/cm^3
            } else if (t.equals("Garments")) {
                d = 1.22; //Density in g/cm^3
            } else if (t.equals("Item x")) {
                d = 3.00; //Density in g/cm^3
            } else if (t.equals("Item y")) {
                d = 3.50; //Density in g/cm^3
            } else if (t.equals("Item z")) {
                d = 4.00; //Density in g/cm^3
            } else {
                d = 1.00; //Density in g/cm^3
            }

            Double weightValue = h * w * l * d;
            weightValue = Math.round(weightValue*100) /100.00;
            weight.setText(String.valueOf(weightValue));
        }catch (Exception e){

        }
    }

    /**Price fetch*/

    void priceFetch(String type){
        loading.show();
        new RemoteDataDownload(SearchActivity.this).priceFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                height.setText(height.getText().toString());
                width.setText(width.getText().toString());
                length.setText(length.getText().toString());
                inputLayout.setVisibility(View.VISIBLE);
                JSONObject jsonObject = (JSONObject) result;
                try {
                    perDistance = jsonObject.getString("distance");
                    perWeight = jsonObject.getString("weight");
                    pricePerDistance = jsonObject.getString("price_distance");
                    pricePerWeight = jsonObject.getString("price_weight");
                    priceCalculator();
                    Log.println(Log.ASSERT,"Values: ",perDistance+" "+perWeight+" "+pricePerDistance+" "+pricePerWeight+" "+distance);
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
    void priceCalculator(){
        Double price = 0.00;
        try {
            int unitDist = (int)( Math.ceil((distance/1000) / Double.valueOf(perDistance)));
            price = price + Double.valueOf(pricePerDistance) * unitDist;
        }catch(Exception e){}
        try {
           int unitWeight =  (int) Math.ceil(Double.valueOf(weight.getText().toString().trim()) / Double.valueOf(perWeight));
           price = price + Double.valueOf(pricePerWeight) * unitWeight;
        }catch (Exception e){}
        totalPrice.setText(String.valueOf(price));
    }


    /**Performing whenever search page is triggered */

    @Override
    protected void onResume() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(!firstTime) {
                getDeviceLocation();
            }
        } else {
            if (firstTime) {
                firstTime = false;
                ActivityCompat.requestPermissions(SearchActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_LOCATION);
            } else {
                progressBar.setVisibility(View.GONE);
                from_address_search.requestFocus();
                super.onBackPressed();
            }
        }

        /**Hides keyboard*/
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        super.onResume();
    }

    /**Performing when back pressed*/

    @Override
    public void onBackPressed() {
        if(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        else
            super.onBackPressed();
    }


    ///////////////////////////////////////////////////////
/////////////////////////////Adapter////////////////////////////
    ///////////////////////////////////////////////////////
    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
        private Context searchContext;
        private List<SearchData> searchArrayList;
        int selectedPosition=-1;

        public SearchAdapter(Context searchContext, List<SearchData> searchArryList) {
            this.searchContext = searchContext;
            this.searchArrayList = searchArryList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_search_result_layout, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
            SearchData searchData = searchArrayList.get(position);
            holder.indicator_image.setBackgroundColor(Color.parseColor("#FF8D00"));
            if(selectedPosition==position)
                holder.indicator_image.setBackgroundColor(Color.parseColor("#FF8D00"));
            else
                holder.indicator_image.setBackgroundColor(Color.parseColor("#FFCC99"));

//            holder.cardSearchResult.setAnimation(AnimationUtils.loadAnimation(searchContext, R.anim.anim_slide_in_right));
            holder.deliveryTimeSearch.setAnimation(AnimationUtils.loadAnimation(searchContext, R.anim.fade_scale_animation));
            holder.deliveryTimeSearch.setText(searchData.getDeliveryTime()+" hrs");
            String t = new ParseDateTimeStamp(searchData.getdropTime()).getReadableDropTime();
            holder.dropTimeSearch.setText(t);

//            Glide.with(this).load(R.raw.gif_splash).into(imageViewTarget);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPosition = position;
                    notifyDataSetChanged();
                    SearchData searchDataClicked = searchArrayList.get(position);
                    serverDeliveryTime = searchDataClicked.getDeliveryTime();
                    serverDropTime = searchDataClicked.getdropTime();

                    /**Showing price when delivery time is selected*/

                    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return searchArrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView deliveryTimeSearch,dropTimeSearch;
            CardView cardSearchResult;
            ImageView delivery_image,indicator_image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                cardSearchResult = itemView.findViewById(R.id.card_search_result);
                deliveryTimeSearch = itemView.findViewById(R.id.delivery_time_search);
                dropTimeSearch = itemView.findViewById(R.id.drop_time_search);
                indicator_image = itemView.findViewById(R.id.indicator_image);
                delivery_image = itemView.findViewById(R.id.delivery_gif);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(delivery_image);
                Glide.with(searchContext).load(R.raw.delivery).into(imageViewTarget);
            }
        }
    }
}
