package com.example.onlineshopp.ActivityLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.onlineshopp.Database.ConnectFirebase;
import com.example.onlineshopp.FragmentLayout.Fragment_Home;
import com.example.onlineshopp.R;
import com.example.onlineshopp.databinding.ActivityLoginBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.onlineshopp.temptlA;

import org.jetbrains.annotations.Nullable;

public class Activity_login extends AppCompatActivity {
    String TAG=Activity_login.class.getName();
    ActivityLoginBinding binding;
    TextInputLayout pwdtxtLayout,usertxtLayout;
    Button btnlogin;
    TextView signUpText;
    boolean canLogin=false;
    String i,i1,i2,i3,i4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setMapping();
        ConnectFirebase.setDb();
        eventCompment();
    }

    @SuppressLint("ResourceAsColor")
    private void eventCompment() {
        String pwd=pwdtxtLayout.getEditText().getText().toString();
        String user=usertxtLayout.getEditText().getText().toString();
        if(pwd.isEmpty() || user.isEmpty()){
            btnlogin.setEnabled(false);
        }


        binding.passwordLogin.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validatePassword(editable.toString());
                enabelbtnLogin(user,editable.toString());
            }
        });


        //BtnLogin
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        checkLogin(user,pwd);
            }
        });



        //Listent Click
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Activity_login.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    private void setMapping(){
        usertxtLayout=binding.userNameLogin;
        pwdtxtLayout=binding.passwordLogin;
        btnlogin=binding.loginButton;
        signUpText=binding.signUpText;
    }
    private void validatePassword(String password) {

        // Kiểm tra điều kiện mật khẩu
        if (password.length() < 5 || !hasUpperCase(password)) {
            // Hiện thị biểu tượng lỗi
            pwdtxtLayout.setError(getString(R.string.invalid_password));
            canLogin=false;
        } else {
            // Xóa thông báo lỗi
            pwdtxtLayout.setError(null);
            canLogin=true;
        }
    }
    @SuppressLint("ResourceAsColor")
    void enabelbtnLogin(String user,String pwd){
        if (!canLogin && (user.isEmpty() || pwd.isEmpty()) ){
            btnlogin.setEnabled(false);
            btnlogin.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey));
        }else{
            btnlogin.setEnabled(true);
            btnlogin.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }
    private boolean hasUpperCase(String str) {
        // Kiểm tra xem chuỗi có ký tự in hoa không
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private void checkLogin(String user,String pwd){
        String t=binding.userNameLogin1.getEditableText().toString();
        String t1=binding.passwordLogin1.getEditableText().toString();
        ConnectFirebase.db.collection("accounts").whereEqualTo("email",t).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        //Check Email
                        if(!task.getResult().isEmpty()){
                            for(QueryDocumentSnapshot document:task.getResult()){
                              loginn(t,t1,document);

                            }
                        }else{
                            Log.d(TAG,"Không có tồn tại Email này "+"\n"+user+"\n"+t);
                        }
                    }else{
                        Log.e("LoginActivity", "Error getting documents: ", task.getException());
                        Toast.makeText(Activity_login.this, "Lỗi khi kiểm tra email: " +
                                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void loginn(String u,String p,QueryDocumentSnapshot documentSnapshot){
        i=documentSnapshot.getString("email");
        i1=documentSnapshot.getString("uid");
        i2=documentSnapshot.getString("roleId");
        i3=documentSnapshot.getString("password");
            if(i.equals(u) && i3.equals(p)){
                Intent in1=new Intent();
                in1.putExtra("email",i);
                in1.putExtra("uid",i1);
                in1.putExtra("roleId",i2);
                in1.putExtra("pwd",i3);
                temptlA.setIsLogin(true);
                setResult(RESULT_OK,in1);
                finish();
        }else{
                Toast.makeText(this,"Tai khoan hoac mat khau khong chinh xac",Toast.LENGTH_LONG).show();
            }
    }

    private  void showDialogErrors(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Lỗi Đăng nhập!!!!");
    }
}