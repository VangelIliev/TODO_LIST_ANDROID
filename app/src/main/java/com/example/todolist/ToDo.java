package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.model.Info;
import com.example.todolist.model.User;
import com.example.todolist.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class ToDo extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    TextView ID;
    EditText itemET,idET;
    Button btn,logOutBtn,ViewAll,DeleteButton,changeBTN,viewAllEmails;
    ListView itemList;
    DatabaseHelper myDb;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        changeBTN = findViewById(R.id.changeBTN);
        logOutBtn = findViewById(R.id.LogOutBtn);
        viewAllEmails = findViewById(R.id.viewAllEmails);
        DeleteButton = findViewById(R.id.DeleteButton);
        itemET = findViewById(R.id.itemET);

        btn = findViewById(R.id.btn);
        itemList = findViewById(R.id.itemList);
        ViewAll = findViewById(R.id.ViewAll);
        adapter = new ArrayAdapter<>(this,R.layout.row);
        // fill adapter
        User user = (User) Session.getInstance().getData().get(Utils.LOGGED_IN_USERNAME);
        if (user != null) {
            for (Info i : user.getUserInfo().getData()) {
                adapter.add(i.getInfo());
            }
        }

        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(this);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDo.this,MainActivity.class);
                startActivity(intent);
                Session.getInstance().getData().remove(Utils.LOGGED_IN_USERNAME);
            }
        });

        myDb = new DatabaseHelper(this);
        btn.setOnClickListener(this);
        changeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDo.this,ChangePassword.class);
                startActivity(intent);
            }
        });
        viewAllEmails();
        viewAll();
        DeleteData();
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this,"You must first loggout !",Toast.LENGTH_SHORT).show();
    }
    public void ShowData(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void DeleteData(){
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User loggedUser = (User) Session.getInstance().getData().get(Utils.LOGGED_IN_USERNAME);
                if (loggedUser != null) {
                    Integer deletedRows = myDb.DeleteData(loggedUser.getUsername());
                    if (deletedRows > 0){
                        Toast.makeText(ToDo.this, "Data Deleted Succesfully" , Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ToDo.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ToDo.this, "Data not deleted,Database doesn't contain such id", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }
    public void viewAll()
    {
        ViewAll .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Cursor res =  myDb.getAllData();
                if(res.getCount() == 0){
                    ShowData("Error","No Users found!");
                    return;
                }
                else{
                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()){
                        buffer.append("Username : "+ res.getString(1)+"\n");


                    }
                    ShowData("Users List",buffer.toString());
                }
            }
        });
    }
    public void viewAllEmails()
    {
        viewAllEmails .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res =  myDb.getAllData();
                if(res.getCount() == 0){
                    ShowData("Error","No emails found!");
                    return;
                }
                else{
                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()){
                        buffer.append("Email : "+ res.getString(3)+"\n");

                    }
                    ShowData("Emails List",buffer.toString());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                String itemEntered = itemET.getText().toString();
                 if (itemEntered.equals(""))
                 {
                     Toast.makeText(this, "Item cannot be empty", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     myDb.addUserItem(itemEntered);
                     adapter.add(itemEntered);
                     itemET.setText("");
                 }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // fill adapter
        User user = (User) Session.getInstance().getData().get(Utils.LOGGED_IN_USERNAME);
        if (user != null) {
            long infoId = user.getUserInfo().getData().get(position).getInfoId();
            user.getUserInfo().getData().remove(0);
            myDb.deleteData(infoId);
            Toast.makeText(this,"Item Deleted",Toast.LENGTH_SHORT).show();
            adapter.remove(adapter.getItem(position));
            adapter.notifyDataSetChanged();
        }

       /* if (items != null && items.size() > position) {
            items.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this,"Item Deleted",Toast.LENGTH_SHORT).show();
        }*/
    }
}
