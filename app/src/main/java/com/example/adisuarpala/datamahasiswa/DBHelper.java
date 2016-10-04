package com.example.adisuarpala.datamahasiswa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Adi Suarpala on 9/30/2016.
 */
public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "db_mhs";
    public static final String CONTACTS_TABLE_NAME = "tb_data";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAMA = "nama";
    public static final String CONTACTS_COLUMN_NIM = "nim";
    public static final String CONTACTS_COLUMN_JURUSAN = "jurusan";
    public static final String CONTACTS_COLUMN_NOTLP = "notlp";
    private HashMap hp;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table tb_data" +
                        "(id integer primary key, nama text, nim text, jurusan text, notlp text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_data");
        onCreate(db);
    }

    public boolean insertData (String nama, String nim, String jurusan, String notlp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("nim", nim);
        contentValues.put("jurusan", jurusan);
        contentValues.put("notlp", notlp);
        db.insert("tb_data", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from tb_data where id="+id+"", null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateData(Integer id, String nama, String nim, String jurusan, String notlp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("nim", nim);
        contentValues.put("jurusan", jurusan);
        contentValues.put("notlp", notlp);
        db.update("tb_data", contentValues, "id = ? ", new String[]{
            Integer.toString(id)
        });
        return true;

    }

    public Integer deleteData(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tb_data", "id = ? ", new String[]{
                Integer.toString(id)
        });
    }

    public ArrayList<String> getAllData(){
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = newHashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from tb_data", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAMA)));
            res.moveToNext();
        }
        return array_list;
    }
}
