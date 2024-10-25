package com.example.onlineshopp.interface1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class readjson extends AsyncTask<Void, Void, String> {
    private Context context;

    public readjson(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String jsonData = null;
        try {
            InputStream is = context.getAssets().open("pori0.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            jsonData = builder.toString();
            reader.close();
        } catch (IOException e) {
            Log.e("ReadJsonTask", "Error reading JSON file: " + e.getMessage());
        }

        return jsonData;
    }
    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray categories = jsonObject.getJSONArray("Category");

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                for (int i = 0; i < categories.length(); i++) {
                    JSONObject category = categories.getJSONObject(i);
                    String id = category.getString("Id");
                    String imagePath = category.getString("ImagePath");
                    String name = category.getString("Name");

                    Map<String, Object> data = new HashMap<>();
                    data.put("Id", id);
                    data.put("ImagePath", imagePath);
                    data.put("Name", name);

                    // Save each category to Firestore
                    db.collection("Banner").document(id).set(data)
                            .addOnSuccessListener(aVoid -> {
                                Log.v("TAG", "Them thanh cong: " + name);
                            })
                            .addOnFailureListener(e -> {
                                Log.w("TAG", "loi them document: " + e.getMessage());
                            });
                }

            } catch (JSONException e) {
                Log.e("ReadJsonTask", "loi parsing JSON: " + e.getMessage());
            }
        } else {
            Log.d("ReadJsonTask", "No data found");
        }
    }


}
