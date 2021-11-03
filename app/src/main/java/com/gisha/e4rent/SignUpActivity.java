package com.gisha.e4rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    Button register;
    TextView linktologin;
    EditText regName,regUsername,regPhone,regPassword,regEmail;
    ProgressBar progressbar;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        //links to all xml files
        regName = findViewById(R.id.fulname);
        regUsername = findViewById(R.id.usernameSignup);
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.passwordsign);
        regPhone = findViewById(R.id.phone);
        register = findViewById(R.id.btnRegister);
        linktologin = findViewById(R.id.haveOne);
        progressbar = findViewById(R.id.progressBar);

        linktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
    }

       public void register(View view){
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                if (!validateEmail() |!validateName()|!validateuserName()|!validatePassword()|!validatephone()){
                   return;
                }

               progressbar.setVisibility(View.GONE);


                //get all the values
                String name = regName.getText().toString();
                String username = regUsername.getText().toString();
                String email = regEmail.getText().toString();
                String phone = regPhone.getText().toString();
                String password = regPassword.getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name, username, email, phone, password);

                reference.child(phone).setValue(helperClass);

           Intent intent = new Intent(SignUpActivity.this,loginActivity.class);
           startActivity(intent);


            }
    public boolean validateName(){
        String val = regName.getText().toString();
        if (val.isEmpty()){
            regName.setError("please enter this field");
            return false;
        }
        else {
            regName.setError(null);
            return true;
        }
}
    public boolean validateuserName(){
        String val = regUsername.getText().toString();
        String noWhitespace = "(?=\\s+$)";
        if (val.isEmpty()){
            regName.setError("please enter this field");
            return false;
        }
        else if (val.length()>=15){
            regUsername.setError("Username is too long");
            return false;
        }
        else if (!val.matches(noWhitespace)){
            regUsername.setError("White spaces are not allowed");

            return false;

        }
        else {
            regUsername.setError(null);
            return true;
        }
    }
    public boolean validateEmail(){
        String val = regEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()){
            regEmail.setError("please enter this field");
            return false;
        }
        else if (!val.matches(emailPattern)){
            regEmail.setError("invalid email");
            return false;
        }
        else {
            regEmail.setError(null);

            return true;
        }
    }
    public boolean validatephone(){
        String val = regPhone.getText().toString();
        if (val.isEmpty()){
            regPhone.setError("please enter this field");
            return false;
        }
        else {
            regPhone.setError(null);
            return true;
        }
    }
    public boolean validatePassword(){
        String val = regPassword.getText().toString();
        String password = "^"+ "(?=.*[0-9])" + "(?=.*[a-z])"+
                "(?=.*[a-zA-Z])"+
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)"+
                "(.{4,})";


        if (val.isEmpty()){
            regPassword.setError("please enter this field");
            return false;
        }
        else if(!val.matches(password)){
            regPassword.setError("password is too weak");
            return false;
        }
        else {
            regPassword.setError(null);
            return true;
        }
    }

}