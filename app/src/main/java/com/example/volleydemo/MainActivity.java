package com.example.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText idEditText ;
    TextView nameTextView, emailTextView, addressTextView, phoneTextView;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idEditText  = findViewById(R.id.idEditText);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        addressTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        imageView = findViewById(R.id.imageView);
    }

    public void getUser(View view) {

        // Value entered by user in the editText
        String idUser = idEditText.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.sheety.co/4db58997dd33ab7eaa3d621c48bdea06/contactInfo/sheet1/"+idUser;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        try {
                            JSONObject responseObject = new JSONObject(response);
                            JSONObject sheetObject = responseObject.getJSONObject("sheet1");
                            String name = sheetObject.getString("name");
                            String email = sheetObject.getString("email");
                            // hw - address and phone

                            nameTextView.setText(name);
                            emailTextView.setText(email);

                            String imageUrl = sheetObject.getString("imgurl");
                            Glide.with(MainActivity.this).load(imageUrl).into(imageView);



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nameTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
}