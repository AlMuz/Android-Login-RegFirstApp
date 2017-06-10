package com.example.a61edpamuzicenko.kursadarbs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button bt1,bt2,bt3;
    EditText et_Login, et_Password;

    DBHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Login Page");
        ActivityHelper.initialize(this);
        final int ID = getSharedPreferences(MuzichenkoAppConstants.PREFS_NAME,MODE_PRIVATE).getInt(MuzichenkoAppConstants.MY_ID,MuzichenkoAppConstants.ID);
        //slezhenie za polami vvoda
        et_Login = (EditText) findViewById(R.id.et_Login);
        et_Password = (EditText) findViewById(R.id.et_Password);
        dbhelper = new DBHelper(this);
        final SQLiteDatabase database = dbhelper.getWritableDatabase();

        bt1 = (Button) findViewById(R.id.btn_log);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Login = et_Login.getText().toString();
                String Password = et_Password.getText().toString();
                if (Login.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Login is empty", Toast.LENGTH_LONG).show();
                } else if (Password.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_LONG).show();
                } else {
                    //Todo: Login sdelat
                    Cursor mCount = database.rawQuery("select count(*) from Users where Login='" + Login + "'and Password='" + Password +"'", null);
                    mCount.moveToFirst();
                    int count = mCount.getInt(0);
                    mCount.close();
                    if(count>0){
                        String WhereClause = "Login= '" + Login + "'";
                        Cursor c = database.query("Users", null, WhereClause, null, null, null, null);
                        if(c!=null&&c.moveToFirst()){
                            do{
                                int ID = c.getInt(c.getColumnIndexOrThrow ("_ID"));
                                getSharedPreferences(MuzichenkoAppConstants.PREFS_NAME,MODE_PRIVATE).edit().putInt(MuzichenkoAppConstants.MY_ID,ID).commit();

                            }while(c.moveToNext());
                        }
                        c.close();
                        Toast.makeText(getApplicationContext(), "Welcome! "+ Login, Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(MainActivity.this,WorkActivity.class);
                        // myIntent.putExtra(ID,"test");
                        startActivity(myIntent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "UserName or password is incorrect!", Toast.LENGTH_LONG).show();
                    }

                }
            }

        });

        bt2 = (Button) findViewById(R.id.btn_reg);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,RegActivity.class);
                startActivity(myIntent);
            }
        });

        bt3 = (Button) findViewById(R.id.btn_test);
        bt3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cursor cursor = database.query(DBHelper.TABLE_USERS,null,null,null,null,null,null);

                if (cursor.moveToFirst()) {
                    int idUser = cursor.getColumnIndex(DBHelper.USER_ID);
                    int LoginUser = cursor.getColumnIndex(DBHelper.USER_LOGIN);
                    int EmailUser = cursor.getColumnIndex(DBHelper.USER_EMAIL);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idUser) +
                                ", UserName = " + cursor.getString(LoginUser) +
                                ", Email = " + cursor.getString(EmailUser));
                                Log.d("mLog", String.valueOf(ID));
                    } while (cursor.moveToNext());
                }
                else{
                        Log.d("mLog","0 rows");
                        Log.d("mLog", String.valueOf(ID));
                    }
                cursor.close();

            }
        });


    }
    protected void onDestroy(){
        super.onDestroy();
        getSharedPreferences(MuzichenkoAppConstants.PREFS_NAME,MODE_PRIVATE).edit().putInt(MuzichenkoAppConstants.MY_ID,0).commit();

    }
    @Override
    public void onStop() {
        super.onStop();
        getSharedPreferences(MuzichenkoAppConstants.PREFS_NAME,MODE_PRIVATE).edit().putInt(MuzichenkoAppConstants.MY_ID,0).commit();
    }
}

