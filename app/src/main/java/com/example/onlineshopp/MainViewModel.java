package com.example.onlineshopp;

import static com.example.onlineshopp.Database.ConnectSQLite.TABLE;
import static com.example.onlineshopp.Database.ConnectSQLite.TABLE_1;
import static com.example.onlineshopp.Database.ConnectSQLite.TABLE_6;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.onlineshopp.Database.ConnectFirebase;
import com.example.onlineshopp.Database.ConnectSQLite;
import com.example.onlineshopp.FragmentLayout.Fragment_Home;
import com.example.onlineshopp.Object.ItemCat;
import com.example.onlineshopp.Object.ItemFood;
import com.example.onlineshopp.Object.User;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.Zalopay.Api.CreateOrder;
import com.example.onlineshopp.databinding.ActivityCartBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class MainViewModel extends AppCompatActivity {
    ActivityCartBinding binding;
    String TAG="MainViewModel";
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
    private  void addtoSQLite(SQLiteDatabase database){
        String[] mang1={"Stylish Plaid Shirt",
                "Blue Cosmetic Bag",
                "Cozy Knit Sweater",
                "Wireless Headphones",
                "Professional Camera",
                "Powerful Laptop"};
        String[] mang2={"https://firebasestorage.googleapis.com/v0/b/project186-de05e.appspot.com/o/item1.png?alt=media&token=35832091-4da1-4d4f-9817-6267b90462ac"
                ,"https://firebasestorage.googleapis.com/v0/b/project186-de05e.appspot.com/o/item2.png?alt=media&token=5a796b9e-0e16-46a4-8d0d-83d107db708f"
                ,"https://firebasestorage.googleapis.com/v0/b/project186-de05e.appspot.com/o/item0.png?alt=media&token=4aa495a9-8621-4b6f-8af2-729d71218fd4"
                ,"https://firebasestorage.googleapis.com/v0/b/project186-de05e.appspot.com/o/item4.png?alt=media&token=b913c6b7-afee-4904-9f19-dbc8db093637"
                ,"https://firebasestorage.googleapis.com/v0/b/project186-de05e.appspot.com/o/item5.png?alt=media&token=65f7648d-99fd-4ae1-b6bf-778f8fa46c04"
                ,"https://firebasestorage.googleapis.com/v0/b/project186-de05e.appspot.com/o/item6.png?alt=media&token=dd526c8e-66df-448d-a51f-fa4dbdef6970"
        };
        int[] inventory={25,40,60,30,320,2000};
        int[] price={35,55,75,40,390,1850};
        for(int i=0;i<6;i++) {
            Random random= new Random();
            ContentValues content = new ContentValues();
            content.put("Product_name", mang1[i]);
            content.put("ProductTypeID", random.nextInt(3));
            content.put("Inventory", inventory[i]);
            content.put("PriceOriginal", price[i]);
            content.put("DiscountPrice", random.nextInt(100));
            content.put("Images",mang2[i]);
            content.put("Descr", " Sản phẩm này không phải là thuốc không có tác dụng thay thế thuốc chữa bệnh\n Không dành cho trẻ em dưới 18 tuổi");
            long result = database.insert(ConnectSQLite.TABLE_1, null, content);
            if (result == -1) {
                Log.e("DB Insert Error", "Failed to insert data\nLuot thu i: " +i);
            } else {
                Log.v("DB Insert Success", "Data inserted with ID: " + result+"\nLuot thu i: "+i);
            }
        }
    }
    private  void addaccount(@NonNull SQLiteDatabase database){

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("ID_Customer", "ysGSYKsEJlYQI6e2sCRd9bGbXJs2");
        contentValues1.put("Nameuser", "trunghieuhsdd1@gmail.com");
        contentValues1.put("Password", "123456A");
        contentValues1.put("Roleid", 1); // Role Admin
        contentValues1.put("UpdateAt", "2024-10-15");
        long result1=database.insert(TABLE, null, contentValues1);
        if(result1==-1){
            Log.v(TABLE,"Ko them thanh cong");
        }else{
            Log.v(TABLE,"Them thanh cong");
        }
    }
    private void addCustomer(SQLiteDatabase database) {
        String[] id_cus = {"ysGSYKsEJlYQI6e2sCRd9bGbXJs2", "KH0001", "KH0002", "KH0003", "KH0004", "KH0005"};
        String[] name_cus = {"Triệu Trung Hiếu", "Nguyễn Vinh Quang", "Nguyễn Cương Vũ", "Nguyễn Bùi Thắng", "Lê Thế Long", "Thằng đbrr"};
        String[] date_cus = {"19/03/2003", "16/10/2004", "17/10/2004", "18/10/2004", "19/10/2004", "20/10/2004"};
        String[] phone_cus = {"123456789", "123456789", "123456789", "9513574862", "369852147", "123698745"};
        String[] add_cus = {"Khu Ga Đồng Đăng ", "Thái Bình", "Yên Bái", "Hà Đông", "37 Nghệ An", "36 Quê Tôi"};
        String[] email_cus = {"trunghieuhsdd1@gmail.com", "123456789@gmail.com", "123456789@edu.vn", "9513574862@ahihi", "369852147@dădawd", "123698745@d.a.ưdad"};

        for (int i = 0; i < id_cus.length; i++) { // Chỉ sử dụng id_cus.length
            ContentValues values = new ContentValues();
            values.put("ID_Customer", id_cus[i]);
            values.put("Name_cus", name_cus[i]);
            values.put("dateBirth", date_cus[i]);
            values.put("Phone", phone_cus[i]);
            values.put("Address", add_cus[i]);
            values.put("Email", email_cus[i]);
            Date today = new Date();
            String date = String.valueOf(today.getDate()) + "/" + String.valueOf(today.getMonth() + 1) + "/" + String.valueOf(today.getYear() + 1900);
            values.put("updateDate", date);
            long insert = database.insert(TABLE_6, null, values);
            if (insert == -1) {
                Log.v("INSERT SQLITE", "Thêm không thành công ở " + i);
            } else {
                Log.v("INSERT SQLITE", "Thêm thành công ở " + i);
            }
        }
    }

    private  void deletedProduct(SQLiteDatabase database){
        int[] mang={2,3,4};
        String whereClause = "ID=?";
        for(int i=0;i<3;i++){
            String[] whereArgs = new String[] { String.valueOf(mang[i])};
          int deletedRows=  database.delete(TABLE,whereClause,whereArgs);
            Log.v("ConnectSQlite", "Deleted Rows: " + deletedRows);
        }
    }

    private void updateSqlite(SQLiteDatabase database){
 String[] id_cus={"C001","KH0001","KH0002","KH0003","KH0004","KH0005","KH0006"};
 String where="ID_Customer = ?";
        for (int i=0;i<6;i++){
            ContentValues values =new ContentValues();
            String[] whereArgs = new String[] { id_cus[i]};
            String date="16/10/2024";
            values.put("updateDate",date);
            if(database.update(TABLE_6,values,where,whereArgs)<0){
                Log.v("Update","Update that bai "+i);
            }else{
                Log.v("Update","Update thanh cong "+i);

            }
        }
    }

}
