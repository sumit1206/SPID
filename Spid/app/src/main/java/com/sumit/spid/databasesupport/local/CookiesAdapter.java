package com.sumit.spid.databasesupport.local;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.sumit.spid.R;

import java.io.IOException;

public class CookiesAdapter
{
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase cookiesDb;
    private CookiesHelper cookiesHelper;

    public CookiesAdapter(Context context)
    {
        this.mContext = context;
        cookiesHelper = new CookiesHelper(mContext);
    }

    public CookiesAdapter createDatabase() throws SQLException
    {
        try
        {
            cookiesHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public CookiesAdapter openReadable() throws SQLException
    {
        try
        {
            cookiesHelper.openDataBase();
            cookiesHelper.close();
            cookiesDb = cookiesHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public CookiesAdapter openWritable() throws SQLException
    {
        try
        {
            cookiesHelper.openDataBase();
            cookiesHelper.close();
            cookiesDb = cookiesHelper.getWritableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }


    public void close()
    {
        cookiesHelper.close();
    }

    public Cursor getStationList()
    {
        try
        {
            String sql ="SELECT Name, Code FROM allStations";

            Cursor mCur = cookiesDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getStationList >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getProfileData(String phoneNumber)
    {
        try
        {
            String sql ="SELECT * FROM PROFILE WHERE " + R.string.attributeCookiesMobileNo + " = "+phoneNumber;

            Cursor mCur = cookiesDb.rawQuery(sql, null);
//            if (mCur!=null)
//            {
//                mCur.moveToNext();
//            }
            if(mCur.getCount() == 0)
                return  null;
            else
                return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getStationList >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public String getProfileValue(String phoneNumber, String attribute)
    {
        try
        {
            String sql ="SELECT "+attribute+" FROM PROFILE WHERE Phone_number = "+phoneNumber;

            Cursor mCur = cookiesDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            try {
                return mCur.getString(0);
            }catch (Exception e){
                return  null;
            }
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getStationList >>"+ mSQLException.toString());
//            throw mSQLException;
            return null;
        }
    }


    public void setNewUser(String phoneNumber, String mailId, String name)
    {
        String nameAttribute = "Name";
        String phoneAttribute = "Phone_number";
        String mailIdAttribute = "Mail_id";
        if(getProfileData(phoneNumber) == null) {
            try {
//            String sql ="INSERT INTO PROFILE (Phone_number,Mail_id) VALUES ("+phoneNumber+",'"+mailId+"')";
                String sql = "INSERT INTO PROFILE ("+phoneAttribute+","+mailIdAttribute+","+nameAttribute+") VALUES (" + phoneNumber + ",'" + mailId + "','" + name + "')";
                cookiesDb.execSQL(sql);
                Toast.makeText(mContext, "Insertion sucessful", Toast.LENGTH_LONG).show();
//            return true;
            } catch (SQLException mSQLException) {
                Log.e(TAG, "setNewUser >>" + mSQLException.toString());
//                throw mSQLException;
            }
        }else {

        }
    }

    public void updateProfileValue(String attribute, String value, String phoneNumber)
    {
        try
        {
            String sql = "UPDATE PROFILE SET "+attribute+" = '"+value+"' WHERE Phone_number = "+phoneNumber;
            cookiesDb.execSQL(sql);
            Toast.makeText(mContext,"update sucessful",Toast.LENGTH_LONG).show();
//            return true;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "setNewUser >>"+ mSQLException.toString());
//            throw mSQLException;
        }
    }

}