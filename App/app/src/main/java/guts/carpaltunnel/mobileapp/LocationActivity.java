package guts.carpaltunnel.mobileapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import guts.carpaltunnel.mobileapp.util.FormManager;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    Activity ctx = this;

    CallbackManager callbackManager;
    LocationManager mLocationManager;

    RadioGroup groupRadio;
    EditText termAddress;
    EditText termCity;
    EditText termCountry;
    EditText termPostcode;

    TextView addr0, addr1, addr2, addr3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_location);
        final LocationListener thisClass = this;
        final Context thisCtx = this;

        groupRadio = findViewById(R.id.groupRadio);

        termAddress = findViewById(R.id.t_address);
        termCity = findViewById(R.id.t_address_city);
        termCountry = findViewById(R.id.t_address_country);
        termPostcode = findViewById(R.id.t_address_postcode);

        termAddress.setVisibility(View.GONE);
        termCity.setVisibility(View.GONE);
        termCountry.setVisibility(View.GONE);
        termPostcode.setVisibility(View.GONE);

        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.noBtn) {
                    termAddress.setVisibility(View.VISIBLE);
                    termCity.setVisibility(View.VISIBLE);
                    termCountry.setVisibility(View.VISIBLE);
                    termPostcode.setVisibility(View.VISIBLE);
                }
                else
                {
                    termAddress.setVisibility(View.GONE);
                    termCity.setVisibility(View.GONE);
                    termCountry.setVisibility(View.GONE);
                    termPostcode.setVisibility(View.GONE);
                }
            }
        });

        Button button = findViewById(R.id.location_button);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            public void onClick(View v) {
                CharSequence colors[] = new CharSequence[] {"Permanent", "Term"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Pick a color");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        if((Build.VERSION.SDK_INT < 23) || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {
                            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, thisClass);
                            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            TextView[] address = {findViewById(R.id.t_address),
                                    findViewById(R.id.t_address_city),
                                    findViewById(R.id.t_address_country),
                                    findViewById(R.id.t_address_postcode)};

                            if (which == 0) {
                                address[0] = findViewById(R.id.p_address);
                                address[1] = findViewById(R.id.p_address_city);
                                address[2] = findViewById(R.id.p_address_country);
                                address[3] = findViewById(R.id.p_address_postcode);
                            } else if (which == 1) {
                                address[0] = findViewById(R.id.t_address);
                                address[1] = findViewById(R.id.t_address_city);
                                address[2] = findViewById(R.id.t_address_country);
                                address[3] = findViewById(R.id.t_address_postcode);
                            }

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
                builder.show();
            }
        });

        Button submit_form = findViewById(R.id.submit_form);
        submit_form.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), FirstContactActivity.class);
                FormManager formManager = ((HSApplication) getApplicationContext()).formManager;
                formManager.setField("perm_address", addr0.getText() + ", " + addr1.getText() + ", " + addr2.getText() + ", " + addr3.getText());
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
    public void onProviderDisabled(String arg0) {
    }

    public void onProviderEnabled(String arg0) {
    }

    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    }
}

