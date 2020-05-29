package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {



    EditText UsernameET, PasswordET, EmailET,RepeatET;
    Button RegisterBTN;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        myDb = new DatabaseHelper(this);
        RepeatET = findViewById(R.id.RepeatET);
        UsernameET = findViewById(R.id.UsernameET);
        PasswordET = findViewById(R.id.PasswordET);

        EmailET = findViewById(R.id.EmailET);
        RegisterBTN = findViewById(R.id.RegisterBTN);




        Register();

    }
    final Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[A-Za-z0-9]{3,}@[A-Za-z]{2,}\\.[a-z]{2,}");
    final Pattern Username_Pattern = Pattern.compile("[A-Za-z0-9]{4,}");
    private boolean CheckUsername(String username){
        return Username_Pattern.matcher(username).matches();
    }
    final Pattern Password_Pattern = Pattern.compile("[A-Za-z0-9]{4,}");
    private boolean CheckPassword(String password){
        return Password_Pattern.matcher(password).matches();
    }
    private boolean CheckEmail(String email1) {
        return EMAIL_ADDRESS_PATTERN.matcher(email1).matches();
    }
    public  void Register() {
        RegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = UsernameET.getText().toString();
                String s2 = PasswordET.getText().toString();
                String s3 = RepeatET.getText().toString();
                String s4 = EmailET.getText().toString();

                Boolean checkEmail = myDb.checkEmail(s4);
                Boolean checkUsername = myDb.checkUsername(s1);

                if (s1.equals("")  || s2.equals("") || s3.equals("") || s4.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();

                }
                if (!s2.equals(s3)){
                    Toast.makeText(Registration.this, "Passwords must match", Toast.LENGTH_SHORT).show();
                }
                if (checkUsername == false) {

                    Toast.makeText(Registration.this, "Username exists", Toast.LENGTH_SHORT).show();
                }
                if (checkEmail == false){
                    Toast.makeText(Registration.this, "Email exists", Toast.LENGTH_SHORT).show();
                }
                if (s2.equals(s3)) {

                        if (checkUsername == true && checkEmail == true) {
                            Boolean insert;
                            // check pattern
                            if (CheckEmail(s4) && CheckUsername(s1) && CheckPassword(s3))
                            {
                                insert = myDb.insertData(s1,s2,s4);
                                if (insert == true)
                                    Toast.makeText(Registration.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else if (!CheckEmail(s4)){
                                Toast.makeText(Registration.this, "Incorrect Email", Toast.LENGTH_SHORT).show();
                            }
                            else if (!CheckUsername(s1))
                            {
                                Toast.makeText(Registration.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                            }
                            else { if(!CheckPassword(s2))
                                Toast.makeText(Registration.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                            }



                    }

                }


        });
    }
}
