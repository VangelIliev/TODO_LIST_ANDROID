package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    EditText firstPassword, secondPassword, newPassword;
    Button confirmBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        firstPassword = findViewById(R.id.firstPassword);
        secondPassword = findViewById(R.id.secondPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmBTN = findViewById(R.id.confirmBTN);
        final DatabaseHelper myDb;
        myDb = new DatabaseHelper(this);

        confirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstPassword.getText().toString().isEmpty()
                        || secondPassword.getText().toString().isEmpty()
                        || newPassword.getText().toString().isEmpty()) {
                    Toast.makeText(ChangePassword.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }

                if (!newPassword.getText().toString().equals(secondPassword.getText().toString())) {
                    Toast.makeText(ChangePassword.this, "The passwords must match ! Try again", Toast.LENGTH_SHORT).show();
                }
                if (firstPassword.getText().toString().equals(secondPassword.getText().toString()) && !newPassword.getText().toString().isEmpty()) {
                    // update the password ????
                    myDb.UpdateData("...",secondPassword.getText().toString());
                }
            }
        });
    }
}

