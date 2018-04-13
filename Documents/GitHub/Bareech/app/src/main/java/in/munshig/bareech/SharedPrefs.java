package in.munshig.bareech;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.util.Log;

public class SharedPrefs {
    private static final String SHARED_PREF_NAME = "FCMSharedPref";
    private static final String TAG_TOKEN = "tagtoken";
    private static final String TAG_TOKEN_BILL = "tagtokenbill";
    private static SharedPrefs mInstance;
    private static Context mCtx;

    private SharedPrefs(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefs getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefs(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token){
        Log.i("token", token);
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(TAG_TOKEN, token);
        edit.apply();
        Log.d("token in savetoken",token);
        Log.d("tag in savedevicetoken",TAG_TOKEN);
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        Log.d("tag in getdevicetoken",TAG_TOKEN);
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TAG_TOKEN, null);
    }

    public boolean savebillno(int billno){
        Log.i("tokenbillno", String.valueOf(billno));
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(TAG_TOKEN_BILL,billno );
        edit.apply();
        Log.d("token in savetoken",String.valueOf(billno));
        Log.d("tag in savedevicetoken",TAG_TOKEN_BILL);
        return true;
    }

    //this method will fetch the device token from shared preferences
    public int getbillno(){
        Log.d("tag in getdevicetoken",TAG_TOKEN_BILL);
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(TAG_TOKEN_BILL, 0);
    }


}