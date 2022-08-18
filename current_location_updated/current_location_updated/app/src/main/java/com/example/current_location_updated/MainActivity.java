package com.example.current_location_updated;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //initialize a variable
    AudioManager am;

    TextView textView1, textView2, textView3, textView4, textView5
            ,textView8;
    FusedLocationProviderClient fusedLocationProviderClient;//to retrieve device location information
    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assign the variables
        am= (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);
        textView8 = findViewById(R.id.text_view8);

        //Initialize fusedlocationproviderclient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Check permissions
        if (ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission granted
            getLastLocation();

        } else {
            //when permission denied
            ActivityCompat.requestPermissions(MainActivity.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44); // lateerrrrrrrr

        }
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location!=null){
                                Geocoder geocoder=new Geocoder(MainActivity.this,Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    textView1.setText("Latitude : "+addresses.get(0).getLatitude());
                                    textView2.setText("Longitude : "+addresses.get(0).getLongitude());
                                    textView3.setText("Address : "+addresses.get(0).getAddressLine(0));
                                    textView4.setText("city : "+addresses.get(0).getLocality());
                                    textView5.setText("Country : "+addresses.get(0).getCountryName());





                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                double Latitude= addresses.get(0).getLatitude();
                                double Longitude= addresses.get(0).getLongitude();
                                if((Latitude>=30&&Latitude<=31)&&(Longitude>=31&&Longitude<=32))
                                {
                                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                    Toast.makeText(MainActivity.this, "Your phone is vibrated", Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    Toast.makeText(MainActivity.this, "Not vibrated", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                    });
        }
        else
        {
            askPermission();
        }

    }


    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}