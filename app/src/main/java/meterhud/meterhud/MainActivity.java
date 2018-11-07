package meterhud.meterhud;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textSpeed;
    private LocationManager LM;
    private double meterPerSecound, kilometerPerHour;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        textSpeed = (TextView)findViewById(R.id.textspeed);
        setLocation();
    }

    public void setLocation() {
        try {
            LM = (LocationManager) getSystemService(LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                boolean turnOnGPS = true;
                @Override
                public void onLocationChanged(Location location) {
                    if(turnOnGPS) {
                        Toast.makeText(getApplicationContext(), "GPS gotowy", Toast.LENGTH_SHORT).show();
                        turnOnGPS=false;
                    }

                    meterPerSecound = location.getSpeed(); //m/s
                    kilometerPerHour = (meterPerSecound * 1000) / 3600; //km/h
                    int roundKilometerPerHour;
                    roundKilometerPerHour= (int)kilometerPerHour;
                    textSpeed.setText(""+roundKilometerPerHour);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
            LM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch(SecurityException e) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}
