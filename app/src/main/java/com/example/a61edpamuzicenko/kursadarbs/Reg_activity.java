package com.example.a61edpamuzicenko.kursadarbs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Reg_activity extends AppCompatActivity implements View.OnClickListener {
    Button btn_reg;
    EditText et_Login, et_Password, et_Email;

    DBHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        //slezhenie za knopkoj registracii
        btn_reg =(Button) findViewById(R.id.btn_reg);
        btn_reg.setOnClickListener(this);

        //slezhenie za polami vvoda
        et_Login = (EditText) findViewById(R.id.et_Login);
        et_Password = (EditText) findViewById(R.id.et_Password);
        et_Email = (EditText) findViewById(R.id.et_Email);

        //
        dbhelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {

        String Login = et_Login.getText().toString();
        String Password = et_Password.getText().toString();
        String Email = et_Email.getText().toString();

        SQLiteDatabase database = dbhelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v.getId()){
            case R.id.btn_reg:
                if (Login.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"UserName is empty",Toast.LENGTH_LONG).show();
                    break;
                }
                else if (Password.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Password is empty",Toast.LENGTH_LONG).show();
                    break;
                }
                else if (Email.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Email is empty",Toast.LENGTH_LONG).show();
                    break;
                }
                else {
                    Cursor mCount= database.rawQuery("select count(*) from Users where Login='" + Login + "'", null);
                    mCount.moveToFirst();
                    int count= mCount.getInt(0);
                    mCount.close();
                    if(count>0){
                        Toast.makeText(getApplicationContext(), "Sorry! This Username already exists! ", Toast.LENGTH_LONG).show();
                    }
                    else {
                        contentValues.put(DBHelper.USER_LOGIN, Login);
                        contentValues.put(DBHelper.USER_PASSWORD, Password);
                        contentValues.put(DBHelper.USER_EMAIL, Email);
                        database.insert(DBHelper.TABLE_USERS, null, contentValues);
                        Toast.makeText(getApplicationContext(), "Account created! UserName " + Login + " and email " + Email, Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(Reg_activity.this,MainActivity.class);
                        startActivity(myIntent);
                        break;
                    }
                }
        }

    }


}
