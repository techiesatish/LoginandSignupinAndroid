package com.techiesatish.signuplogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
TextView tvMainName,tvMainEmail,tvMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMainName=(TextView)findViewById(R.id.tv_mainname);
        tvMainEmail=(TextView)findViewById(R.id.tv_mainemail);
        tvMobile=(TextView)findViewById(R.id.tv_mainmob);

        Bundle bundle=getIntent().getExtras();
        tvMainName.setText(bundle.getString("name", String.valueOf(bundle)));
        tvMainEmail.setText(bundle.getString("email",String.valueOf(bundle)));
        tvMobile.setText(bundle.getString("mobile",String.valueOf(bundle)));


    }
}