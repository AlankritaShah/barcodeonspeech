package in.munshig.bareech;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.opengl.GLES31Ext;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.Toolbar;

public class ViewActivity extends ActionBarActivity implements Serializable{

    List<ItemModel> thingList;
    SQLiteDatabase mDatabase;
    ListView listViewThings;
    //    ImageButton addbutton;
//    ImageButton refreshbutton;
    LocalDatabase ldb2;
    ListAdapter thingadapter;
    Button refreshbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_floating_button);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));

        listViewThings = (ListView) findViewById(R.id.listViewThings);
        thingList = new ArrayList<>();

        ldb2 = new LocalDatabase(getApplicationContext());
        //opening the database
        //  ldb2.deleteAllbarcodeItems();
        mDatabase = openOrCreateDatabase(ldb2.DATABASE_NAME, MODE_PRIVATE, null);

        thingList = ldb2.getitemmodel();

        // thingadapter = new ThingAdapter(this, R.layout.list_layout_item, thingList, mDatabase, ldb2);
        listViewThings.setAdapter(new ViewAdapter(this, R.layout.list_layout_item, thingList, mDatabase, ldb2));

        listViewThings.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("item", "clicked");
                final ItemModel thing = thingList.get(i);
              //  updateThing(thing);
                Intent in = new Intent(ViewActivity.this, UpdateActivity.class);
                in.putExtra("imodel", thing);

                finish();
                startActivity(in);



            }

        });
    }

//    private void updateThing(final ItemModel sm) {
//        // SQLiteDatabase db = this.getWritableDatabase();
//        final AlertDialog.Builder builder = new AlertDialog.Builder(ItemBarcodeActivity.this);
//
//        LayoutInflater inflater = LayoutInflater.from(ItemBarcodeActivity.this);
//        View view = inflater.inflate(R.layout.dialog_update_barcode_thing, null);
//        builder.setView(view);
//
//        final TextView textbarcode = (TextView) view.findViewById(R.id.textbarcode);
//        final EditText editTextName = (EditText) view.findViewById(R.id.editTextName);
//
//        //final EditText editTextalternate = (EditText) view.findViewById(R.id.editTextalternate);
//        //  final EditText editTextStock = (EditText) view.findViewById(R.id.editTextStock);
//        final EditText editTextPrice = (EditText) view.findViewById(R.id.editTextPrice);
//        //  final Spinner spinnerUnit = (Spinner) view.findViewById(R.id.spinnerUnit);
//
//        textbarcode.setText(sm.getBarcode());
//        editTextName.setText(sm.getname());
//        editTextPrice.setText(String.valueOf(sm.getprice()));
//
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//
//        view.findViewById(R.id.buttonUpdateThing).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = editTextName.getText().toString().trim();
//                String price = editTextPrice.getText().toString().trim();
//
//                Log.i("itemnamepassed", sm.getname());
//                BarcodeItemModel smodel = new BarcodeItemModel();
//                smodel.itemname = name;
//                smodel.itemprice = Float.parseFloat(price);
//                smodel.barcode = sm.getBarcode();
//                ldb2.upDatebarcodeIteminlocal(smodel);
//                MainActivity.dataitembarcode.child(sm.getBarcode()).setValue(smodel);
//
//                Toast.makeText(getApplicationContext(), "Item Updated", Toast.LENGTH_SHORT).show();
//
//                reloadThingsFromDatabase();
//
//                dialog.dismiss();
//            }
//        });
//
//        view.findViewById(R.id.buttonDeleteThing).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(ItemBarcodeActivity.this);
//                builder.setTitle("Are you sure?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //  Log.i("itemnamepassed", name);
//                        BarcodeItemModel smodel = new BarcodeItemModel();
//                        smodel.barcode = sm.getBarcode();
//                        smodel.itemname = sm.getname();
//                        ldb2.deletebarcodeItemfromlocal(smodel);
//                        MainActivity.dataitembarcode.child(smodel.barcode).setValue(null);
//                        Toast.makeText(getApplicationContext(), "Item Deleted.", Toast.LENGTH_SHORT).show();
//                        reloadThingsFromDatabase();
//                        dialog.dismiss();
//
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
//    }

//    private void reloadThingsFromDatabase() {
//
//        thingList  = ldb2.getbarcodeItemsfromlocal();
//        listViewThings.setAdapter(new ThingAdapter(this, R.layout.list_layout_item, thingList, mDatabase, ldb2));
//        //   thingadapter.notifyDataSetChanged();
//    }




    }


