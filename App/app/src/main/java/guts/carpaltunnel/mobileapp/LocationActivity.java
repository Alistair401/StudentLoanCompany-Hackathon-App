package guts.carpaltunnel.mobileapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import java.io.IOException;

import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity implements LocationListener{

    CallbackManager callbackManager;
    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_location);
        final LocationListener thisClass = this;
        final Context thisCtx = this;

        Button button = findViewById(R.id.location_button);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(View v) {
                if((Build.VERSION.SDK_INT > 23) || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                    mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, thisClass);
                    Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    TextView[] address = {findViewById(R.id.permanent_address),
                                          findViewById(R.id.address_city),
                                          findViewById(R.id.address_country),
                                          findViewById(R.id.address_postcode)};

                    if(location == null) {
                        address[0].setText("Location error :(");
                        return; }

                    Geocoder geocoder = new Geocoder(thisCtx, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(), 1);
                        address[0].setText(addresses.get(0).getAddressLine(0).split(",")[0]);
                        address[1].setText(addresses.get(0).getLocality());
                        address[2].setText(addresses.get(0).getCountryName());
                        address[3].setText(addresses.get(0).getPostalCode());
                        System.out.println(addresses.get(0));
                    } catch (IOException e) {
                        address[0].setText("GPS Error :(");
                    }
                }
            }
        });

        Button submit_form = findViewById(R.id.submit_form);
        submit_form.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SignatureActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
            mLocationManager.removeUpdates(this);
        }
    }

    // Required functions
    public void onProviderDisabled(String arg0) {}
    public void onProviderEnabled(String arg0) {}
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
}

