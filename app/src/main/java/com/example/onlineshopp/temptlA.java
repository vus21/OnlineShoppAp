package com.example.onlineshopp;

import static com.example.onlineshopp.Database.ConnectFirebase.db;
import static com.example.onlineshopp.Database.ConnectFirebase.setDb;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.onlineshopp.Object.User;
import com.example.onlineshopp.Object.cartItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class temptlA {
    public static boolean isLogin= false;
    public static boolean isUser=true;
    public static String IDuser =null;
    public static String IDCART =null;
    static LocalDate date=LocalDate.now();
    static LocalTime date1 = LocalTime.now();
    public static String Datetimecurrent=String.valueOf(date)+" Timer: "+String.valueOf(date1);
    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        temptlA.isLogin = isLogin;
    }

    public static boolean isIsUser() {
        return isUser;
    }

    public static void setIsUser(boolean isUser) {
        temptlA.isUser = isUser;
    }
    public static List<cartItem> listcart=new ArrayList<>();


    public static boolean hasUpperCase(String str) {
        // Kiểm tra xem chuỗi có ký tự in hoa không
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }
    public static boolean addProduct(cartItem cartfake){
        for(int i=0;i<listcart.size();i++){
            if(listcart.get(i).getItemID().equals(String.valueOf(cartfake.getItemID())) ){
                int currentQuantity = listcart.get(i).getQuantity();
                int newQuantity = currentQuantity + cartfake.getQuantity();
                listcart.get(i).setQuantity(newQuantity);
                return true;
            }
        }
        return false;
    }
    public static User user;
    public static String getTotalBillCart(){
        String result="0";
        if(listcart.isEmpty()){
            return result;
        }
        int sum=0;
        for(int i=0;i<listcart.size();i++){
            sum=sum+(listcart.get(i).getQuantity()*listcart.get(i).getItem().getPrice());
        }
        result= String.valueOf(sum);
        return "Tổng cộng: "+result+ " đ";
    }
    public static  String nameuser;
    public static final  int REQUEST_LOGIN=200;
    public static final  int REQUEST_LOGOUT=201;
    public static final  int REQUEST_CAMERA=100;
    public static final  int REQUEST_FOLDER=101;
    public static final  int REQUEST_DELTAILITEM=199;
    public static final  int REQUEST_GOHOME=198;
    public static final  int REQUEST_ME=197;


}
