package com.example.newprovider1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;



public class CompanyProvider extends ContentProvider {
    SQLiteDatabase mydb;
    private static final String DB_NAME="Company";
    private static final String DB_TABLE="emp";
    private static final int DB_VER=1;

    public CompanyProvider() {
    }
    public static final String AUTHORITY="com.example.newprovider1.companyProvider";
    public static final Uri  CONTENT_URI= Uri.parse("content://"+ AUTHORITY+ "/emp");
    static int EMP =1;
    static int EMP_ID=2;

     static UriMatcher myUri= new UriMatcher(UriMatcher.NO_MATCH);
     static {

         myUri.addURI(AUTHORITY,"emp",EMP);
         myUri.addURI(AUTHORITY,"emp/#",EMP_ID);
     }






    private class MyOwnDatabase extends SQLiteOpenHelper{

        public MyOwnDatabase(@Nullable Context context) {
            super(context, DB_NAME,null,DB_VER);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            //mydb=sqLiteDatabase;
            sqLiteDatabase.execSQL("create table "+DB_TABLE+" (_id integer primary key autoincrement, emp_name text,profile text);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          db.execSQL("DROP TABLE IF EXISTS  " +DB_TABLE);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        mydb.delete(DB_TABLE, selection, selectionArgs);
        return 0;
        //throw new UnsupportedOperationException("Not yettttt implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
         Log.d("msg", "Values - "+values.getAsString("emp_name"));
         long row =mydb.insert(DB_TABLE,null,values);
        Log.d("msg", "I am called");
        //getContext().getContentResolver().insert(CompanyProvider.CONTENT_URI, values);
         //getContext().getContentResolver().insert(uri, values);
        //Toast.makeText(getContext(), "Inserting", Toast.LENGTH_SHORT).show();

         if(row>0){
             uri = ContentUris.withAppendedId(CONTENT_URI, row);
             getContext().getContentResolver().notifyChange(uri,null);
         }
         return uri;

    }

    @Override
    public boolean onCreate() {
        MyOwnDatabase myHelper = new MyOwnDatabase(getContext());
        mydb = myHelper.getWritableDatabase();
        if (mydb != null) {
            return true;

        } else {
            return false;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder myQuery = new SQLiteQueryBuilder();
        myQuery.setTables(DB_TABLE);
        Cursor cr = myQuery.query(mydb,projection,selection,null,null,null,sortOrder);
        cr.setNotificationUri(getContext().getContentResolver(),uri);
        return cr;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        mydb.update(DB_TABLE, values, selection, selectionArgs);
        return 0;
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
