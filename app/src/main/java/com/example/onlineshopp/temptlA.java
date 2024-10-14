package com.example.onlineshopp;

import com.example.onlineshopp.Object.cartItem;

import java.util.ArrayList;
import java.util.List;

public class temptlA {
    public static boolean isLogin= false;
    public static boolean isUser=true;

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
    public static String getTotalBillCart(){
        String result="0";
        if(listcart.isEmpty()){
            return result;
        }
        int sum=0;
        for(int i=0;i<listcart.size();i++){
            sum=sum+(listcart.get(i).getQuantity()*listcart.get(i).getItem().getCostNew());
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


}
