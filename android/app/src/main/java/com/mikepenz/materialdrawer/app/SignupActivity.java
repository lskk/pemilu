package com.mikepenz.materialdrawer.app;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maycol Meza on 15/04/2017.
 */

public class SignupActivity extends AppCompatActivity {

    private TextView loginLink;
    private ImageView imageView;
    private EditText password;
    private EditText nama;
    private EditText nombre;
    private EditText ktp;
    private EditText username;
    private RadioButton male;
    private RadioButton female;
    private Button registrar;
    private int request_code = 1;
    private Bitmap bitmap_foto;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private byte[] bytes;
    private String sex;
    private EditText address;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginLink = (TextView)findViewById(R.id.link_login);
        ktp = (EditText)findViewById(R.id.correo_registro);
        nama = (EditText)findViewById(R.id.namalengkap);
        password = (EditText)findViewById(R.id.password_registro);
        registrar = (Button)findViewById(R.id.btn_registro_usuario);
        username = (EditText)findViewById(R.id.username);
        address = (EditText)findViewById(R.id.alamat);
        phone = (EditText) findViewById(R.id.notelp);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister(view);
            }
        });


        loginLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void userRegister(View view) {
        if (!validar()) return;

        final String sKtp = ktp.getText().toString().trim();
        final String sNama = nama.getText().toString().trim();
        final String sPassword = password.getText().toString().trim();
        final String sUsername = username.getText().toString().trim();
        final String sAddress = address.getText().toString().trim();
        final String sPhone = phone.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_REGISTER, // url api
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  progressDialos.dismiss();
                        try{
                            JSONObject obj = new JSONObject(response);
                            //JSONObject objData = new JSONObject(obj.getString("data"));
                            if(obj.getBoolean("success")) { // berhasil
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // get session
                                SharedPreferences.Editor editor = pref.edit(); // edit session
//                                editor.putString("id", obj.getString("id")); // set session
                                editor.putString("username", sUsername); // set session
//                                editor.putString("username", obj.getString("username")); // set session
                                editor.commit(); // save session
                                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),DrawerActivity.class));
                                finish();
                            }
                            else{ // gagal
//                                JSONArray arrFail = new JSONArray(obj.getString("data"));
//                                JSONObject objFail = arrFail.getJSONObject(0);
                                Toast.makeText(getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>(); // parameter
                params.put("ktp",sKtp);
                params.put("nama",sNama);
                params.put("password",sPassword);
                params.put("username",sUsername);
                params.put("address",sAddress);
                params.put("phone",sPhone);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

//        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
//                R.style.MaterialTheme);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creando cuenta...");
//        progressDialog.show();
    }



    private boolean validar() {
        boolean valid = true;

        String sPassword = password.getText().toString();
        String sKtp = ktp.getText().toString();
        String sUsername = username.getText().toString().trim();
        String sAddress = address.getText().toString().trim();
        String sPhone = phone.getText().toString().trim();

        if (sKtp.isEmpty() || ktp.length() < 15) {
            ktp.setError("No KTP salah");
            valid = false;
        } else {
            ktp.setError(null);
        }

        if (sPassword.isEmpty() || password.length() < 6) {
            password.setError("Enter at least 4 characters");
            valid = false;
        } else {
            password.setError(null);
        }

        if (sUsername.isEmpty() || username.length() < 4) {
            username.setError("Enter at least 4 characters");
            valid = false;
        } else {
            username.setError(null);
        }

        if (sAddress.isEmpty() || address.length() < 10) {
            address.setError("Enter at least 10 characters");
            valid = false;
        } else {
            address.setError(null);
        }

        if (sPhone.isEmpty() || phone.length() < 10) {
            phone.setError("Enter at least 6 characters");
            valid = false;
        } else {
            phone.setError(null);
        }

//        if(male.isChecked()) {
//            sex = "M";
//            male.setError(null);
//            female.setError(null);
//        } else if(female.isChecked()) {
//            sex = "F";
//            female.setError(null);
//            male.setError(null);
//        } else {
//            valid = false;
//            male.setError("Pilih salah satu");
//            female.setError("Pilih salah satu");
//        }

        return valid;
    }

    private byte[] imageToByte(ImageView image) {
        Bitmap bitmapFoto = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapFoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == request_code){
            imageView.setImageURI(data.getData());
            bytes = imageToByte(imageView);

            // para que se vea la imagen en circulo
            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            imageView.setImageDrawable(roundedBitmapDrawable);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}


