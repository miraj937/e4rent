package com.gisha.e4rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {
TextView linktosign;
   EditText logUsername,logPassword;
    ProgressBar progressbar;
    Button btnLogin;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mFirebaseAuth = FirebaseAuth.getInstance();
        linktosign = findViewById(R.id.signupaccnt);
        logUsername = findViewById(R.id.usernameLogin);
        logPassword = findViewById(R.id.passlogin);
        btnLogin = findViewById(R.id.buttonlogin);
        progressbar = findViewById(R.id.progressBar);


        linktosign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    public void login(View view){
        if (!validateuserName() |!validatePassword()){

            return;
        }
        else{
            isUser();

        }

    }

    private void isUser() {
      final   String usernameEntered = logUsername.getText().toString();
      final   String usernamePassword = logPassword.getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(usernameEntered);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    logUsername.setError(null);
                    ;
                    String passwordFromDB = snapshot.child(usernameEntered).child("password").getValue(String.class);
                    if (passwordFromDB.equals(usernamePassword)) {
                        logPassword.setError(null);

                        String nameFromDB = snapshot.child(usernameEntered).child("name").getValue(String.class);
                        String usernameFromDB = snapshot.child(usernameEntered).child("username").getValue(String.class);
                        String phoneFromDB = snapshot.child(usernameEntered).child("phone").getValue(String.class);
                        String emailFromDB = snapshot.child(usernameEntered).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), homeActivity.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("phone", phoneFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("password", passwordFromDB);

                        progressbar.setVisibility(View.GONE);
                        startActivity(intent);
                    } else {
                        logUsername.setError("Wrong password");
                        logUsername.requestFocus();
                    }

                }

                  else{
                    logPassword.setError("Wrong password");
                    logPassword.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }

     );

    }

    public boolean validateuserName(){
        String val =  logUsername.getText().toString();
        if (val.isEmpty()){
            logUsername.setError("please enter this field");
            return false;
        }

        else {
            logUsername.setError(null);

            return true;
        }
    }
    public boolean validatePassword(){
        String val = logPassword.getText().toString();

        if (val.isEmpty()){
            logPassword.setError("please enter this field");
            return false;
        }

        else {
            logPassword.setError(null);
            return true;
        }
    }
}