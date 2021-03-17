package com.example.secondprovider;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.LoaderManager;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Model> list;
    LayoutInflater inflater;


    ListAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_item, parent, false);
        final TextView id, name, designation;
        id = convertView.findViewById(R.id.item_id);
        name = convertView.findViewById(R.id.item_name);
        designation = convertView.findViewById(R.id.item_designation);

        Button edit = convertView.findViewById(R.id.btn_edit);
        Button delete = convertView.findViewById(R.id.btn_delete);

        final String ids = ""+list.get(position).getId();
        String names = list.get(position).getName();
        String designat = list.get(position).getDesig();

        id.setText(ids);
        name.setText(names);
        designation.setText(designat);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Edit Data From Hear...
                editDialog(ids);



                Toast.makeText(context, ids+" Edit", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Delete Data From Hear...

                String whereClause="_id = '"+ids+"'";
                context.getContentResolver().delete(Uri.parse(MainActivity.CONTENT_URI+"/"+ids), whereClause, null);



                Toast.makeText(context, ids+" Delete", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public void editDialog(final String ids){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View v= inflater.inflate(R.layout.edit_dialog, null);
        final EditText name = v.findViewById(R.id.edit_name);
        final EditText designation = v.findViewById(R.id.designation);
        final ContentValues values=new ContentValues();
        Log.d("msg","Value in edittext - "+name.getText().toString());




        alert.setTitle("Edit Employee Details");


        alert.setView(v);

        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(context, name.getText(), Toast.LENGTH_LONG).show();

                values.put("emp_name",name.getText().toString());
                values.put("profile",designation.getText().toString());

                //String whereClause="emp_name = ?"; //'"+ids+"'";
                String whereClause="_id = '"+ids+"'";
                Log.d("msg", "Values - "+values.getAsString("emp_name"));
                //context.getContentResolver().delete(Uri.parse(MainActivity.CONTENT_URI+"/"+ids), whereClause, null);
                context.getContentResolver().update(Uri.parse(MainActivity.CONTENT_URI+"/"+ids), values, whereClause, null );
                //context.getContentResolver().insert(MainActivity.CONTENT_URI, values);




            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
                Toast.makeText(context, designation.getText(), Toast.LENGTH_LONG).show();
                

            }
        });

        alert.show();
    }


}
