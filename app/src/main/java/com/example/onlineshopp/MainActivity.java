package com.example.onlineshopp;

import static com.example.onlineshopp.temptlA.REQUEST_GOHOME;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.onlineshopp.Database.ConnectSQLite;
import com.example.onlineshopp.FragmentLayout.FragmentHomeViewModel;
import com.example.onlineshopp.FragmentLayout.FragmentMeViewModel;
import com.example.onlineshopp.FragmentLayout.Fragment_Cart;
import com.example.onlineshopp.FragmentLayout.Fragment_Home;
import com.example.onlineshopp.FragmentLayout.Fragment_me;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.interface1.InterFace;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements InterFace {
    private  String TAG= MainActivity.class.getName();
    BottomNavigationView bottomNavigationView;
    FrameLayout mView;
    Bundle bundle;
    Fragment_Home fragmentHome=new Fragment_Home();
    Fragment_me fragmentMe=new Fragment_me();
    Fragment_Cart fragmentCart=new Fragment_Cart();
    private String int1,int2,int3,int4,int5;
    private FragmentMeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setMapping();
        Intent i=getIntent();
        if(i!=null){
            Log.v("TAG!!!","Da nhan dc "+i.getStringExtra("email"));
        }else{
            Log.v("TAG!!!","Ko  nhan dc ");
        }

        //Khi Chay app  FrameLayout se load Fragment_Home dau` tien
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_view,new Fragment_Home()).commit();
        }

        eVentCompoment();

    }


    @Override
    public void setMapping() {
        //Ánh xạ tới  BottomNavigationView
        bottomNavigationView=findViewById(R.id.btngv);

        //Ánh xạ tới FrameLayout
        mView=findViewById(R.id.framelayout_view);
    }

    @Override
    public void eVentCompoment() {

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                            //Set idItem in Menu
                        int idItem = menuItem.getItemId();
                        if (idItem == R.id.navigation_home) {
//                            Load Fragment_Home
                            loadFragment(fragmentHome);
                        } else if (idItem == R.id.navigation_cart) {
//                            Load Fragment_Search
                            loadFragment(fragmentCart);
                        } else if (idItem == R.id.navigation_notifications) {
//                            Load Fragment_Notification
                            Fragment_me fragmentMe=new Fragment_me();
                            fragmentMe.setArguments(bundle);
                            loadFragment(fragmentMe);
                        } else if (idItem == R.id.navigation_profile) {
//                            Load Fragment_me
                            loadFragment(fragmentMe);
                        }
                        return true;
                    }
        });
    }

    @Override
    public void onQuantityChanged() {

    }

    @Override
    public void getDataCheckBox(List<cartItem> mlistcart) {
    }


    //    Load FragmentLayout
    public void loadFragment(Fragment fragment){

        //Changed FrameLayout if Click  ItemMenu  on BottomNavigationView
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_view,fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GOHOME) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String email = data.getStringExtra("email");
                    String uid = data.getStringExtra("uid");
                    String roleId = data.getStringExtra("roleId");
                    String pwd = data.getStringExtra("pwd");

                    // Gửi dữ liệu vào Fragment_me
                    Fragment_me fragmentMe = new Fragment_me();
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("uid", uid);
                    bundle.putString("roleId", roleId);
                    bundle.putString("pwd", pwd);

                    Log.v("MAINACTIVITY\nSUCCESS", "TẢI DỮ LIỆU THÀNH CÔNG!!!\n" + email + "\n" + roleId + "\n" + pwd);
                    fragmentMe.setArguments(bundle);
                    loadFragment(fragmentMe);
                } else {
                    Log.v("MAINACTIVITY", "DATA NULL");
                }
            } else {
                Log.v("MAINACTIVITY", "KHÔNG NHẬN ĐC REQUEST_GOHOME");
            }
        }else{

        }
    }
}