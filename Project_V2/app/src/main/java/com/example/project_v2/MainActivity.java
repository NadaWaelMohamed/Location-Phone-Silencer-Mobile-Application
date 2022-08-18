package com.example.project_v2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    EditText id, Latitude, Longitude;
    Button insert, update, delete, view, Vibrate;
    DB DB;
    TextView tvLatitude;
    FusedLocationProviderClient fusedLocationProviderClient;//to retrieve device location information
    AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        am= (AudioManager)getSystemService(Context.AUDIO_SERVICE);


        id = findViewById(R.id.User_ID);
        Latitude = findViewById(R.id.Latitude);
        Longitude = findViewById(R.id.Longitude);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
      //  Vibrate = findViewById(R.id.btnVibrate);

        DB = new DB(this);
        tv = findViewById(R.id.Details);


        //actionListener
        insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String idTxt = id.getText().toString();
                String LatitudeTxt = Latitude.getText().toString();
                String LongitudeTxt = Longitude.getText().toString();


                //call el function ale 3mltha f el DB
                Boolean checkInsertData = DB.InsertData(idTxt, LatitudeTxt, LongitudeTxt, null);
                if (checkInsertData == true) {

                    Toast.makeText(MainActivity.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                }

            }

        });
        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String idTxt = id.getText().toString();
                String LatitudeTxt = Latitude.getText().toString();
                String LongitudeTxt = Longitude.getText().toString();

                //call el function ale 3mltha f el DB
                Boolean checkUpdateData = DB.UpdateData(idTxt, LatitudeTxt, LongitudeTxt, null);
                if (checkUpdateData == true) {
                    Toast.makeText(MainActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Not Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String idTxt = id.getText().toString();
                //call el function ale 3mltha f el DB
                Boolean checkDeleteData = DB.DeleteData(idTxt);
                if (checkDeleteData == true) {
                    Toast.makeText(MainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                }

            }
        });
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Cursor res = DB.GetData();

                String LatitudeTxt = Latitude.getText().toString();
                String LongitudeTxt = Longitude.getText().toString();
                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    return;
                }
                //bysr3 3mlyt el processing w by3rd el data ahsn
                StringBuffer buffer = new StringBuffer();
                //tol mfe data tet3rd hotha f el append
                while (res.moveToNext()) {
                    buffer.append("ID : " + res.getString(0) + "\n");
                    buffer.append("Latitude : " + res.getString(1) + "\n");
                    buffer.append("Longitude : " + res.getString(2) + "\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Location Details");
                builder.setMessage(buffer.toString());
                builder.show();

                //sh brdooooooooooo
                double x = Double.parseDouble(Latitude.getText().toString());
                double y = Double.parseDouble(Longitude.getText().toString());
                if ((x >=30  &&x<=31) && (y>=31&&y<=32) ) {
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    Toast.makeText(MainActivity.this, "Your mobile phone is vibrated now", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //sh hnaaaaaaaaaaa
      /*  Vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(Latitude.getText().toString());
                int y = Integer.parseInt(Longitude.getText().toString());
                if (x == 2 && y == 3) {
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                }
            }
        });*/

    }
}