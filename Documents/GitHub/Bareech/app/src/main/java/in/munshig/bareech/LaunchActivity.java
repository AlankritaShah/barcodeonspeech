package in.munshig.bareech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class LaunchActivity extends Activity {
    LocalDatabase ldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        ldb = new LocalDatabase(getApplicationContext());
        SharedPrefs sp = SharedPrefs.getInstance(getApplication());
        ldb.userphone = sp.getDeviceToken();

        if(ldb.userphone!=null) {
            Log.i("user", ldb.userphone);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, InputUserdetails.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
    }
}
