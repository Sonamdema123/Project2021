package com.example.lokhorchunye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText mName, mEmail,mPassword,mConfirmPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    ProgressDialog progressDialog;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm);
        mLoginBtn = findViewById(R.id.login);
        mRegisterBtn = findViewById(R.id.signUp);
        progressDialog = new ProgressDialog(this);

        fAuth = FirebaseAuth.getInstance();
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String confirm = mConfirmPassword.getText().toString();
                User user = new User(name, email, password, confirm);
                reference.child(password).setValue(user);
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Register(){
        fAuth = FirebaseAuth.getInstance();
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String confirm = mConfirmPassword.getText().toString();

        if(TextUtils.isEmpty(name)) {
            mName.setError("Username is required");
            return;
        }
        if(TextUtils.isEmpty(email)) {
            mEmail.setError("email is required");
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Please provide valid email");
            return;
        }
        else if(TextUtils.isEmpty(password)) {
            mPassword.setError("Password is required");
            return;
        }
        else if(password.length()<6) {
            mPassword.setError("Password must be of equal to or more than 6 characters");
        }

        else if (TextUtils.isEmpty(confirm)){
            mConfirmPassword.setError("confirmation password required");
            return;
        }
        else if (!password.equals(confirm)){
            mConfirmPassword.setError("password does not match");
            return;
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "successfully registered",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this,Login.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(Register.this, "Failed to register! Try again",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}