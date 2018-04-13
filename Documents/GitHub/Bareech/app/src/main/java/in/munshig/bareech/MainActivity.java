package in.munshig.bareech;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PipedOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Locale;

import in.munshig.bareech.SharedPrefs;

public class MainActivity extends AppCompatActivity {

    public int PERMISSIONS_MULTIPLE_REQUEST = 123;
    static EditText name;
    static Button unit[]=new Button[3];
    static ToggleButton small[] = new ToggleButton[4];
    static ToggleButton medium[] = new ToggleButton[4];
    static ToggleButton big[] = new ToggleButton[4];
    static Button exists, DNexists;
    static TextView sm,md,lg;
    static int unitvalue=-1;
    static int k=0;
    TextView vieweditems, saveditems;
    SharedPrefs sp;
    public static DatabaseReference datauser;
  //  static String[] listofitems = new String[300];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));

        final LocalDatabase ldb = new LocalDatabase(getApplicationContext());

        vieweditems = (TextView) findViewById(R.id.vieweditems);
        saveditems = (TextView) findViewById(R.id.saveditems);

        unit[0] = (Button)findViewById(R.id.toggleButton);
        unit[1] = (Button)findViewById(R.id.toggleButton2);
        unit[2] = (Button)findViewById(R.id.toggleButton3);
        small[0] = (ToggleButton)findViewById(R.id.toggleButton4);
        small[1] = (ToggleButton)findViewById(R.id.toggleButton5);
        small[2] = (ToggleButton)findViewById(R.id.toggleButton6);
        small[3] = (ToggleButton)findViewById(R.id.toggleButton7);
        medium[0] = (ToggleButton)findViewById(R.id.toggleButton8);
        medium[1] = (ToggleButton)findViewById(R.id.toggleButton9);
        medium[2] = (ToggleButton)findViewById(R.id.toggleButton10);
        medium[3] = (ToggleButton)findViewById(R.id.toggleButton11);
        big[0] = (ToggleButton)findViewById(R.id.toggleButton12);
        big[1] = (ToggleButton)findViewById(R.id.toggleButton13);
        big[2] = (ToggleButton)findViewById(R.id.toggleButton14);
        big[3] = (ToggleButton)findViewById(R.id.toggleButton15);

        exists = (Button)findViewById(R.id.button);
        DNexists = (Button)findViewById(R.id.button2);

        name = (EditText) findViewById(R.id.itemname);
        sm = (TextView)findViewById(R.id.textView);
        md = (TextView)findViewById(R.id.textView2);
        lg = (TextView)findViewById(R.id.textView3);

        sp = SharedPrefs.getInstance(getApplicationContext());
        k=sp.getbillno();

        datauser = FirebaseDatabase.getInstance().getReference(ldb.userphone);


        final String[] listofitems = new String[]{
                "अगरबत्ती",
                "अंडा",
                "आइसक्रीम",
                "आटा",
                "आलू",
                "इंच टेप",
                "इतरा",
                "इरेज़र",
                "ईमली",
                "ईलायची",
                "ईसबगोल ",
                "उड़द दाल ",
                "ए4 शीट",
                "कंगनी आटा",
                "कंगी",
                "कटर",
                "कपड़े की साबुन",
                "कपूर",
                "कलर्स",
                "काजू",
                "काला चना",
                "काला नमक",
                "काली इलायची",
                "काली मिर्च",
                "किशमिश",
                "कुमकुम",
                "केची",
                "केसर",
                "कॉफ़ी",
                "क्लीनर",
                "खजूर",
                "खड़े मसाला",
                "गजक",
                "गम",
                "गाठिया",
                "गुड",
                "ग्लव्ज़",
                "ग्लिसरीन",
                "घी",
                "चक्की",
                "चंदन",
                "चने",
                "चना दाल ",
                "चपड़ा",
                "मूंगफली पट्टी",
                "चाय पत्ती",
                "चारोली",
                "चार्ट",
                "ड्राइंग शीट",
                "चोक ",
                "छलनी",
                "चावल",
                "चिक्की",
                "चिप्स",
                "चीज़",
                "चीनी पाउडर",
                "चुरा",
                "चौपाल स्लीपर",
                "काबुली चना",
                "छोले",
                "जडी बूटी",
                "जयफल",
                "जीरा",
                "जैस्मीन ऑयल",
                "ज्योमेट्री बॉक्स",
                "बॉक्स",
                "झाड़ू",
                "टिफिन",
                "टोमेटो केचप",
                "सोया स्नेक",
                "टेप",
                "टॉफी",
                "टॉयलेट ब्रश",
                "टोस्ट",
                "ट्यूबलाइट",
                "डस्टबिन",
                "पुदीन हरा",
                "डायरी",
                "तंबाकू",
                "तलने वाला साबूदाना",
                "तुअर दाल",
                "गुटखा",
                "ताला",
                "तिल",
                "तिल ऑयल",
                "तिल के लड्डू",
                "तेजपत्ता",
                "थैली",
                "दलिया",
                "दाल",
                "दालचीनी",
                "दिलबाग पान मसाला",
                "धनिया",
                "धागा",
                "धूप पाउच",
                "धूपबत्ती",
                "नमकीन",
                "नागकेसर",
                "नारियल",
                "नारियल चूरा",
                "नींबू सत",
                "नूडल्स",
                "नोटबुक",
                "पान मजा",
                " सुपारी",
                "पापड",
                "पिसा हुआ धनिया",
                "पिस्ता",
                "पूजा सामग्री",
                "पेन",
                "पेपर बैग",
                "पेंसिल",
                "पेंसिल पाउच",
                "पोछा",
                "पोहा",
                "प्याज",
                "प्लास्टिक ग्लास",
                "प्लेट",
                "प्लेयर्स कार्ड",
                "फिनाइल गोली",
                "फ्राई चना",
                "फ्रेम्स",
                "हेयर ऑयल",
                "इलायची",
                "नेपकिन",
                "बदाम",
                "बंबू ट्री",
                "बर्तन की साबुन",
                "बलून ",
                "बल्ब",
                "बाल्टी",
                "बीड़ी",
                "बैडमिंटन रैकेट",
                "बोतल",
                "बोल",
                "ब्रेड",
                "बटर",
                "ब्लेड",
                "मक्की दलिया",
                "मक्की आटा",
                "मखाने",
                "मग",
                "माली पन्ना",
                "मशीन ऑयल",
                "सुई",
                "मसूर दाल",
                "माचिस",
                "मारकर",
                "मास्क",
                "मिर्ची",
                "मिश्री",
                "मीठा चना",
                "मीठा सोडा",
                "सौंफ",
                "मूंग दाल",
                "मूंग मोगर",
                "मूंगफली",
                "मेथी",
                "मेदा",
                "मेहंदी",
                "मोमबत्ती",
                "रंगोली",
                "रजिस्टर",
                "रबड़",
                "रबड़ बैंड",
                "रस्सी",
                "राई",
                "राजमा",
                "रिफिल",
                "रीचार्ज",
                "रुमाल",
                "लहर सोडा",
                "लहसून",
                "लाइटर",
                "लिप गार्ड",
                "लिप बाम",
                "लौंग",
                "वाइपर",
                "शक्कर",
                "शटल कॉक",
                "शैमपू",
                "सफेद चना",
                "डिटर्जेंट पाउडर",
                "सेवैया",
                "साबुन",
                "साबुत उड़द",
                "साबुत मूंग",
                "साबुनदानी",
                "साबूदाना",
                "सावा",
                "सिका हुआ चना",
                "सिंगदाना",
                "सिगरेट",
                "सिंघाड़ा आटा",
                "सिंघाड़ा सेव",
                "सिंदुर",
                "सिरका",
                "सुपारी",
                "सूजी",
                "सेफ्टी पिन",
                "सोंठ",
                "सोया बड़ी",
                "स्केच पैन",
                "स्केल",
                "स्क्रबर",
                "हल्दी",
                "हींग",
                "हुक",
                "हैंकी"
        };

        final String[] listofcategories = new String[]{
                "H",
                "F",
                "F",
                "F",
                "F",
                "S",
                "H",
                "S",
                "F",
                "F",
                "F",
                "F",
                "S",
                "F",
                "S",
                "S",
                "H",
                "H",
                "O",
                "F",
                "F",
                "F",
                "F",
                "F",
                "F",
                "H",
                "S",
                "F",
                "F",
                "H",
                "F",
                "F",
                "F",
                "S",
                "F",
                "F",
                "O",
                "O",
                "F",
                "F",
                "H",
                "F",
                "F",
                "F",
                "F",
                "F",
                "F",
                "S",
                "S",
                "S",
                "H",
                "F",
                "F",
                "F",
                "F",
                "F",
                "F",
                "H",
                "F",
                "F",
                "F",
                "F",
                "F",
                "H",
                "S",
                "O",
                "H",
                "O",
                "F",
                "F",
                "S",
                "F",
                "H",
                "F",
                "F",
                "H",
                "F",
                "O",
                "O",
                "F",
                "F",
                "O",
                "O",
                "F",
                "F",
                "F",
                "F",
                "O",
                "F",
                "F",
                "F",
                "O",
                "F",
                "H",
                "H",
                "H",
                "F",
                "F",
                "F",
                "F",
                "F",
                "F",
                "S",
                "O",
                "O",
                "F",
                "F",
                "F",
                "H",
                "S",
                "O",
                "S",
                "S",
                "H",
                "F",
                "F",
                "O",
                "O",
                "O",
                "H",
                "F",
                "O",
                "H",
                "F",
                "H",
                "F",
                "O",
                "H",
                "O",
                "H",
                "H",
                "O",
                "O",
                "H",
                "O",
                "F",
                "F",
                "H",
                "F",
                "F",
                "F",
                "H",
                "F",
                "O",
                "H",
                "F",
                "O",
                "S",
                "O",
                "F",
                "F",
                "F",
                "F",
                "F",
                "F",
                "F",
                "F",
                "F",
                "F",
                "O",
                "H",
                "H",
                "S",
                "S",
                "S",
                "O",
                "F",
                "F",
                "S",
                "O",
                "O",
                "F",
                "F",
                "O",
                "O",
                "O",
                "F",
                "H",
                "F",
                "O",
                "H",
                "F",
                "H",
                "F",
                "H",
                "F",
                "F",
                "H",
                "F",
                "F",
                "F",
                "F",
                "O",
                "F",
                "F",
                "H",
                "F",
                "O",
                "F",
                "S",
                "F",
                "F",
                "S",
                "S",
                "H",
                "F",
                "F",
                "H",
                "H"
        };

        vieweditems.setText(String.valueOf(k) + "/" + listofitems.length);
        saveditems.setText(String.valueOf(ldb.getcountofitemssaved()));

        if(k<listofitems.length) {
            name.setText(listofitems[k]);
//            SomeModel smodel = ldb.searchitem(name);
//            if (smodel!=null) {
//
//               abkyahoga(name.getText().toString().trim(), smodel.unit);
//               if(smodel.ONE!=null)
//               {
//                   small[0].setChecked(true);
//               }
//
//
//            }

         //   else{
//                unit[0].setVisibility(View.VISIBLE);
//                unit[1].setVisibility(View.VISIBLE);
//                unit[2].setVisibility(View.VISIBLE);
            unit[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    unitvalue = 0;
                    String namevalue = name.getText().toString().trim();
                    abkyahoga(namevalue, unitvalue);
                    //   unitvalue=-1;
                }
            });

            unit[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    unitvalue = 1;
                    String namevalue = name.getText().toString().trim();
                    abkyahoga(namevalue, unitvalue);
                    //   unitvalue=-1;
                }
            });

            unit[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    unitvalue = 2;
                    String namevalue = name.getText().toString().trim();
                    abkyahoga(namevalue, unitvalue);
                    //    unitvalue=-1;
                }
            });

            DNexists.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  name.setText("");
                    if (k < listofitems.length - 1) {
                        vieweditems.setText(String.valueOf(k + 1) + "/" + listofitems.length);
                        k++;
                        name.setText(listofitems[k]);
                    } else {
//                        vieweditems.setText(String.valueOf(k + 1) + "/" + listofitems.length);
 //                       k = 0;
                        name.setText("");
                        Toast.makeText(getApplicationContext(), "End of list.", Toast.LENGTH_LONG).show();

                    }
                    sm.setVisibility(View.INVISIBLE);
                    md.setVisibility(View.INVISIBLE);
                    lg.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < 4; i++) {
//                    if(i!=3)
//                    unit[i].setChecked(false);
                        small[i].setVisibility(View.INVISIBLE);
                        medium[i].setVisibility(View.INVISIBLE);
                        big[i].setVisibility(View.INVISIBLE);
                    }

                }
            });


            exists.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String namevalue = name.getText().toString().trim();
                    if (!namevalue.equals("") && unitvalue != -1) {
                        String itemcode = "";
                        if (k <= 9)
                            itemcode = "100" + String.valueOf(k);
                        else if (k <= 99)
                            itemcode = "10" + String.valueOf(k);
                        else if (k <= 999)
                            itemcode = "1" + String.valueOf(k);

                        int catofitem = 0;
                        if (listofcategories[k] == "H")
                            catofitem = 1;
                        else if (listofcategories[k] == "S")
                            catofitem = 2;
                        else if (listofcategories[k] == "F")
                            catofitem = 3;
                        else if (listofcategories[k] == "O")
                            catofitem = 4;

                        String barcode = "444" + ldb.userphone + String.valueOf(catofitem) + itemcode;
                        ldb.saveitemname(namevalue, catofitem, unitvalue, barcode);
                        ldb.saveinreferencetable(itemcode, namevalue);
                        Boolean save = sp.savebillno(k + 1);

                        for (int i = 0; i < 4; i++) {
                            if (small[i].isChecked()) {
                                if (i == 0)
                                    ldb.updatetablefor0and1(namevalue, "ONE", 1);
                                if (i == 1)
                                    ldb.updatetablefor0and1(namevalue, "TWO", 1);
                                if (i == 2)
                                    ldb.updatetablefor0and1(namevalue, "FIVE", 1);
                                if (i == 3)
                                    ldb.updatetablefor0and1(namevalue, "TEN", 1);
                            }
                            else
                            {
                                if (i == 0)
                                    ldb.updatetablefor0and1(namevalue, "ONE", 0);
                                if (i == 1)
                                    ldb.updatetablefor0and1(namevalue, "TWO", 0);
                                if (i == 2)
                                    ldb.updatetablefor0and1(namevalue, "FIVE", 0);
                                if (i == 3)
                                    ldb.updatetablefor0and1(namevalue, "TEN", 0);
                            }
                            if (medium[i].isChecked()) {
                                if (i == 0)
                                    ldb.updatetablefor0and1(namevalue, "FIFTY", 1);
                                if (i == 1)
                                    ldb.updatetablefor0and1(namevalue, "HUNDRED", 1);
                                if (i == 2)
                                    ldb.updatetablefor0and1(namevalue, "TWOFIFTY", 1);
                                if (i == 3)
                                    ldb.updatetablefor0and1(namevalue, "FIVEHUNDRED", 1);
                            }
                            else
                            {
                                if (i == 0)
                                    ldb.updatetablefor0and1(namevalue, "FIFTY", 0);
                                if (i == 1)
                                    ldb.updatetablefor0and1(namevalue, "HUNDRED", 0);
                                if (i == 2)
                                    ldb.updatetablefor0and1(namevalue, "TWOFIFTY", 0);
                                if (i == 3)
                                    ldb.updatetablefor0and1(namevalue, "FIVEHUNDRED", 0);
                            }
                            if (big[i].isChecked()) {
                                if (i == 0)
                                    ldb.updatetablefor0and1(namevalue, "THOUSAND", 1);
                                if (i == 1)
                                    ldb.updatetablefor0and1(namevalue, "TWOTHOUSAND", 1);
                                if (i == 2)
                                    ldb.updatetablefor0and1(namevalue, "TWOTHOUSANDFIVEHUNDRED", 1);
                                if (i == 3)
                                    ldb.updatetablefor0and1(namevalue, "FIVETHOUSAND", 1);
                            }
                            else
                            {
                                if (i == 0)
                                    ldb.updatetablefor0and1(namevalue, "THOUSAND", 0);
                                if (i == 1)
                                    ldb.updatetablefor0and1(namevalue, "TWOTHOUSAND", 0);
                                if (i == 2)
                                    ldb.updatetablefor0and1(namevalue, "TWOTHOUSANDFIVEHUNDRED", 0);
                                if (i == 3)
                                    ldb.updatetablefor0and1(namevalue, "FIVETHOUSAND", 0);
                            }
                        }
                        //   name.setText("");
                        //  k++;
                        if (k < listofitems.length - 1) {
                            vieweditems.setText(String.valueOf(k + 1) + "/" + listofitems.length);
                            saveditems.setText(String.valueOf(ldb.getcountofitemssaved()));
                            k++;
                            name.setText(listofitems[k]);
                        } else {
//                            vieweditems.setText(String.valueOf(k + 1) + "/" + listofitems.length);
//                            saveditems.setText(String.valueOf(ldb.getcountofitemssaved()));
//                            k = 0;
//                            name.setText(listofitems[k]);
                            name.setText("");
                            Toast.makeText(getApplicationContext(), "End of list.", Toast.LENGTH_LONG).show();


                        }
                        sm.setVisibility(View.INVISIBLE);
                        md.setVisibility(View.INVISIBLE);
                        lg.setVisibility(View.INVISIBLE);
                        for (int j = 0; j < 4; j++) {
//                        if (j != 3)
//                            unit[j].setChecked(false);
                            if (small[j].isChecked())
                                small[j].setChecked(false);
                            if (medium[j].isChecked())
                                medium[j].setChecked(false);
                            if (big[j].isChecked())
                                big[j].setChecked(false);
                            small[j].setVisibility(View.INVISIBLE);
                            medium[j].setVisibility(View.INVISIBLE);
                            big[j].setVisibility(View.INVISIBLE);
                        }
                        Log.i("returnref", ldb.iwanttoseethereftable());
                        Log.i("return", ldb.iwanttoseethetable());
                        unitvalue = -1;
                    }
                    if (!namevalue.equals("") && unitvalue == -1) {
                        Toast.makeText(getApplicationContext(), "Select one of the units to proceed.", Toast.LENGTH_SHORT).show();
                    }

                }

            });
   //     }
        }

//        else if(k>=listofitems.length)
//        {
//            name.setText(listofitems[k]);
//
//            unit[0].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    InputMethodManager inputManager = (InputMethodManager)
//                            getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                            InputMethodManager.HIDE_NOT_ALWAYS);
//                    unitvalue = 0;
//                    String namevalue = name.getText().toString().trim();
//                    abkyahoga(namevalue);
//                }
//            });
//
//            unit[1].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    InputMethodManager inputManager = (InputMethodManager)
//                            getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                            InputMethodManager.HIDE_NOT_ALWAYS);
//                    unitvalue = 1;
//                    String namevalue = name.getText().toString().trim();
//                    abkyahoga(namevalue);
//                }
//            });
//
//            unit[2].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    InputMethodManager inputManager = (InputMethodManager)
//                            getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                            InputMethodManager.HIDE_NOT_ALWAYS);
//                    unitvalue = 2;
//                    String namevalue = name.getText().toString().trim();
//                    abkyahoga(namevalue);
//                }
//            });
//
//            DNexists.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                      name.setText("");
//                  //  k++;
//                    sm.setVisibility(View.INVISIBLE);
//                    md.setVisibility(View.INVISIBLE);
//                    lg.setVisibility(View.INVISIBLE);
//                    for (int i = 0; i < 4; i++) {
////                    if(i!=3)
////                    unit[i].setChecked(false);
//                        small[i].setVisibility(View.INVISIBLE);
//                        medium[i].setVisibility(View.INVISIBLE);
//                        big[i].setVisibility(View.INVISIBLE);
//                    }
//
//                }
//            });
//
//
//            exists.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String namevalue = name.getText().toString().trim();
//                    if (!namevalue.equals("")) {
//                        ldb.saveitemname(namevalue, unitvalue);
////                    for(int i=0;i<3;i++)
////                    {
//////                        if(unitvalue.isChecked())
//////                        {
////                            ldb.saveitemname(namevalue, i);
////                            break;
////                      //  }
////                    }
//                        for (int i = 0; i < 4; i++) {
//                            if (small[i].isChecked()) {
//                                if (i == 0)
//                                    ldb.updatetablefor0and1(namevalue, "ONE");
//                                if (i == 1)
//                                    ldb.updatetablefor0and1(namevalue, "TWO");
//                                if (i == 2)
//                                    ldb.updatetablefor0and1(namevalue, "FIVE");
//                                if (i == 3)
//                                    ldb.updatetablefor0and1(namevalue, "TEN");
//                            }
//                            if (medium[i].isChecked()) {
//                                if (i == 0)
//                                    ldb.updatetablefor0and1(namevalue, "FIFTY");
//                                if (i == 1)
//                                    ldb.updatetablefor0and1(namevalue, "HUNDRED");
//                                if (i == 2)
//                                    ldb.updatetablefor0and1(namevalue, "TWOFIFTY");
//                                if (i == 3)
//                                    ldb.updatetablefor0and1(namevalue, "FIVEHUNDRED");
//                            }
//                            if (big[i].isChecked()) {
//                                if (i == 0)
//                                    ldb.updatetablefor0and1(namevalue, "THOUSAND");
//                                if (i == 1)
//                                    ldb.updatetablefor0and1(namevalue, "TWOTHOUSAND");
//                                if (i == 2)
//                                    ldb.updatetablefor0and1(namevalue, "TWOTHOUSANDFIVEHUNDRED");
//                                if (i == 3)
//                                    ldb.updatetablefor0and1(namevalue, "FIVETHOUSAND");
//                            }
//                        }
//                           name.setText("");
//                      //  k++;
//                        sm.setVisibility(View.INVISIBLE);
//                        md.setVisibility(View.INVISIBLE);
//                        lg.setVisibility(View.INVISIBLE);
//                        for (int j = 0; j < 4; j++) {
////                        if (j != 3)
////                            unit[j].setChecked(false);
//                            if (small[j].isChecked())
//                                small[j].setChecked(false);
//                            if (medium[j].isChecked())
//                                medium[j].setChecked(false);
//                            if (big[j].isChecked())
//                                big[j].setChecked(false);
//                            small[j].setVisibility(View.INVISIBLE);
//                            medium[j].setVisibility(View.INVISIBLE);
//                            big[j].setVisibility(View.INVISIBLE);
//                        }
//                        Log.i("return", ldb.iwanttoseethetable());
//
//                    }
//                }
//
//            });
//        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        Log.i("menu","getmenuinflater");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        int id = item.getItemId();

        switch (id) {
            case R.id.backbutton:
            {
                startActivity(new Intent(MainActivity.this, ViewActivity.class));

                break;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }



    public void abkyahoga(String namevalue, int someunitvalue)
    {
                if (!namevalue.equals("")) {
                    sm.setVisibility(View.VISIBLE);
                    md.setVisibility(View.VISIBLE);
                    lg.setVisibility(View.VISIBLE);
                    if(someunitvalue == 0) {
                        sm.setText("Gram - Small");
                        md.setText("Gram - Medium");
                        lg.setText("Gram - Large");
                    }
                    else if(someunitvalue == 1) {
                        sm.setText("miliLitre - Small");
                        md.setText("miliLitre - Medium");
                        lg.setText("miliLitre - Large");
                    }
                    else if(someunitvalue == 2) {
                        sm.setText("unit - Small");
                        md.setText("unit - Medium");
                        lg.setText("unit - Large");
                    }
                    for (int j = 0; j < 4; j++) {
                        small[j].setVisibility(View.VISIBLE);
                        medium[j].setVisibility(View.VISIBLE);
                        big[j].setVisibility(View.VISIBLE);

                    }
                }
    }
}
