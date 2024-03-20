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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Forget extends AppCompatActivity {

    Button btnForget;
    TextInputLayout txtEmail;
    TextInputEditText edtEmail;
    TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        btnForget = findViewById(R.id.btnForget);
        txtEmail = findViewById(R.id.txtEmail);
        edtEmail = findViewById(R.id.edtEmail);
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forget.this,LoginActivity.class));
            }
        });
        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                if (email.equals("")) {
                    txtEmail.setError("Vui Lòng Nhập Email");
                } else {
                    txtEmail.setError(null);
                }
                resetPassWord(email);
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
    void resetPassWord(String email){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                changeInprogress(false);
                if(task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        Utility.showToast(Forget.this,"Quên Mật Khẩu Thành Công,Hãy Kiểm Tra Email Của Bạn");
                    }else {
                        Utility.showToast(Forget.this,"Email không tồn tại");
                    }
                }
            }
        });
    }
    void changeInprogress(boolean inProgress){
        if(inProgress){
            btnForget.setVisibility(View.GONE);
        }else{
            btnForget.setVisibility(View.VISIBLE);
        }
    }
}
