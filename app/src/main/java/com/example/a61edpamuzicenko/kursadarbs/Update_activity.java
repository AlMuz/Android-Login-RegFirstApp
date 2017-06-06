package com.example.a61edpamuzicenko.kursadarbs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Update_activity extends AppCompatActivity {
    Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        bt1 = (Button) findViewById(R.id.btn_log);
        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
}
}