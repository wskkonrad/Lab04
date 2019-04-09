package com.example.lab04;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DodajWpis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wpis);
    }

    public void wyslij (View view) {
        EditText kontrolka =
                (EditText)findViewById(R.id.editText);
        String pole =
                kontrolka.getText().toString();
        Intent intencja = new Intent();
        intencja.putExtra("wpis", pole);
        setResult(RESULT_OK, intencja);
        finish();
    }

    public static class MySQLite extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;

        public MySQLite(Context context){
            super(context, "animalsDB",null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase
                                     database) {
            String DATABASE_CREATE =
                    "create table animals " +
                            "(_id integer primary key autoincrement," +
                            "species text not null," +
                            "color text not null," +
                            "size real not null," +
                            "description text not null);";
            database.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS animals");
            onCreate(db);
        }

        public void usun(String id) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("animals", "_id = ?", new String[] { id });
            db.close();
        }
        public int aktualizuj(Animal zwierz) {
            SQLiteDatabase db =
                    this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("gatunek", zwierz.getSpecies());
            values.put("kolor", zwierz.getColor());
            values.put("wielkosc", zwierz.getSize());
            values.put("opis", zwierz.getDescription());
            int i = db.update("animals", values, "_id = ?", new String[]{String.valueOf(zwierz.get_id())});
            db.close();
            return i;
        }

        public Animal pobierz(int id){
            SQLiteDatabase db =
                    this.getReadableDatabase();
            Cursor cursor =
                    db.query("animals", //a. table name
                            new String[] { "_id",
                                    "gatunek", "kolor", "wielkosc", "opis" },"_id = ?", // c. selections
                            new String[] {
                                    String.valueOf(id) }, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit
            if (cursor != null)
                cursor.moveToFirst();
            Animal zwierz = new
                    Animal(cursor.getString(1), cursor.getString(2),
                    cursor.getFloat(3), cursor.getString(4));

            zwierz.set_id(Integer.parseInt(cursor.getString(0))
            );
            return zwierz;
        }

        public Cursor lista(){
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("Select * from animals",null);
        }


    }
}
