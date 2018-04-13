package in.munshig.bareech;

/**
 * Created by ALANKRITA on 02-02-2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;


public class LocalDatabase extends SQLiteOpenHelper {

    private static final String LOG = "LocalDatabase3";

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "breech";
    public static final String CREATE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS "  ;
    public static final String CREATE = "CREATE TABLE " ;

    public static final String INSERT = "INSERT INTO " ;

    public static String userphone;

    Context context;

     /*---------------------Bills Table Details----------------------------------------*/

    // Table Name
    private static final String BARCODE_SPEECH = "barcodespeech";

    //  Column names
    private static final String ITEM_NAME = "itemname";
    private static final String UNIT = "unit";
    private static final String ONE = "one";
    private static final String TWO = "two";
    private static final String FIVE = "five";
    private static final String TEN = "ten";
    private static final String FIFTY = "fifty";
    private static final String HUNDRED = "hundred";
    private static final String TWOFIFTY = "twofifty";
    private static final String FIVEHUNDRED = "fivehundred";
    private static final String THOUSAND = "thousand";
    private static final String TWOTHOUSAND = "twothousand";
    private static final String TWOTHOUSANDFIVEHUNDRED = "twothousandfivehundred";
    private static final String FIVETHOUSAND = "fivethousand";

    // Table Create Statement

    private static final String TABLE_BARCODESPEECH = BARCODE_SPEECH + " ("
            + ITEM_NAME            + " TEXT,"
            + UNIT              + " INTEGER,"
            + ONE       + " INTEGER,"
            + TWO      + " INTEGER,"
            + FIVE              + " INTEGER,"
            + TEN         + " INTEGER,"
            + FIFTY        + " INTEGER, "
            + HUNDRED            + " INTEGER, "
            + TWOFIFTY           + " INTEGER, "
            + FIVEHUNDRED   + " INTEGER, "
            + THOUSAND  + " INTEGER, "
            + TWOTHOUSAND         + " INTEGER, "
            + TWOTHOUSANDFIVEHUNDRED          + " INTEGER, "
            + FIVETHOUSAND      + " INTEGER "
            + ")";

    public void saveitemname(String itemname, int unit)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.execSQL(CREATE_IF_NOT_EXISTS+TABLE_BILLS);
        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, itemname);
        values.put(UNIT, unit);

        db.insert(BARCODE_SPEECH, null, values);
    }

    public void updatetablefor0and1(String itemname, String col)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(col, 1);
        String WHERE1 = String.format("%s='%s'", ITEM_NAME, itemname);

        db.update(BARCODE_SPEECH,values, WHERE1, null);
    }

    public LocalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context=context;
    }

    public String iwanttoseethetable()
    {
        String selectQuery = "SELECT * FROM " + BARCODE_SPEECH;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        String whatever = "";

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Log.i("found", "something");
                if(c.getString(c.getColumnIndex(ITEM_NAME))!=null) {
                    whatever = whatever + "\n" + ITEM_NAME +" - " + c.getString(c.getColumnIndex(ITEM_NAME));
                }
                if(c.getString(c.getColumnIndex(UNIT))!=null) {
                    whatever = whatever + "\n" + UNIT +" - " + c.getString(c.getColumnIndex(UNIT));
                }
                if(c.getString(c.getColumnIndex(ONE))!=null) {
                    whatever = whatever + "\n" + ONE +" - " + c.getString(c.getColumnIndex(ONE));
                }
                if(c.getString(c.getColumnIndex(TWO))!=null) {
                    whatever = whatever + "\n" + TWO +" - " + c.getString(c.getColumnIndex(TWO));
                }
                if(c.getString(c.getColumnIndex(FIVE))!=null) {
                    whatever = whatever + "\n" + FIVE +" - " + c.getString(c.getColumnIndex(FIVE));
                }
                if(c.getString(c.getColumnIndex(TEN))!=null) {
                    whatever = whatever + "\n" + TEN +" - " + c.getString(c.getColumnIndex(TEN));
                }
                if(c.getString(c.getColumnIndex(FIFTY))!=null) {
                    whatever = whatever + "\n" + FIFTY +" - " + c.getString(c.getColumnIndex(FIFTY));
                }
                if(c.getString(c.getColumnIndex(HUNDRED))!=null) {
                    whatever = whatever + "\n" + HUNDRED +" - " + c.getString(c.getColumnIndex(HUNDRED));
                }
                if(c.getString(c.getColumnIndex(TWOFIFTY))!=null) {
                    whatever = whatever + "\n" + TWOFIFTY +" - " + c.getString(c.getColumnIndex(TWOFIFTY));
                }
                if(c.getString(c.getColumnIndex(FIVEHUNDRED))!=null) {
                    whatever = whatever + "\n" + FIVEHUNDRED +" - " + c.getString(c.getColumnIndex(FIVEHUNDRED));
                }
                if(c.getString(c.getColumnIndex(THOUSAND))!=null) {
                    whatever = whatever + "\n" + THOUSAND + " - " + c.getString(c.getColumnIndex(THOUSAND));
                }
                if(c.getString(c.getColumnIndex(TWOTHOUSAND))!=null) {
                    whatever = whatever + "\n" + TWOTHOUSAND + " - " + c.getString(c.getColumnIndex(TWOTHOUSAND));
                }
                if(c.getString(c.getColumnIndex(TWOTHOUSANDFIVEHUNDRED))!=null) {
                    whatever = whatever + "\n" + TWOTHOUSANDFIVEHUNDRED + " - " + c.getString(c.getColumnIndex(TWOTHOUSANDFIVEHUNDRED));
                }
                if(c.getString(c.getColumnIndex(FIVETHOUSAND))!=null) {
                    whatever = whatever + "\n" + FIVETHOUSAND + " - " + c.getString(c.getColumnIndex(FIVETHOUSAND));
                }
                whatever = whatever + "\n";

            } while (c.moveToNext());
        }
        return whatever;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_IF_NOT_EXISTS+TABLE_BARCODESPEECH);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARCODESPEECH);

        Log.v(LOG,"dropping tables");
        onCreate(db);
    }

   /*-------------------------------------String Processing ------------------------------------------------------------------ */

    public List<String> stringSeperate(String text){
        String[] temp = text.split(",");

        List<String> splitStr = new ArrayList<String>();
        for(String name : temp)
            splitStr.add(name.trim());

        return splitStr;
    }

    public String combinedString(List<String> list){
        String combined="";

        for (String element : list) {

            combined =combined + element+ ","
            ;
        }

        return combined;
    }


/*-------------------------------------String Processing End ------------------------------------------------------------------ */

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public void deleteDB(Context context){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
        context.deleteDatabase(DATABASE_NAME);
    }



}
