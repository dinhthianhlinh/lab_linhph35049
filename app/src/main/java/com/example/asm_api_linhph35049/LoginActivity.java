package com.example.asm_api_linhph35049;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextInputEditText edtEmail = findViewById(R.id.edtEmail);
        TextInputLayout txtEmail = findViewById(R.id.txtEmail);
        TextInputEditText edtPass = findViewById(R.id.edtPass);
        TextInputLayout txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        CheckBox cboRemeber = findViewById(R.id.cboRemember);
        TextView tvQuenMK = findViewById(R.id.tvQuenMK);
        TextView tvDangKy = findViewById(R.id.tvDangKy);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Home.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString();

                if (email.equals("") || pass.equals("")) {

                    if (email.equals("")) {
                        txtEmail.setError("Vui Lòng Nhập UserName");
                    } else {
                        txtEmail.setError(null);
                    }
                    if (pass.equals("")) {
                        txtPass.setError("Vui Lòng Nhập Mật Khẩu");
                    } else {
                        txtPass.setError(null);
                    }



                }
                loginAccountInFireBase(email,pass);
            }
        });
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,DangKiActivity.class);
                startActivity(intent);
            }
        });
        tvQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Forget.class);
                startActivity(intent);
            }
        });




        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    txtPass.setError("Vui Lòng Nhập PassWord");
                }else if(s.length() <=6){
                    txtPass.setError("Vui lòng nhập lớn hơn 6 ký tự");
                }
                else {
                    txtPass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = edtEmail.getText().toString().trim();
                if (s.length() == 0)
                {
                    txtEmail.setError("Vui Lòng Nhập Email");
                }
                else if(!isValidEmail(email)){
                    txtEmail.setError("Email không đúng định dạng");
                }else{
                    txtEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }
    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    void loginAccountInFireBase(String email,String pass){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInprogress(false);
                if(task.isSuccessful()){
//                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                    startActivity(new Intent(LoginActivity.this,Home.class));
//                    }else {
//                        Utility.showToast(LoginActivity.this,"Email không tồn tại vui lòng kiểm tra lại Email");
//                    }
                }else{
                    Utility.showToast(LoginActivity.this,task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void changeInprogress(boolean inProgress){
        if(inProgress){
            btnLogin.setVisibility(View.GONE);
        }else{
            btnLogin.setVisibility(View.VISIBLE);
        }
    }
}