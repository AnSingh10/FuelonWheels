package fire.auth.com.fuelonwheels;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Home extends AppCompatActivity {

    FirebaseFirestore mFire;
    TextView Name, price;
    Spinner mSpinner, aSpinner;
    String menu[] = {"Petrol", "Diesel"}, finalType, finalQty;
    String amount[] = {"1", "2", "3", "4"};
    Button mLocationBtn, mOrderBtn;
    EditText et_mLocation;
    FusedLocationProviderClient client;
    LocationRequest locationRequest;
    Geocoder geocoder;
    Location mLocation;

    Spinner capacity, fuel_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        requestPermission();

        Name = findViewById(R.id.homeName);
        price = findViewById(R.id.price);
        fuel_type = findViewById(R.id.hometype);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, menu);
        fuel_type.setAdapter(arrayAdapter);

        capacity = findViewById(R.id.homeCapacity);
        ArrayAdapter arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, amount);
        capacity.setAdapter(arrayAdapter1);



        mFire = FirebaseFirestore.getInstance();
        et_mLocation = findViewById(R.id.et_loction);

        mOrderBtn = findViewById(R.id.orderBtn);

        fuel_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finalType = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        capacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finalQty = parent.getSelectedItem().toString();
                if( finalType.equals("Petrol")) {
                    int finalRate = Integer.parseInt(finalQty) * 85;
                    price.setText(String.valueOf(finalRate));
                }else{
                    int finalRate = Integer.parseInt(finalQty) * 75;
                    price.setText(String.valueOf(finalRate));
                }
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                final Date resultdate = new Date(System.currentTimeMillis());

                Map<String, Object> orderMap = new HashMap<>();
                orderMap.put("latitude", String.valueOf(mLocation.getLatitude()));
                orderMap.put("longitude", String.valueOf(mLocation.getLongitude()));
                orderMap.put("address", et_mLocation.getText().toString());
                orderMap.put("fuel_type", fuel_type.getSelectedItem().toString());
                orderMap.put("capacity", capacity.getSelectedItem().toString());
                orderMap.put("dnt", sdf.format(resultdate));
                orderMap.put("price",price.getText().toString());
                orderMap.put("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());


                mFire.collection("orders").add(orderMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(Home.this, "Order Placed Successfully..:)", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(Home.this,ordersuccessful.class);
                        i.putExtra("type",fuel_type.getSelectedItem().toString());
                        i.putExtra("amount",capacity.getSelectedItem().toString());
                        i.putExtra("location",et_mLocation.getText().toString());
                        i.putExtra("latitude", String.valueOf(mLocation.getLatitude()));
                        i.putExtra("Time", String.valueOf(sdf.format(resultdate)));
                        i.putExtra("price",price.getText().toString());
                        startActivity(i);
                    }
                });
            }
        });



        geocoder = new Geocoder(this, Locale.getDefault());
        mLocationBtn = findViewById(R.id.getBtn);
        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

    }

    private void getLocation() {
        //location
        client = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        final LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        //TODO: UI updates.
                    }
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.requestLocationUpdates(locationRequest, mLocationCallback, null);
        client.getLastLocation().addOnSuccessListener(Home.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location !=null) {

                    try {
                        mLocation = location;
                        List<Address> addresse = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        String city = addresse.get(0).getLocality();
                        String area = addresse.get(0).getSubLocality();
                        String country = addresse.get(0).getCountryName();
                        String state = addresse.get(0).getAdminArea();
                        if(area != ""){
                            et_mLocation.setText(area + ", "+city + ", "+state+", "+country);
                        }else{
                            et_mLocation.setText(city + ", "+state+", "+country);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(Home.this, "Location not found, try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeHome:
                startActivity(new Intent(getApplicationContext(), Home.class));
                break;
            case R.id.homeProfile:
                startActivity(new Intent(getApplicationContext(), Profile.class));
                break;
            case R.id.homeOrders:
                startActivity(new Intent(getApplicationContext(), Myorder.class));
                break;
            case R.id.homeLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                default:
                    Toast.makeText(this, "Something went wrong..", Toast.LENGTH_SHORT).show();
        }
    return true;
    }


}
