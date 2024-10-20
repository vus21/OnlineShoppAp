package com.example.onlineshopp.ActivityLayout;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.onlineshopp.Database.ConnectSQLite;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.R;
import com.example.onlineshopp.databinding.ActivityprofileuserBinding;
import com.example.onlineshopp.interface1.InterFace;
import com.example.onlineshopp.temptlA;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_profileuser extends AppCompatActivity implements InterFace {
    ActivityprofileuserBinding binding;
    private  final String TAG="Activity_profileuser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityprofileuserBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        setMapping();
        eVentCompoment();
            if(temptlA.IDuser!=null) {
                    getDatauser();

            }else{
                Log.w(TAG, "No user is signed in");
                Toast.makeText(getApplicationContext(), "Vui lòng đăng nhập để xem thông tin cá nhân", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void setMapping() {
    }

    @Override
    public void eVentCompoment() {

            binding.imgback.setOnClickListener(
                    view -> {finish();});
            binding.btnsaveprofile.setOnClickListener(view -> {
                String name,phone,address,date;
                name=binding.userNameTextView.getText().toString();
                phone=binding.phoneNumberEditText.getText().toString();
                address=binding.addressEditText.getText().toString();
                date=binding.birthdayEditText.getText().toString();
                updateProfile(name,phone,address,date);
                finish();
            });


            binding.calendarButton.setOnClickListener(view -> {openDialogDate();});
    }

    @Override
    public void onQuantityChanged() {


    }

    @Override
    public void getDataCheckBox(List<cartItem> mlistcart) {

    }


    private  void getDatauser() {
        ConnectSQLite connect = new ConnectSQLite(getApplicationContext());

        SQLiteDatabase database = connect.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT customer.*, account.Roleid FROM customer JOIN account " +
                "ON account.ID_Customer=customer.ID_Customer " +
                " WHERE customer.ID_Customer= ?", new String[]{temptlA.IDuser});

        if (cursor.moveToFirst()) {
            Log.v(TAG, cursor.getString(0) + "\n" +
                    cursor.getString(1) + "\n" +
                    cursor.getString(2) + "\n" +
                    cursor.getString(3) + "\n" +
                    cursor.getString(4) + "\n" +
                    cursor.getString(5) + "\n" +
                    cursor.getString(6) + "\n" +
                    cursor.getString(7) + "\n");
            binding.emailEditText.setText(cursor.getString(5));
            binding.phoneNumberEditText.setText(cursor.getString(3));
            if (Integer.parseInt(cursor.getString(7)) == 2) {
                binding.fullNameEditText.setText(cursor.getString(1) + "\nKhách hàng");
            } else {
                binding.fullNameEditText.setText(cursor.getString(1) + "\nAdmin");
            }
            if (cursor.getString(2) == null && cursor.getString(4) == null) {
                Log.v(TAG, "null123");
            } else {
                Log.v(TAG, "null1232222");
                binding.addressEditText.setText(cursor.getString(4));
                binding.birthdayEditText.setText(cursor.getString(2));
            }
        }
        else{
            getDatauserFirebase();
        }
    }
    private void getDatauserFirebase(){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DocumentReference documentReference=db.collection("customers").document(temptlA.IDuser);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot=task.getResult();
                    if(snapshot!=null && snapshot.exists()){
                        binding.userNameTextView.setText(snapshot.get("Nameuser").toString());
                        binding.phoneNumberEditText.setText(snapshot.get("Phone").toString());
                        if(snapshot.contains("Address"))
                        binding.userNameTextView.setText(snapshot.get("Address").toString());
                        if (snapshot.contains("Gender")){
                            int Gender = snapshot.getLong("Gender").intValue();
                            if(Gender==1){
                                binding.gender.setText("Nữ");
                            }else{
                                binding.gender.setText("Nam");
                            }
                        }
                    }
                }
            }
        });

    }
    private void updateProfile(String name,String phone,String address,String datebirth){
        Map<String,Object> newdata=new HashMap<>();

        newdata.put("Nameuser",name);
        newdata.put("Phone",phone);
        newdata.put("Address",address);
        newdata.put("DateBirth",datebirth);


        FirebaseFirestore db=FirebaseFirestore.getInstance();

        DocumentReference document=db.collection("customers").document(temptlA.IDuser);

        document.update(newdata).addOnSuccessListener(aVoid->{
            Log.v(TAG,"Sua thanh cong");
            Toast.makeText(getApplicationContext(),"Cập nhật lại thông thành công!!!!",Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e ->{
            Log.w(TAG,"Lỗi khi cập nhật"+e.getMessage());
            Toast.makeText(getApplicationContext(),"Cập nhật thất bại!!!!",Toast.LENGTH_SHORT).show();
        });

    }

    private void openDialogDate(){
        Date n=new Date();
        DatePickerDialog datePicker= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String years=String.valueOf(i);
                String months=String.valueOf(i1);
                String dates=String.valueOf(i2);
                binding.birthdayEditText.setText(i2+"/"+i1+"/"+i);
            }
        },n.getDate(),n.getMonth(),n.getDay());

        datePicker.show();

    }

}