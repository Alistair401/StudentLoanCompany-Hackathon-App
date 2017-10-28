package guts.carpaltunnel.mobileapp;

import android.Manifest;
import android.content.Context;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationActivity extends AppCompatActivity implements LocationListener {

    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final LocationListener thisClass = this;
        final Context thisCtx = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button button = findViewById(R.id.location);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    if(Build.VERSION.SDK_INT < 23 || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        TextView t = findViewById(R.id.locationtext);

                        while(location == null)
                            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, thisClass);

                        Geocoder geocoder = new Geocoder(thisCtx, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            t.setText("Your address is\n\n" + addresses.get(0).getAddressLine(0));
                        }
                        catch(IOException e){
                        }
                }
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
