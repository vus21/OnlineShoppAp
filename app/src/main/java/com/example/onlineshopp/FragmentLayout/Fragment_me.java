package com.example.onlineshopp.FragmentLayout;

import static com.example.onlineshopp.temptlA.REQUEST_GOHOME;
import static com.example.onlineshopp.temptlA.user;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopp.ActivityLayout.ActivityDeltai_Item;
import com.example.onlineshopp.ActivityLayout.Activity_login;
import com.example.onlineshopp.ActivityLayout.Activity_profileuser;
import com.example.onlineshopp.Database.ConnectSQLite;
import com.example.onlineshopp.MainActivity;
import com.example.onlineshopp.Object.ItemFood;
import com.example.onlineshopp.Object.User;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.interface1.InterFace;
import com.example.onlineshopp.R;
import com.example.onlineshopp.temptlA;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment_me extends Fragment implements InterFace {

    private FragmentMeViewModel mViewModel;
    TextView nameuser,tv2;
    ImageView btnimg;
    View mview;
    String roleId;
    Button btn,btn1,btn2,btn3,btn4;
    String down="\n";
    String TAG="Fragment_me";
    String username1, name[];

    public static Fragment_me newInstance() {
        return new Fragment_me();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         mview=inflater.inflate(R.layout.fragment_me, container, false);
        mViewModel = new ViewModelProvider(this).get(FragmentMeViewModel.class);
        // TODO: Use the ViewModel
        setMapping();
        eVentCompoment();



        return mview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void setMapping() {
        btnimg=mview.findViewById(R.id.imgCart);
        tv2=mview.findViewById(R.id.totalitemincart);
        nameuser=mview.findViewById(R.id.userNameTextView);

        btn=mview.findViewById(R.id.editProfileButton);
        btn3=mview.findViewById(R.id.manageButton);
        btn4=mview.findViewById(R.id.logOutButton);
    }

    @Override
    public void eVentCompoment() {
        tv2.setText(ActivityDeltai_Item.slProduct());
        if(!temptlA.isLogin){
            Intent i =new Intent(getActivity(),Activity_login.class);
            startActivityForResult(i, REQUEST_GOHOME);
        }else{
            nameuser.setText(temptlA.nameuser);
            //Logout
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
                }
            });
        }
        if(temptlA.IDuser!=null && !temptlA.IDuser.isEmpty()){
            Log.v(TAG,temptlA.IDuser);
            checkProfile();
            checkRole(user.getRole());
//            getCart(temptlA.IDuser);
//            Log.v(TAG,temptlA.IDCART);
        }

        // Observe user data
        mViewModel.getuser().observe(getViewLifecycleOwner(), userName -> {
            nameuser.setText(userName);
        });

        btn3.setOnClickListener(v ->{
            MainActivity main=new MainActivity();
            main.loadFragment(new Fragment_Cart());
        });


        //goto Activity_profile
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), Activity_profileuser.class);
                startActivity(i);
            }
        });

    }

    public void passData(String data) {
        mViewModel.setuata(data);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GOHOME) {
            if (resultCode == Activity.RESULT_OK) {
                // Cập nhật UI hoặc thực hiện hành động nào đó với username
                if (data!=null){
                    temptlA.IDuser= data.getStringExtra("uid");
                    getInfor(temptlA.IDuser);
                    checkProfile();
                    getCart(user.getID());
                    Log.v("Fragment_Me","Data không rỗng!!!\n"+
                            data.getStringExtra("email")
                                    +"\n"+data.getStringExtra("uid"));
                    Toast.makeText(getActivity(),
                            "Chào mừng "+temptlA.nameuser+" đã quay trở lại!!!",Toast.LENGTH_LONG).show();

                }else{
                    Log.v("Fragment_Me","Data rỗng!!!\n");
                }
            }else{
                Log.v("Fragment_Me","Không có result_Oke!!!\n");
            }
        }else{
            Log.v("Fragment_Me","Không có request!!!\n");
        }
    }

    private void logout(){
        temptlA.setIsLogin(false);temptlA.listcart.clear();
        temptlA.nameuser="";
        Intent intent = new Intent(getActivity(), Activity_login.class);
        startActivity(intent);
        if (getActivity() != null) {
            Log.d(TAG,"Ban da chon Logout");
            getActivity().finish();
        }
    }
    @Override
    public void onQuantityChanged() {

    }

    @Override
    public void getDataCheckBox(List<cartItem> mlistcart) {

    }
    private void getInfor(String id){
        SQLiteDatabase database=new ConnectSQLite(getActivity()).getReadableDatabase();
        String query="SELECT * FROM "+ConnectSQLite.TABLE_6+" WHERE ID_Customer = ?";
        Cursor cursor=database.rawQuery(query,new String[]{id});
        if(cursor.moveToFirst()){
            temptlA.nameuser=cursor.getString(1);
            nameuser.setText(cursor.getString(1));
            temptlA.IDuser=cursor.getString(0);
        }
    }

    private void checkProfile() {
        if ( temptlA.IDuser != null && !temptlA.IDuser.isEmpty() ) {
            ConnectSQLite connect=new ConnectSQLite(getActivity());
            SQLiteDatabase db= connect.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT customer.*, account.Roleid FROM customer JOIN account " +
                            "ON account.ID_Customer = customer.ID_Customer WHERE customer.ID_Customer = ?",
                            new String[]{temptlA.IDuser});
            if(cursor.moveToFirst()){
                temptlA.user=new User(cursor.getString(0),
                        cursor.getString(1),
                        "Nam",
                        cursor.getString(2),
                        cursor.getString(4),
                        cursor.getString(3),
                        cursor.getInt(7));
                Log.v("Roleid", String.valueOf(cursor.getInt(7)));
            }
        }else{
            Log.v("Fragment_me","nguoi dung chua dang nhap");
        }
    }
    private void checkRole(String id){
        if(Integer.parseInt(id)==1){
            btn3.setVisibility(View.VISIBLE);
            Log.v("Fragment_me","Admin da dang nhap , "+temptlA.Datetimecurrent+"\n"+user.getID());
        }else{
            btn3.setVisibility(View.GONE);
        }

    }
        private void getCart(String id) {
            String idtemp = id;
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

            firebaseFirestore.collection("cartdeltai").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id_cust = document.getString("ID_CUs");
                                String id_cart = document.getString("ID");
                                if (id_cust != null && id_cart != null && id_cust.equals(idtemp)) {
                                    temptlA.IDCART = id_cart;
                                    Log.v(TAG, "Giỏ hàng ID: " + id_cart);
                                    break; // Thoát khỏi vòng lặp sau khi tìm thấy
                                } else {
                                    Log.v(TAG, "Không tìm thấy");
                                }
                            }
                        } else {
                            Log.e(TAG, "Không có kết quả");
                        }
                    } else {
                        Log.e(TAG, "Lỗi khi lấy dữ liệu từ Firestore");
                    }

                    if (temptlA.IDCART != null) {
                        CollectionReference coll = firebaseFirestore.collection("cart_customer").document(temptlA.IDCART).collection(id);
                        ConnectSQLite c = new ConnectSQLite(getActivity());
                        SQLiteDatabase dbSQLite = c.getReadableDatabase();

                        coll.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().isEmpty()) {
                                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                            Log.v(TAG, "Tài liệu: " + snapshot.getId());
                                            Cursor cursor1 = dbSQLite.rawQuery("SELECT * FROM " + ConnectSQLite.TABLE_1 + " WHERE ID_product = ?", new String[]{snapshot.getId()});
                                            if (cursor1.moveToFirst()) {
                                                ArrayList<String> listimg = new ArrayList<>();
                                                listimg.add(cursor1.getString(6));
                                                ItemFood item = new ItemFood(
                                                        cursor1.getInt(0),
                                                        cursor1.getString(1),
                                                        cursor1.getInt(2),
                                                        cursor1.getInt(4),
                                                        cursor1.getInt(3),
                                                        cursor1.getInt(5),
                                                        listimg,
                                                        cursor1.getString(7)
                                                );
                                                temptlA.listcart.add(new cartItem(String.valueOf(item.getID()),
                                                        item,
                                                        Integer.parseInt(String.valueOf(snapshot.getLong("Quantity")))));
                                                Log.v(TAG, cursor1.getString(1) + "\n" + cursor1.getString(7) + "\n" + cursor1.getInt(3));
                                            }
                                        }
                                    } else {
                                        Log.e(TAG, "Giỏ hàng rỗng");
                                    }
                                } else {
                                    Log.e(TAG, "Lỗi khi lấy dữ liệu giỏ hàng từ Firestore");
                                }
                            }
                        });
                    } else {
                        Log.e(TAG, "Không tìm thấy IDCART");
                    }
                }
            });
        }


}