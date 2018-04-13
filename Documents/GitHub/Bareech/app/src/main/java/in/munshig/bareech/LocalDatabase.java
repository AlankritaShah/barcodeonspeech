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

    private static final String REFERENCE_TABLE = "referencetable";

    private static final String ITEMCODE = "itemcode";
    private static final String ITEMNAME = "itemname";

    private static final String TABLE_REFERENCE_TABLE = REFERENCE_TABLE + " ("
            + ITEMCODE            + " TEXT,"
            + ITEMNAME            + " TEXT "
            + ")";

    public void saveinreferencetable(String itemcode, String itemname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.execSQL(CREATE_IF_NOT_EXISTS+TABLE_BILLS);
        ContentValues values = new ContentValues();
        values.put(ITEMCODE, itemcode);
        values.put(ITEMNAME, itemname);

        db.insert(REFERENCE_TABLE, null, values);
    }

    public String iwanttoseethereftable()
    {
        String selectQuery = "SELECT * FROM " + REFERENCE_TABLE;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        String whatever = "";

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Log.i("found", "something");
                if(c.getString(c.getColumnIndex(ITEMCODE))!=null) {
                    whatever = whatever + "\n" + ITEMCODE +" - " + c.getString(c.getColumnIndex(ITEMCODE));
                }
                if(c.getString(c.getColumnIndex(ITEMNAME))!=null) {
                    whatever = whatever + "\n" + ITEMNAME +" - " + c.getString(c.getColumnIndex(ITEMNAME));
                }

                whatever = whatever + "\n";

            } while (c.moveToNext());
        }
        return whatever;

    }

    //end of refernce table


    // Table Name
    private static final String BARCODE_SPEECH = "_" + userphone;

    //  Column names
    private static final String ITEM_NAME = "itemname";
    private static final String CATEGORY = "category";
    private static final String UNIT = "unit";
    private static final String BARCODE = "barcode";
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
            + CATEGORY            + " INTEGER,"
            + UNIT              + " INTEGER,"
            + BARCODE            + " TEXT,"
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

    public void saveitemname(String itemname, int category, int unit, String barcode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.execSQL(CREATE_IF_NOT_EXISTS+TABLE_BILLS);
        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, itemname);
        values.put(CATEGORY, category);
        values.put(UNIT, unit);
        values.put(BARCODE, barcode);

        db.insert(BARCODE_SPEECH, null, values);
        MainActivity.datauser.child(itemname).child(ITEM_NAME).setValue(itemname);
        MainActivity.datauser.child(itemname).child(CATEGORY).setValue(category);
        MainActivity.datauser.child(itemname).child(UNIT).setValue(unit);
        MainActivity.datauser.child(itemname).child(BARCODE).setValue(barcode);
    }

    public void updatetablefor0and1(String itemname, String col, int num)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(col, num);
        String WHERE1 = String.format("%s='%s'", ITEM_NAME, itemname);

        db.update(BARCODE_SPEECH,values, WHERE1, null);
        MainActivity.datauser.child(itemname).child(col).setValue(num);
    }

    public LocalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context=context;
    }

    public int getcountofitemssaved()
    {
        String selectQuery = "SELECT COUNT(*) FROM " + REFERENCE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        int count=0;
        if (c.moveToFirst()) {
            do {
                count  = c.getInt(0);

            } while (c.moveToNext());
        }
        return count;
    }

    public List<ItemModel> getitemmodel()
    {
        List<ItemModel> billsList = new ArrayList<ItemModel>();
        String selectQuery = "SELECT * FROM " + BARCODE_SPEECH;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Log.i("temnamae", c.getString(c.getColumnIndex(ITEM_NAME)));
                Log.i("cat", String.valueOf(c.getString(c.getColumnIndex(CATEGORY))));

                    ItemModel bills = new ItemModel(c.getString(c.getColumnIndex(ITEM_NAME)),
                            c.getInt(c.getColumnIndex(CATEGORY)),
                            c.getInt(c.getColumnIndex(UNIT)),
                            c.getString(c.getColumnIndex(BARCODE)),
                            c.getInt(c.getColumnIndex(ONE)),
                            c.getInt(c.getColumnIndex(TWO)),
                            c.getInt(c.getColumnIndex(FIVE)),
                            c.getInt(c.getColumnIndex(TEN)),
                            c.getInt(c.getColumnIndex(FIFTY)),
                            c.getInt(c.getColumnIndex(HUNDRED)),
                            c.getInt(c.getColumnIndex(TWOFIFTY)),
                            c.getInt(c.getColumnIndex(FIVEHUNDRED)),
                            c.getInt(c.getColumnIndex(THOUSAND)),
                            c.getInt(c.getColumnIndex(TWOTHOUSAND)),
                            c.getInt(c.getColumnIndex(TWOTHOUSANDFIVEHUNDRED)),
                            c.getInt(c.getColumnIndex(FIVETHOUSAND)));

                    billsList.add(bills);
            } while (c.moveToNext());
        }

//        billsList2 = billsList.r;
//        System.out.println(reverseView); // [c, b, a]

        Log.i("billinldb", billsList.toString());
        return billsList;

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
                if(c.getString(c.getColumnIndex(CATEGORY))!=null) {
                    whatever = whatever + "\n" + CATEGORY +" - " + c.getString(c.getColumnIndex(CATEGORY));
                }
                if(c.getString(c.getColumnIndex(UNIT))!=null) {
                    whatever = whatever + "\n" + UNIT +" - " + c.getString(c.getColumnIndex(UNIT));
                }
                if(c.getString(c.getColumnIndex(BARCODE))!=null) {
                    whatever = whatever + "\n" + BARCODE +" - " + c.getString(c.getColumnIndex(BARCODE));
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
        db.execSQL(CREATE_IF_NOT_EXISTS+TABLE_REFERENCE_TABLE);
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
