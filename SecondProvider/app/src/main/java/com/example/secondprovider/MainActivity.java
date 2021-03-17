package com.example.secondprovider;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Log Qlog;
    public static Uri CONTENT_URI = Uri.parse("content://com.example.newprovider1.companyProvider/emp");
    private ListView listView;
    private ArrayList<Model> arrayList = new ArrayList<>();
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = getContentResolver().query(CONTENT_URI, null, null, null, "_id");
                Qlog.d(TAG, "  values " + c);
                StringBuilder stringBuilder = new StringBuilder();

                if (c.getCount() == 0) {

                } else {
                    while (c.moveToNext()) {
                        int id = c.getInt(0);
                        String s1 = c.getString(1);
                        String s2 = c.getString(2);
                        stringBuilder.append(id + "   " + s1 + "    " + s2 + "\n");

                        arrayList.add(new Model(c.getInt(0), c.getString(1), c.getString(2)));
                    }
                }
                adapter = new ListAdapter(MainActivity.this, arrayList);
                if (listView != null) {
                    listView.setAdapter(adapter);
                    if (arrayList.isEmpty()){
                    button.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Please Enter Employe Data", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        button.setVisibility(View.GONE);
                    }
                }
            }
        });

    }





}

