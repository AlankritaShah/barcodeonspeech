package in.munshig.bareech;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputUserdetails extends AppCompatActivity {
    Button okbutton;
    EditText edittextuser;
    String usernumber;
    LocalDatabase ldb;
    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    SharedPrefs sp;
    double x=0.0,y=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputuser);
        sp = SharedPrefs.getInstance(getApplicationContext());

        edittextuser = (EditText) findViewById(R.id.edittextuser);

        okbutton = (Button) findViewById(R.id.okbutton);

        ldb = new LocalDatabase(getApplicationContext());

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernumber = edittextuser.getText().toString();
                Log.i("userno",usernumber);
                boolean k = sp.saveDeviceToken(usernumber);
                Log.i("k",String.valueOf(k));
                ldb.userphone=usernumber;
                startActivity(new Intent(InputUserdetails.this, MainActivity.class));
                finish();
            }
        });
    }
}