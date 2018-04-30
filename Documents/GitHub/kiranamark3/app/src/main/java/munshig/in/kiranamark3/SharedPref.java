package munshig.in.kiranamark3;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

public class SharedPref {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MunshigPref";

    // All Shared Preferences Keys

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    //   private static final String Key_SESSION = "session";
//    private static final String KEY_INSTALLED = "install";
    // User name (make variable public to access from outside)
    //   public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    //  public static final String KEY_ID = "id";
    private static final String LOGINNED_USER = "user";
    private static final String APPROVED_USER = "approved";
    private static final String DATA_DOWNLOADED = "downloaded";
    // Constructor
    public SharedPref(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setDownload(boolean download){
        editor.putBoolean(DATA_DOWNLOADED,download);
        editor.commit();
    }

    public boolean isDownloaded(){
        return pref.getBoolean(DATA_DOWNLOADED,false);
    }


    /*  public void firstSession(int num){
          // Storing login value as TRUE
          editor.putBoolean(Key_SESSION, true);
          editor.putString(KEY_NAME, "user");
          editor.putBoolean(KEY_INSTALLED, false);
          editor.putString(KEY_ID, "example@xyz.com");
          // commit changes
          editor.commit();
      }
      public boolean check(){
          if(pref.contains(Key_SESSION)){
              return true;
          }
          else{
              return false;
          }
      }
      public boolean isIstalled(){
          return pref.getBoolean(KEY_INSTALLED,false);
      }
      public void setInstalled(){
          editor.putBoolean(KEY_INSTALLED,true);
          editor.commit();
      }*/
    public String getUser(){
        return pref.getString(LOGINNED_USER,null);
    }
    public int getApprovedStatus(){
        return pref.getInt(APPROVED_USER,-1);
    }

    public void setUser(FirebaseUser user){
        editor.putString(LOGINNED_USER,user.getPhoneNumber());
        editor.commit();
    }
    public void setApproved(int res){
        editor.putInt(APPROVED_USER,res);
        editor.commit();
    }
    public void logOut(){
        editor.clear();
        editor.commit();
    }

}