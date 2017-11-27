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
//private boolean flagdel = true;

    public SQLiteDatabase getDb() {
        return database;
    }

    public DatabaseHelper(Context context, String databaseName) {
        super(context, databaseName, null, 1);
        this.context = context;
        //Составим полный путь к базам для нашего приложения
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
        //Андроид не любит утечки ресурсов, все должно закрываться
        if (checkDb != null) checkDb.close();
        return checkDb != null;
    }

    /**
     * метод проверки наличия в БД таблицы
     */
    public boolean checkTableInDB(String nameTable) {
        boolean result = false;
        Cursor cur = database.query("sqlite_master", new String[]{"name"}, "type=? AND name=?", new String[]{"table", nameTable}, null, null, null);
        cur.moveToLast();
        if (cur.getCount() > 0) result = true;
        cur.close();
        return result;
    }

    /**
     * метод проверки наличия поля в таблице
     */
    public boolean checkFieldInTable(String nameTable, String nameField) {
        boolean result = false;
        try {
            Cursor cur = database.rawQuery("PRAGMA table_info('" + nameTable + "')", null);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                String name = "";
                try {
                    name = cur.getString(cur.getColumnIndexOrThrow("name"));
                } catch (IllegalArgumentException e) {
                }
                if (name.equals(nameField)) {
                    result = true;
                    break;
                }
                cur.moveToNext();
            }
            cur.close();
            cur = null;
        } catch (SQLException e) {
        }
        return result;
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
        // Мы будем хорошими мальчиками (девочками) и закроем потоки
        localDbStream.close();
        externalDbStream.close();
    }

    /**
     * Метод получения всех данных из таблицы
     */
    public Cursor getAllData(String dbTable) {
        return database.query(dbTable, null, null, null, null, null, null);
    }

    /**
     * Метод получения данных из таблицы по условию
     */
    public Cursor getDataByWhere(String table, String[] columns, String where, String[] where_args) {
        return database.query(table, columns, where, where_args, null, null, null);
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