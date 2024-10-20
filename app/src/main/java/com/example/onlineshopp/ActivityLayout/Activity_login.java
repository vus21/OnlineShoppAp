package com.example.onlineshopp.ActivityLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.onlineshopp.Database.ConnectSQLite;
import com.example.onlineshopp.FragmentLayout.Fragment_Home;
import com.example.onlineshopp.MainActivity;
import com.example.onlineshopp.R;
import com.example.onlineshopp.databinding.ActivityLoginBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.onlineshopp.temptlA;

import org.jetbrains.annotations.Nullable;

public class Activity_login extends AppCompatActivity {
    String TAG=Activity_login.class.getName();
    ActivityLoginBinding binding;
    TextInputEditText  pwdtxt,usertxt;
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
        ConnectSQLite db=new ConnectSQLite(getApplicationContext());
        SQLiteDatabase d1=db.getWritableDatabase();
        setMapping();
        ConnectFirebase.setDb();
        eventCompment();
    }

    @SuppressLint("ResourceAsColor")
    private void eventCompment() {

                String pwd=pwdtxt.getText().toString();
                String user=usertxt.getText().toString();
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
                Log.v("TAG",pwdtxt.getText().toString()+"\n"+usertxt.getText().toString());
                checkLogin(usertxt.getText().toString(),pwdtxt.getText().toString());

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
        usertxt=binding.userNameLogin1;
        pwdtxt=binding.passwordLogin1;
        btnlogin=binding.loginButton;
        signUpText=binding.signUpText;

    }
    private void validatePassword(String password) {

        // Kiểm tra điều kiện mật khẩu
        if (password.length() < 5 || !temptlA.hasUpperCase(password)) {
            // Hiện thị biểu tượng lỗi
            pwdtxt.setError(getString(R.string.invalid_password));
            canLogin=false;
        } else {
            // Xóa thông báo lỗi
            pwdtxt.setError(null);
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


    private void checkLogin(String user,String pwd){
        SQLiteDatabase database=new ConnectSQLite(getApplicationContext()).getWritableDatabase();
        Cursor cursor;
        cursor=database.rawQuery("SELECT * FROM "+ConnectSQLite.TABLE+" WHERE Nameuser = ? AND Password = ?", new String[]{user,pwd});
        String id=null;
        if(cursor.getCount() ==1){
            if(cursor.moveToNext()){
                Log.v("Activity_login",temptlA.Datetimecurrent+"\n"+"Login is Successfully \n "+
                        cursor.getString(0)+"\nId_customer: "+
                        cursor.getString(1));
                id=cursor.getString(0);
                updateTime(cursor.getString(1),database);
                Intent in1=new Intent();
                in1.putExtra("id",cursor.getString(0));
                in1.putExtra("uid",id);
                in1.putExtra("roleId",cursor.getString(2));
                in1.putExtra("pwd",cursor.getString(3));
                temptlA.setIsLogin(true);
                setResult(RESULT_OK,in1);
                finish();
            }
        }else{
            Log.v("TAG","Loi dang nhap !!1");
            Toast.makeText(getApplicationContext(),"Tai khoan hoac mat khau khong chinh sac vui long nhap lai",Toast.LENGTH_LONG).show();
        }

    }
    private void updateTime(String id,SQLiteDatabase database){
        if(!id.isEmpty()){
            ContentValues values=new ContentValues();
            values.put("UpdateAt",temptlA.Datetimecurrent);
            database.update(ConnectSQLite.TABLE,values,"ID_Customer = ? ",new String[]{id});
            Log.v("Activity_login","Ban ghi  dc cap nhat");
        }else{
            Log.v("Activity_login","Ban ghi chua dc cap nhat");
        }
    }

}