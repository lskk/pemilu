package com.mikepenz.materialdrawer.app;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;



public class LoginActivity extends AppCompatActivity {

    private EditText eUsername, ePassword;
    private Button acceder;
    private TextView registrar;
    private String username;
    private String password;
    private Cursor comprobar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eUsername = (EditText)findViewById(R.id.etusuario);
        ePassword = (EditText)findViewById(R.id.etpass);
        acceder = (Button)findViewById(R.id.button);
        registrar = (TextView)findViewById(R.id.signup);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),FormPersetujuan_Registrasi.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
              //  iniciar();

            }
        });
    }

    private void iniciar() {

        if (!validar()) return;

        username = eUsername.getText().toString();
        password = ePassword.getText().toString();
        Intent intent =new Intent(getApplicationContext(),DrawerActivity.class);
        intent.putExtra("IDENT",username);
        startActivity(intent);
        finish();


//        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MaterialBaseTheme);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Iniciando...");
//        progressDialog.show();


        eUsername.getText().clear();
        ePassword.getText().clear();

    }

    private boolean validar() {
        boolean valid = true;

        String username = eUsername.getText().toString();
        String password = ePassword.getText().toString();

        if (username.isEmpty() || username.length() < 4 || username.length() > 10) {
            eUsername.setError("\n" +"Enter a valid username");
            valid = false;
        } else {
            eUsername.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            ePassword.setError("Enter 4 - 10 characters");
            valid = false;
        } else {
            ePassword.setError(null);
        }

        return valid;
    }

    private void userLogin(){
        if (!validar()) return;
        final String username = eUsername.getText().toString().trim();
        final String password = ePassword.getText().toString().trim();
        //progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_LOGIN, // url api
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
          //              progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            //JSONObject objData = new JSONObject(obj.getString("data"));
                            if(obj.getBoolean("success")){ // berhasil
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // get session
                                SharedPreferences.Editor editor = pref.edit(); // edit session
                                editor.putString("id", obj.getString("id")); // set session
                                editor.putString("username", obj.getString("username")); // set session
                                editor.commit(); // save session
                                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),DrawerActivity.class));
                                finish();
                            }
                            else{ // gagal
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
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>(); // parameter
                params.put("username",username);
                params.put("password",password);

                return params;
            }
        };

        //RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        com.mikepenz.materialdrawer.app.RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}