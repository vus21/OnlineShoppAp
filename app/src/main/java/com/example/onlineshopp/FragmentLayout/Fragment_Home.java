        package com.example.onlineshopp.FragmentLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshopp.Adapter.CategoriesAdapter;
import com.example.onlineshopp.Adapter.Pager2Adapter;
import com.example.onlineshopp.Adapter.foodAdapter;
import com.example.onlineshopp.interface1.InterFace;
import com.example.onlineshopp.Object.ItemCat;
import com.example.onlineshopp.Object.ItemFood;
import com.example.onlineshopp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home extends Fragment  implements InterFace {

        private FragmentHomeViewModel mViewModel;
        private RecyclerView dishes_recycler_view,category_recycler_view;
        private ActivityResultLauncher<Intent> activityResultLauncher;
                CategoriesAdapter adapter;
                foodAdapter adapter1;
                ViewPager2 viewpg2;
        private InterFace dataPasser;


    // Gắn interface với activity trong phương thức onAttach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataPasser=(InterFace) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }
    // Phương thức để gọi và truyền dữ liệu
        public static Fragment_Home newInstance() {
            return new Fragment_Home();
        }
        @SuppressLint("MissingInflatedId")
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View mview =inflater.inflate(R.layout.fragment_home, container, false);

                dishes_recycler_view=mview.findViewById(R.id.dishes_recycler_view);
                category_recycler_view=mview.findViewById(R.id.category_recycler_view);
                viewpg2=mview.findViewById(R.id.banner_view);


            // Đăng ký launcher để nhận kết quả từ Activity khác
            activityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            // Nhận dữ liệu trả về từ Activity khác
                            Intent data = result.getData();
                            String returnedData = data.getStringExtra("key");
                            // Xử lý dữ liệu, cập nhật giao diện, vv...
                            Log.d("Fragment_HOME", "Data returned: " + returnedData);
                        }
                    });

            return mview;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mViewModel = new ViewModelProvider(this).get(FragmentHomeViewModel.class);
            // TODO: Use the ViewModel
            mViewModel.loadlistCat();
            mViewModel.loadlistFood();

            category_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

            dishes_recycler_view.setLayoutManager(new GridLayoutManager(getActivity(),2,LinearLayoutManager.VERTICAL,false));

            mViewModel.getlistCat().observe(getViewLifecycleOwner(), new Observer<List<ItemCat>>() {
                @Override
                public void onChanged(List<ItemCat> itemCats) {
                    // Khi dữ liệu thay đổi, cập nhật RecyclerView
                    if (itemCats != null) {
                       adapter = new CategoriesAdapter(itemCats); // Khởi tạo adapter với dữ liệu
                        category_recycler_view.setAdapter(adapter); // Cài đặt adapter cho RecyclerView

                    }
                }
            });

            mViewModel.getlistfood().observe(getViewLifecycleOwner(), new Observer<List<ItemFood>>() {
                @Override
                public void onChanged(List<ItemFood> itemFoods) {
                    // Khi dữ liệu thay đổi, cập nhật RecyclerView
                    if (itemFoods != null) {
                        adapter1 = new foodAdapter(itemFoods); // Khởi tạo adapter với dữ liệu
                        dishes_recycler_view.setAdapter(adapter1); // Cài đặt adapter cho RecyclerView
                    }
                }
            });
            LoadImg();

        }

            @Override
            public void setMapping() {

            }

            @Override
            public void eVentCompoment() {

            }

            @Override
            public void onQuantityChanged() {

            }

    @Override
    public void onDatapass() {
        if (dataPasser != null) {
            dataPasser.onDatapass();
        }
    }

    private void LoadImg(){
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=database.getReference("Category");
                        List<String> mlistt=new ArrayList<>();
                        mlistt.clear();
                Pager2Adapter adpa =new Pager2Adapter(mlistt);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child:snapshot.getChildren()){
                            // get picUrls
                           mlistt.add(child.child("picUrl").getValue(String.class).toString());
                           Log.v("Fragment_Home\nLoad thanh cong Anh",child.child("picUrl").getValue(String.class).toString());
                            }
                          adpa.notifyDataSetChanged();
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                viewpg2.setAdapter(adpa);
                viewpg2.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }

}