package munshig.in.kiranamark3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Collections;


public class ItemAddBarcodeActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextName, editTextStock, editTextPrice, editTextbarcodenumber;
    Spinner spinnerUnit;
    Button scanbarcode;
    Button buttonmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));

        Intent intent = getIntent();
        String numbarcode = intent.getStringExtra("numbarcode");

        scanbarcode = (Button) findViewById(R.id.scanbarcode);
        buttonmain = (Button) findViewById(R.id.buttonmain);
        editTextbarcodenumber = (EditText) findViewById(R.id.editTextbarcodenumber);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
            editTextbarcodenumber.requestFocus();
            editTextbarcodenumber.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction()!=KeyEvent.ACTION_DOWN)
                        return true;
                    if(keyCode == KeyEvent.KEYCODE_ENTER ){
                        //your necessary codes...

                        final String numbar = editTextbarcodenumber.getText().toString();
                        if (!(numbar.equals(""))) {

                                final BarcodeItemModel barcodeitem = ldb3.searchbarcodeIteminlocal(numbar);
                                if (barcodeitem != null) {
                                    Log.i("item", "found");
                                    //edit option
                                    //  editTextbarcodenumber.setText("");
                                    //   Toast.makeText(getApplicationContext(), "Already exists.", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ItemAddBarcodeActivity.this);
                                    builder.setTitle(barcodeitem.getname() + " - Rs." + barcodeitem.getprice());
                                    builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            updateThing(barcodeitem);
                                            editTextbarcodenumber.setText("");
                                            editTextbarcodenumber.requestFocus();
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            editTextbarcodenumber.setText("");
                                            editTextbarcodenumber.requestFocus();

                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }

                                else {
                                    final BarcodeItemModel barcodeitem2 = ldb3.searchbarcodeItem(numbar);
                                    if (barcodeitem2 != null) {
                                        //edit option
                                        //  editTextbarcodenumber.setText("");
                                        //   Toast.makeText(getApplicationContext(), "Already exists.", Toast.LENGTH_SHORT).show();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ItemAddBarcodeActivity.this);
                                        builder.setTitle(barcodeitem2.getname() + " - Rs." + barcodeitem2.getprice());
                                        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                ldb3.savebarcodeIteminlocal(barcodeitem2);
                                                MainActivity.dataitembarcode.child(barcodeitem2.getBarcode()).setValue(barcodeitem2);
                                                updateThing(barcodeitem2);
                                                editTextbarcodenumber.setText("");
                                                editTextbarcodenumber.requestFocus();
                                            }
                                        });
                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                editTextbarcodenumber.setText("");
                                                editTextbarcodenumber.requestFocus();

                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();

                                    }
                                    else {


                                        //add option
                                        editTextName.requestFocus();
                                        InputMethodManager inputMethodManager =
                                                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        inputMethodManager.toggleSoftInputFromWindow(
                                                editTextName.getApplicationWindowToken(),
                                                InputMethodManager.SHOW_FORCED, 0);
                                    }
                                }
                        }

                        return true;
                    }
                    return false;
                }
            });


        findViewById(R.id.buttonAddThing).setOnClickListener(this);
        scanbarcode.setOnClickListener(this);
        buttonmain.setOnClickListener(this);
    }

    private void updateThing(final BarcodeItemModel sm) {
        // SQLiteDatabase db = this.getWritableDatabase();
        final AlertDialog.Builder builder = new AlertDialog.Builder(ItemAddBarcodeActivity.this);

        LayoutInflater inflater = LayoutInflater.from(ItemAddBarcodeActivity.this);
        View view = inflater.inflate(R.layout.dialog_update_barcode_thing, null);
        builder.setView(view);

        final TextView textbarcode = (TextView) view.findViewById(R.id.textbarcode);
        final EditText editTextName = (EditText) view.findViewById(R.id.editTextName);

        //final EditText editTextalternate = (EditText) view.findViewById(R.id.editTextalternate);
        //  final EditText editTextStock = (EditText) view.findViewById(R.id.editTextStock);
        final EditText editTextPrice = (EditText) view.findViewById(R.id.editTextPrice);
        //  final Spinner spinnerUnit = (Spinner) view.findViewById(R.id.spinnerUnit);

        textbarcode.setText(sm.getBarcode());
        editTextName.setText(sm.getname());
        editTextPrice.setText(String.valueOf(sm.getprice()));
        editTextPrice.requestFocus();
     //   final AlertDialog dialog = builder.create();

      //  dialog.show();

        editTextPrice.setSelection(0,String.valueOf(sm.getprice()).length());

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();

        view.findViewById(R.id.buttonUpdateThing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String price = editTextPrice.getText().toString().trim();

                Log.i("itemnamepassed", sm.getname());
                BarcodeItemModel smodel = new BarcodeItemModel();
                smodel.itemname = name;
                smodel.itemprice = Float.parseFloat(price);
                smodel.barcode = sm.getBarcode();
                ldb3.upDatebarcodeIteminlocal(smodel);
                MainActivity.dataitembarcode.child(sm.getBarcode()).setValue(smodel);

                Toast.makeText(getApplicationContext(), "Item Updated", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        view.findViewById(R.id.buttonDeleteThing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemAddBarcodeActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //  Log.i("itemnamepassed", name);
                        BarcodeItemModel smodel = new BarcodeItemModel();
                        smodel.barcode = sm.getBarcode();
                        smodel.itemname = sm.getname();
                        ldb3.deletebarcodeItemfromlocal(smodel);
                        MainActivity.dataitembarcode.child(smodel.barcode).setValue(null);
                        Toast.makeText(getApplicationContext(), "Item Deleted.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private boolean inputsAreCorrect(String name, String price) {
        if (name.isEmpty()) {
            editTextName.setError("Please enter a name");
            editTextName.requestFocus();
            return false;
        }

//        if (stock.isEmpty() || Integer.parseInt(stock) <= 0) {
//            editTextStock.setError("Please enter stock");
//            editTextStock.requestFocus();
//            return false;
//        }

        if (price.isEmpty() || Float.parseFloat(price) <= 0) {
            editTextPrice.setError("Please enter price");
            editTextPrice.requestFocus();
            return false;
        }
        return true;
    }

    //Adds items to the database of kirana and firebase
    private void addThing() {

        String name = editTextName.getText().toString().trim();
    //    String fbid = editTextStock.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();
     //   String ut = spinnerUnit.getSelectedItem().toString();
        String barcodenumber = editTextbarcodenumber.getText().toString().trim();

        BarcodeItemModel bim = new BarcodeItemModel(name, Float.parseFloat(price), barcodenumber);
        Log.i("bim", bim.toString());

        ldb3.savebarcodeIteminlocal(bim);


      //  ldb3.savebarcodeItem(bim);
        MainActivity.dataitembarcode.child(barcodenumber).setValue(bim);
            //query to insert values in the local database
//            mDatabase.execSQL("INSERT INTO things \n" +
//                    "(name, unit, stock, price, barcodenumber)\n" +
//                    "VALUES \n" +
//                    "(?, ?, ?, ?, ?);", new String[]{name, ut, stock, price, barcodenumber});

            Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
        if(frombill==true)
        {
            frombill=false;
            Intent in2 = new Intent(ItemAddBarcodeActivity.this, SpeechActivity.class);
            in2.putExtra("numbarcode2", barcodenumber);
            startActivity(in2);
            finish();
        }

            //Item's barcode is added as the column in the customer thing database
//            mDatabaseCT.execSQL("ALTER TABLE customerthing \n" +
//                    "ADD B" + barcodenumber + " INTEGER");
//            itemcount++;
     //   }
        editTextName.setText("");
        editTextbarcodenumber.setText("");
        editTextPrice.setText("");
        editTextbarcodenumber.requestFocus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonAddThing:
                addThing();
                break;

            case R.id.scanbarcode:
                editTextName.setText("");
                editTextbarcodenumber.setText("");
                editTextPrice.setText("");
                editTextbarcodenumber.requestFocus();
                break;

            case R.id.buttonmain:
                startActivity(new Intent(this, MainActivity.class));
                finish();

//            case R.id.textViewViewThings:
//                startActivity(new Intent(this, ThingActivity.class));
//                break;
//
//            case R.id.scanbarcode:
//                startActivity(new Intent(this, ContinousCaptureActivity.class));
//                break;
//
//            case R.id.customerbutton:
//                startActivity(new Intent(this, CustomerAddActivity.class));

        }
    }
}
