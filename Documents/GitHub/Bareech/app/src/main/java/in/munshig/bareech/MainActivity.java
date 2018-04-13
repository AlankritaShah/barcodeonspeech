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
    static int unitvalue;
    static int k=0;
  //  static String[] listofitems = new String[300];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        final LocalDatabase ldb = new LocalDatabase(getApplicationContext());
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
                "नेपकिन",
                "किशमिश",
                "कुमकुम",
                "केची",
                "केसर",
                "कैंडल",
                "मोमबत्ती",
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
                "चाय की छलनी",
                "चाय पत्ती",
                "चारोली",
                "चार्ट",
                "ड्राइंग शीट",
                "चोक ",
                "छलनी",
                "चावल",
                "चिक्की",
                "गजक",
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
                "दाख",
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
                "पेड़ नोटपैड",
                "पेन",
                "पेपर बैग",
                "पेंसिल",
                "पेंसिल पाउच",
                "पोछा",
                "पोहा",
                "प्याज",
                "प्लास्टिक थैली",
                "प्लास्टिक ग्लास",
                "प्लेट",
                "प्लेयर्स कार्ड",
                "फिनाइल गोली",
                "फ्राई चना",
                "फ्रेम्स",
                "चाय पत्ती",
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
                "रसना",
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
                "लॉक",
                "लौंग",
                "वाइपर",
                "शंकर छाप",
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
                "सेंसर",
                "सोंठ",
                "सोया बड़ी",
                "स्केच पैन",
                "स्केल",
                "स्क्रबर",
                "हल्दी",
                "हींग",
                "हुक",
                "हैंकी",
                "हैंड वॉश"
        };


        if(k<listofitems.length) {
            name.setText(listofitems[k]);

            unit[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    unitvalue = 0;
                    String namevalue = name.getText().toString().trim();
                    abkyahoga(namevalue,unitvalue);
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
                    abkyahoga(namevalue,unitvalue);
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
                    abkyahoga(namevalue,unitvalue);
                }
            });

            DNexists.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  name.setText("");
                    if(k<listofitems.length-1)
                    {
                        k++;
                        name.setText(listofitems[k]);
                    }


                    else {
                        name.setText("");

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
                    if (!namevalue.equals("")) {
                        ldb.saveitemname(namevalue, unitvalue);
//                    for(int i=0;i<3;i++)
//                    {
////                        if(unitvalue.isChecked())
////                        {
//                            ldb.saveitemname(namevalue, i);
//                            break;
//                      //  }
//                    }
                        for (int i = 0; i < 4; i++) {
                            if (small[i].isChecked()) {
                                if (i == 0)
                                    ldb.updatetablefor0and1(namevalue, "ONE");
                                if (i == 1)
                                    ldb.updatetablefor0and1(namevalue, "TWO");
                                if (i == 2)
                                    ldb.updatetablefor0and1(namevalue, "FIVE");
                                if (i == 3)
                                    ldb.updatetablefor0and1(namevalue, "TEN");
                            }
                            if (medium[i].isChecked()) {
                                if (i == 0)
                                    ldb.updatetablefor0and1(namevalue, "FIFTY");
                                if (i == 1)
                                    ldb.updatetablefor0and1(namevalue, "HUNDRED");
                                if (i == 2)
                                    ldb.updatetablefor0and1(namevalue, "TWOFIFTY");
                                if (i == 3)
                                    ldb.updatetablefor0and1(namevalue, "FIVEHUNDRED");
                            }
                            if (big[i].isChecked()) {
                                if (i == 0)
                                    ldb.updatetablefor0and1(namevalue, "THOUSAND");
                                if (i == 1)
                                    ldb.updatetablefor0and1(namevalue, "TWOTHOUSAND");
                                if (i == 2)
                                    ldb.updatetablefor0and1(namevalue, "TWOTHOUSANDFIVEHUNDRED");
                                if (i == 3)
                                    ldb.updatetablefor0and1(namevalue, "FIVETHOUSAND");
                            }
                        }
                     //   name.setText("");
                      //  k++;
                        if(k<listofitems.length-1)
                        {
                            k++;
                            name.setText(listofitems[k]);
                        }


                        else {
                            name.setText("");

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
                        Log.i("return", ldb.iwanttoseethetable());

                    }
                }

            });
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


    public void abkyahoga(String namevalue, int someunitvalue)
    {
//        for(int i=0;i<3;i++)
//        {
//            if(unit[i].isChecked()) {
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
                    else if(someunitvalue == 3) {
                        sm.setText("unit - Small");
                        md.setText("unit - Medium");
                        lg.setText("unit - Large");
                    }
                    for (int j = 0; j < 4; j++) {
                        small[j].setVisibility(View.VISIBLE);
                        medium[j].setVisibility(View.VISIBLE);
                        big[j].setVisibility(View.VISIBLE);

                    }

                  //  break;
                }
//            }
//        }
    }


}
