package com.example.jitvar.reapassignment.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jitvar.reapassignment.Core.UCProduct;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jitvar on 6/3/16.
 */
public class UCDatabase extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "reapAssignment.sqlite";
    public static final int DB_VERSION = 1;

    private static final String TAG = UCDatabase.class.getSimpleName();

    private static List<Class> classTableList = new ArrayList<>();
    private static ConnectionSource connection;

    public UCDatabase(Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);
        Log.i(TAG, "Data Base Constructor called");
        connection = getConnectionSource();
        classTableList.add(UCProduct.class);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.i(TAG,"onCreate Called");
        try {
            for (Iterator<Class> iterator = classTableList.iterator(); iterator.hasNext();) {
                Class classToCreate = iterator.next();
                TableUtils.createTable(connectionSource, classToCreate);
                Log.d(TAG, classToCreate.getSimpleName() + " created");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {}

    public ConnectionSource getConnection() {
        return connection;
    }

    public void setConnection(ConnectionSource connection) {
        this.connection = connection;
    }

    public static void resetDatabase() {
        Log.d(TAG, "Clearing the tables");

        try {
            for (Iterator<Class> iterator = classTableList.iterator(); iterator.hasNext();) {
                Class classToDrop = iterator.next();
                TableUtils.dropTable(connection, classToDrop, false);
                Log.d(TAG, classToDrop.getSimpleName() + " dropped");
            }

            for (Iterator<Class> iterator = classTableList.iterator(); iterator.hasNext();) {
                Class classToCreate = iterator.next();
                TableUtils.createTable(connection, classToCreate);
                Log.d(TAG, classToCreate.getSimpleName() + " created");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
