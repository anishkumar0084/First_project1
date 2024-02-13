package com.example.first_project;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Data_base extends SQLiteOpenHelper {
    public static final String table="Regi_page";
    public static final String table_name="Regi_user";

    public static final String col="Name";
    public static final String col2="Email";
    public static final String col3="Age";
    public static final String col4="Password";
    public static final String col5="Mobile_num";



    public Data_base(@Nullable Context context) {
        super(context,table, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE " + table_name + " (" +
                col5 + " INTEGER PRIMARY KEY, " +
                col + " TEXT, " +
                col2+ " TEXT, " +
                col3+ " INTEGER, " +
                col4+ " TEXT)";

        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_name );
        onCreate(sqLiteDatabase);


    }
    public void insertData(String name, String email,String password,String age,String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col, name);
        values.put(col2, email);
        values.put(col4, password);
        values.put(col3, age);
        values.put(col5, mobile);
        // You can add more values if needed

        db.insert(table_name, null, values);
        db.close();
    }
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = col2 + " = ? AND " + col4 + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(table_name, null, selection, selectionArgs, null, null, null);
        boolean result = cursor.getCount() > 0;

//        cursor.close();
//        db.close();

        return result;
    }
    public Cursor getData(String mobileNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {col, col2, col3};
        String selection = col4 + " = ?";
        String[] selectionArgs = {mobileNumber};

        return db.query(table_name, projection, selection, selectionArgs, null, null, null);
    }
    public boolean checkUserExists(String name, String email, String password, String age, String mobile) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean userExists = false;

        try {
            // Query to check if any record matches the provided parameters
            String query = "SELECT * FROM " + table_name + " WHERE " +
                    col + " = ? AND " +
                    col2 + " = ? AND " +
                    col3 + " = ? AND " +
                    col4 + " = ? AND " +
                    col5 + " = ?";
            cursor = db.rawQuery(query, new String[]{name, email, age, password, mobile});

            // If the cursor has any rows, it means the user exists
            if (cursor != null && cursor.getCount() > 0) {
                userExists = true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking user existence: " + e.getMessage());
        } finally {
            // Close the cursor and database connection
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return userExists;
    }





}
