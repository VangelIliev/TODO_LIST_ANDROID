package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.model.User;
import com.example.todolist.model.UserInfo;
import com.example.todolist.util.Utils;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText usernameET;
    EditText PasswordET;
    Button LogInBtn;
    Button RegisterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameET = findViewById(R.id.usernameET);
        PasswordET = findViewById(R.id.PasswordET);
        LogInBtn = findViewById(R.id.LogInBtn);
        RegisterBtn = findViewById(R.id.RegisterBtn);

        myDb = new DatabaseHelper(this);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistration();
            }

            private void openRegistration() {
                Intent intent = new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
            }
        });

        LogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ToDo.class);
                String username = usernameET.getText().toString();
                String password = PasswordET.getText().toString();
                if (myDb.usernamePassword(username,password))
                {
                    UserInfo userInfo = myDb.getUserInfo(username);
                    User user = new User(username, userInfo);
                    Session.getInstance().getData().put(Utils.LOGGED_IN_USERNAME, user);
                    Toast.makeText(getApplicationContext(),"Sucessfully Login",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Wrong email or password ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
