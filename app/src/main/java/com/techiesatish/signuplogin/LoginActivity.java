package com.techiesatish.signuplogin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class LoginActivity extends AppCompatActivity {
    EditText Login_Mob,Login_Pwd;
    TextView Login_signup_here,Login_Forget_Pwd;
    Button Login_Button;
    CheckBox showPWD;
    private static String LOGIN_URL = "";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Login_Mob =(EditText)findViewById(R.id.et_lnmobioe);
        Login_Pwd=(EditText)findViewById(R.id.et_lnpassword);
        Login_signup_here=(TextView)findViewById(R.id.tv_signuphere);
        showPWD=(CheckBox)findViewById(R.id.show_pwd);
        Login_Button = (Button) findViewById(R.id.btn_login);
        Login_signup_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,Signup.class);
                startActivity(intent);
            }
        });
        showPWD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override

        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if(isChecked){
                Login_Pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            Login_Pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
        });
        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Login_UserMob=Login_Mob.getText().toString();
                String Login_UserPass=Login_Pwd.getText().toString();


                if (Login_UserMob.equalsIgnoreCase("")||
                        Login_UserPass.equalsIgnoreCase("")||Login_UserPass.length()>=5)
                {
                    if (Login_UserMob.equalsIgnoreCase("")){
                        Login_Mob.setError("Enter your mobile No ");
                    }
                    else if(Login_UserPass.equalsIgnoreCase("")){
                        Login_Pwd.setError("Enter your password");
                    }
                    else{
                        if(Constants.isOnline(getApplicationContext())){
                            login(LOGIN_URL, Login_UserMob, Login_UserPass);
                        } else {
                            Toast.makeText(getApplicationContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Entries are Wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void login(String loginUrl, final String login_userMob, final String login_userPass) {
        RequestQueue requestQueue=new Volley().newRequestQueue(getApplicationContext());
        StringRequest postRequest=new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);


                    if(jsonObject.getInt("success")==1)
                    {

                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        JSONObject jsonObjectInfo=jsonObject.getJSONObject("User");
                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("name",jsonObjectInfo.getString("name"));
                        intent.putExtra("email",jsonObjectInfo.getString("email"));
                        intent.putExtra("mobile",jsonObjectInfo.getString("mobile"));
                        startActivity(intent);
                    }
                    else

                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })

        {
            protected Map<String,String> getParams(){
                Map<String, String> param = new HashMap<String, String>();
                param.put("mobile", login_userMob);
                param.put("password",login_userPass);
                return param;
            }

        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        requestQueue.add(postRequest);
    }
}
