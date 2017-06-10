package com.example.a61edpamuzicenko.kursadarbs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static com.example.a61edpamuzicenko.kursadarbs.DBHelper.TABLE_NOTES;


public class WorkActivity extends AppCompatActivity {
    EditText et_text;
    Button btn_add,btn_del,btn_read;

    DBHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        getSupportActionBar().setTitle("Note");
        ActivityHelper.initialize(this);
        final int ID = getSharedPreferences(MuzichenkoAppConstants.PREFS_NAME,MODE_PRIVATE).getInt(MuzichenkoAppConstants.MY_ID,MuzichenkoAppConstants.ID);

        et_text = (EditText) findViewById(R.id.et_text);

        dbhelper = new DBHelper(this);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String  Text = et_text.getText().toString();
                SQLiteDatabase database = dbhelper.getWritableDatabase();
                database.delete(DBHelper.TABLE_NOTES,null,null);
                ContentValues contentValues = new ContentValues();
                if (Text.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Text line is empty! Please, fill it!",Toast.LENGTH_LONG).show();
                }
                else{
                    contentValues.put(DBHelper.NOTE_TEXT, Text);
                    contentValues.put(DBHelper.NOTE_ID, ID);
                    database.insert(TABLE_NOTES, null, contentValues);
                    Toast.makeText(getApplicationContext(),"Note: "+ Text +" sucessfuly added!",Toast.LENGTH_LONG).show();
                }

            }
        });
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = dbhelper.getWritableDatabase();
                database.delete(DBHelper.TABLE_NOTES,null,null);
                Toast.makeText(getApplicationContext(),"Note Deleted!",Toast.LENGTH_LONG).show();
            }
        });
        btn_read = (Button) findViewById(R.id.btn_read);
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = dbhelper.getWritableDatabase();
                Cursor cursor = database.query(TABLE_NOTES,null,null,null,null,null,null);
                if (cursor.moveToFirst()) {
                    int NOTEID = cursor.getColumnIndex(DBHelper.NOTE_ID);
                    int NOTETEXT = cursor.getColumnIndex(DBHelper.NOTE_TEXT);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(NOTEID) +
                                ", Text = " + cursor.getString(NOTETEXT));
                        Toast.makeText(getApplicationContext(),"Note: "+ cursor.getString(NOTETEXT),Toast.LENGTH_LONG).show();
                    } while (cursor.moveToNext());
                }
                else{
                    Log.d("mLog","0 rows");
                    Toast.makeText(getApplicationContext(),"Note is empty!",Toast.LENGTH_LONG).show();
                }
                cursor.close();

            }
        });

}

}