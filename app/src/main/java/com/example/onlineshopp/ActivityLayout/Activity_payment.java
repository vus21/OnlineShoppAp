package com.example.onlineshopp.ActivityLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshopp.Adapter.itemselectAdapter;
import com.example.onlineshopp.Database.ConnectFirebase;
import com.example.onlineshopp.MainActivity;
import com.example.onlineshopp.MainViewModel;
import com.example.onlineshopp.Object.ItemCat;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.R;
import com.example.onlineshopp.Zalopay.Api.CreateOrder;
import com.example.onlineshopp.databinding.ActivityPaymentBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class Activity_payment extends AppCompatActivity  {
    ActivityPaymentBinding binding;
    private final String TAG="Activity_payment";
    private ArrayList<String> listmethods=new ArrayList<>();
    private ArrayList<Integer> listfreeship=new ArrayList<>();
    private  int selectionPosition =0;

    private List<ItemCat> itemselect =new ArrayList<>();

    private itemselectAdapter adapter;
    private String[] rows;
    private List<cartItem> mlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPaymentBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        Log.d(TAG,user.getUid());
        Intent getint=getIntent();
        if (getint!=null){
         int size;
            size=Integer.parseInt(getint.getStringExtra("sizecart"));
            for(int i=0;i<size;i++){
                cartItem item = (cartItem) getint.getSerializableExtra("Item"+String.valueOf(i));
                Log.d(TAG,item.getItemID()+"\nQuantity"+item.getQuantity()+"\nAmount: "+item.getQuantity()*item.getItem().getPrice());
                mlist.add(item);
            }
            Log.v(TAG,"Size : "+getint.getStringExtra("sizecart")+"\n"+size);
        }else {
            Log.d(TAG,"Khong co nhan dc j");
        }
        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);
        listmethods.add("Thanh toán COD ");
        listmethods.add("Thanh toán ZaloPay ");
        listmethods.add("Thanh toán MOMO ");
        listmethods.add("Thanh toán bằng thẻ tín dụng ");
        listfreeship.add(30000);
        listfreeship.add(25000);
        listfreeship.add(41000);
        listfreeship.add(15000);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplication(),R.layout.itempayment,listmethods);
        binding.methodship.setAdapter(adapter);
        setupProfile("51wdvJisLrhoMJmWCgSKG2fjfL92");


        binding.methodship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setFreeship(i);
                selectionPosition = i;
                Log.i(TAG, adapter.getItem(i) + "Bạn đã chọn cách thanh toán" + adapterView.getItemAtPosition(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setFreeship(0);
            }
        });

        setRecycle();

        //BtnPayment
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(getApplicationContext(),Activity_notifaction.class);
                Log.d(TAG, String.valueOf(selectionPosition));
                    switch (selectionPosition){
                        //Methods COD
                        case 0:
                                    i1.putExtra("result",R.string.thanks_user);
                            break;
                            //Methods ZaloPay
                        case 1:
                            CreateOrder orderApi = new CreateOrder();

                            try {
                                JSONObject data = orderApi.createOrder(rows[0],binding.editTextTextname.getText().toString());
                                Log.d("Amount", "900");
                                String code = data.getString("return_code");
                                Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

                                if (code.equals("1")) {
                                    //zp+trans_token this is token
                                    String token =data.getString("zp_trans_token").toString();
                                    Log.d("Token",data.getString("zp_trans_token")+"\n"+code+"\nAmount: "+"900");
                                    ZaloPaySDK.getInstance().payOrder(Activity_payment.this, token, "demozpdk://app", new PayOrderListener() {
                                        @Override
                                        public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    new AlertDialog.Builder(getApplication())
                                                            .setTitle("Payment Success")
                                                            .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    i1.putExtra("result",R.string.thanks_user);
                                                                }
                                                            })
                                                            .setNegativeButton("Cancel", null).show();
                                                }

                                            });
                                        }

                                        @Override
                                        public void onPaymentCanceled(String zpTransToken, String appTransID) {
                                            new AlertDialog.Builder(getApplication())
                                                    .setTitle("User Cancel Payment")
                                                    .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    })
                                                    .setNegativeButton("Cancel", null).show();
                                        }

                                        @Override
                                        public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                                            new AlertDialog.Builder(getApplication())
                                                    .setTitle("Payment Fail")
                                                    .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    })
                                                    .setNegativeButton("Cancel", null).show();
                                        }
                                    });
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            break;

                            //momo
                        case 2:

                            i1.putExtra("result",R.string.thanks_user);
                            break;
                        case 3:
                            i1.putExtra("result",R.string.thanks_user);
                            break;
                    }
                    startActivity(i1);


            }
        });

        binding.imageView5.setOnClickListener(view -> {
            finish();

        });

    }

    private void setRecycle(){
        adapter=new itemselectAdapter(mlist);
        binding.recyitembuy.setAdapter(adapter);
        binding.recyitembuy.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        int total=0;
        for(cartItem item:mlist){
            total+=(item.getQuantity()*item.getItem().getPrice());
        }
        String cut=binding.totalPrice.getText().toString();
        rows=cut.split("đ");
        binding.totalPrice.setText(String.valueOf(total)+"đ");

    }
    private void setFreeship(int i){
            switch (i){
                case 0:
                    binding.shippingfree.setText(String.valueOf(listfreeship.get(0)));
                    break;
                case 1:
                    binding.shippingfree.setText(String.valueOf(listfreeship.get(1)));
                    break;
                case 2:
                    binding.shippingfree.setText(String.valueOf(listfreeship.get(2)));
                    break;
                default:
                    binding.shippingfree.setText(String.valueOf(listfreeship.get(3)));
                    break;
            }
    }



    private void setupProfile(String id){
        ConnectFirebase.setDb();

        ConnectFirebase.db.collection("customers").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot snapshot=task.getResult();
                    if(snapshot!=null && snapshot.exists()){
                    Log.v("TAG",(String)snapshot.get("ID_Customer") +
                            "\nNameuser: "+(String)snapshot.get("Nameuser")+
                            "\nPhone: "+(String) snapshot.get("Phone")+
                            "\nRoleid: "+(Long)snapshot.get("Roleid"));
                    binding.editTextTextname.setText((String)snapshot.get("Nameuser"));
                    binding.editTextphone.setText((String)snapshot.get("Phone"));
                        if(snapshot.contains("Address")){
                            Log.v("TAG", (String) snapshot.get("Address"));
                            binding.editTextphone.setText((String)snapshot.get("Address"));
                        }else {
                            Log.v("TAG", "Khong ton tai Field: Address");
                        }
                    }
                }
            }
        });
    }


    private void SaveOrderinFirebase(){

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }


}