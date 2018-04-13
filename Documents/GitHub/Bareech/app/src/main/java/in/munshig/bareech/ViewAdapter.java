package in.munshig.bareech;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class ViewAdapter extends ArrayAdapter<ItemModel> {

    Context mCtx;
    int listLayoutRes;
    List<ItemModel> thingList;
    SQLiteDatabase mDatabase;
    LocalDatabase ldb2;
    Map<String, Object> result;

    public ViewAdapter(Context mCtx, int listLayoutRes, List<ItemModel> thingList, SQLiteDatabase mDatabase, LocalDatabase ldb2) {
        super(mCtx, listLayoutRes, thingList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.thingList = thingList;
        this.mDatabase = mDatabase;
        this.ldb2 = ldb2;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        final ItemModel thing = thingList.get(position);

        TextView textViewName =(TextView) view.findViewById(R.id.textViewName);
        TextView textViewbarcode =(TextView) view.findViewById(R.id.textViewBarcode);
        TextView textViewunit = (TextView) view.findViewById(R.id.textViewunit);
        TextView textViewquantity = (TextView) view.findViewById(R.id.textviewquantity);

        textViewName.setText(thing.itemname);
        textViewbarcode.setText(String.valueOf(thing.barcode));
        String unitvalue="";
        if(thing.unit==0)
            unitvalue="gram";
        else if(thing.unit==1)
            unitvalue="ml";
        else if(thing.unit==2)
            unitvalue="unit";
        textViewunit.setText(unitvalue);
        String qua="";
        if(thing.one==1)
            qua=qua+"1, ";
        if(thing.two==1)
            qua=qua+"2, ";
        if(thing.five==1)
            qua=qua+"5, ";
        if(thing.ten==1)
            qua=qua+"10, ";
        if(thing.fifty==1)
            qua=qua+"50, ";
        if(thing.hundred==1)
            qua=qua+"100, ";
        if(thing.twofifty==1)
            qua=qua+"250, ";
        if(thing.fivehundred==1)
            qua=qua+"500, ";
        if(thing.onethousand==1)
            qua=qua+"1000, ";
        if(thing.twothousand==1)
            qua=qua+"2000, ";
        if(thing.twothousandfivehundred==1)
            qua=qua+"2500, ";
        if(thing.fivethousand==1)
            qua=qua+"5000, ";
        textViewquantity.setText(String.valueOf(qua));

        notifyDataSetChanged();
        return view;

    }

}