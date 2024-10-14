package com.example.onlineshopp.FragmentLayout;

import static com.example.onlineshopp.temptlA.REQUEST_GOHOME;
import static com.example.onlineshopp.temptlA.REQUEST_LOGIN;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopp.ActivityLayout.ActivityDeltai_Item;
import com.example.onlineshopp.ActivityLayout.Activity_login;
import com.example.onlineshopp.interface1.InterFace;
import com.example.onlineshopp.R;
import com.example.onlineshopp.temptlA;

public class Fragment_me extends Fragment implements InterFace {

    private FragmentMeViewModel mViewModel;
    TextView nameuser,tv2;
    View mview;
    String roleId;
    Button btn,btn1,btn2,btn3,btn4;
    String down="\n";
    String TAG="Fragment_me";
    String username1;


    private InterFace dataPasser;

    // Giao diện để truyền dữ liệu

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Đảm bảo Activity implements interface
        dataPasser = (InterFace) context;
    }

    // Phương thức để gửi dữ liệu
    public void sendData(String data) {
        dataPasser.onDatapass();
    }

    // Trong phương thức nào đó (ví dụ: khi nhấn nút)
    public void onButtonClicked() {
        String dataToSend = "Some data";
        sendData(dataToSend);
    }
    public static Fragment_me newInstance() {
        return new Fragment_me();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         mview=inflater.inflate(R.layout.fragment_me, container, false);

        nameuser=mview.findViewById(R.id.userNameTextView);


        return mview;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentMeViewModel.class);
        // TODO: Use the ViewModel

        if(!temptlA.isLogin){
            startActivityForResult(new Intent(getActivity(), Activity_login.class), REQUEST_GOHOME);
        }else{
            btn4=mview.findViewById(R.id.logOutButton);
            nameuser.setText(temptlA.nameuser);

            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    temptlA.setIsLogin(false);temptlA.listcart.clear();
                }
            });
        }

            setMapping();
        eVentCompoment();
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
                    temptlA.nameuser=data.getStringExtra("email");

                    sendData(data.getStringExtra("email"));
                    Log.v("Fragment_Me","Data không rỗng!!!\n"+temptlA.nameuser+"\n");
                }else{
                    Log.v("Fragment_Me","Data rỗng!!!\n");
                    nameuser.setText("Not thing on me");
                }
            }else{
                Log.v("Fragment_Me","Không có result_Oke!!!\n");
            }
        }else{
            Log.v("Fragment_Me","Không có request!!!\n");
        }
    }


    @Override
    public void setMapping() {
        tv2=mview.findViewById(R.id.totalitemincart);
    }

    @Override
    public void eVentCompoment() {
        tv2.setText(ActivityDeltai_Item.slProduct());

        if(username1!=null){
            nameuser.setText(username1);
        }

        // Observe user data
        mViewModel.getuser().observe(getViewLifecycleOwner(), userName -> {
            nameuser.setText(userName);
        });

    }

    @Override
    public void onQuantityChanged() {

    }

    @Override
    public void onDatapass() {
            Log.d("ADWDa","Du lieu tu fragme_dc chuyen ve actovtymain");
    }
}