package com.example.cosmotracker;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper extends SQLiteOpenHelper {

    //Путь к папке с базами на устройстве
    public static String DB_PATH;
    //Имя файла с базой
    public static String DB_NAME;

    public SQLiteDatabase database;
    public final Context context;

    public DatabaseHelper(Context context, String databaseName) {
        super(context, databaseName, null, 1);
        this.context = context;
        //полный путь к базе
        DB_PATH = context.getDatabasePath(databaseName).getAbsolutePath();
        DB_NAME = databaseName;
        openDataBase();
    }

    /**
     * Создаст базу, если она не создана и откроет ее
     */
    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();//Создает и/или открывает базу данных
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database!");
            }
        }
    }

    /**
     * Проверка существования базы данных
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        String path = DB_PATH + DB_NAME;
        try {
            checkDb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }

        if (checkDb != null) checkDb.close();
        return checkDb != null;
    }

    /**
     * Метод копирования базы
     */
    private void copyDataBase() throws IOException {
        // Открываем поток для чтения из уже созданной нами БД источник в assets
        InputStream externalDbStream = context.getAssets().open(DB_NAME);
        // Путь к уже созданной пустой базе в андроиде
        String outFileName = DB_PATH + DB_NAME;
        // Теперь создадим поток для записи в эту БД побайтно
        OutputStream localDbStream = new FileOutputStream(outFileName);
        // Собственно, копирование
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }

        localDbStream.close();
        externalDbStream.close();
    }

    /**
     * Метод открытия БД
     */
    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (database == null) {
            createDataBase();
            database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }




}