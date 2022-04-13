package com.example.spotifyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Create_account extends AppCompatActivity {

    private Button btn_create;
    private EditText text_firstname,text_lastname,text_password,text_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        btn_create = (Button) findViewById(R.id.create_account_btn);
        text_firstname = (EditText) findViewById(R.id.firstname_txt);
        text_lastname = (EditText) findViewById(R.id.lastname_txt);
        text_email = (EditText) findViewById(R.id.email_txt);
        text_password = (EditText) findViewById(R.id.password_txt);



        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Client_server cl = new Client_server(text_firstname.getText().toString(),text_lastname.getText().toString(),text_email.getText().toString(),text_password.getText().toString());
                cl.sendMessage(text_firstname.getText().toString());
                cl.sendMessage(text_lastname.getText().toString());
                cl.sendMessage(text_email.getText().toString());
                cl.sendMessage(text_password.getText().toString());
            }
        });
    }
}