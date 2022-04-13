package com.example.spotifyv2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_create_account;
    private Button btn_log_in;

    private EditText user;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.editTextTextPersonName);
        pass = (EditText) findViewById(R.id.editTextTextPassword);

        btn_create_account = (Button) findViewById(R.id.button2);
        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreate_account();
            }
        });

        btn_log_in = (Button) findViewById(R.id.button);
        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHome_screen();
                Client_server cl = new Client_server();
                if(user.getText().toString() == "aaa" && pass.getText().toString()=="aaa"){
                    System.out.println("Welcom");
                }
                else
                {
                    System.out.println("Username of Password Error");
                }
            }
        });
    }

    public void openHome_screen(){
        Intent intent2 = new Intent(this,Music_list.class);
        startActivity(intent2);
    }

    public void openCreate_account() {
        Intent intent1 = new Intent(this, Create_account.class);
        startActivity(intent1);
    }
}