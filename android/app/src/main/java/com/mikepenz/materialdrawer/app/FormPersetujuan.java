package com.mikepenz.materialdrawer.app;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.app.service.GPSTracker;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Maycol Meza on 15/04/2017.
 */

public class FormPersetujuan extends AppCompatActivity {
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private static final int PROFILE_SETTING = 100000;


    private Button lanjutkan;
    private int request_code = 1;
    private Bitmap bitmap_foto;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private byte[] bytes;
    private GPSTracker gps;
    private TextView username;
    private Double latitude;
    private Double longitude;
    private TextView date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_persetujuan);

        Calendar calander;
        SimpleDateFormat simpledateformat;
        final String Date;
        final TextView DisplayDateTime;

        DisplayDateTime = (TextView)findViewById(R.id.date);
        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpledateformat.format(calander.getTime());

        lanjutkan = (Button) findViewById(R.id.btn_input);
        username = (TextView) findViewById(R.id.txtLabel);
        gps = new GPSTracker(FormPersetujuan.this);
        bitmap_foto = BitmapFactory.decodeResource(getResources(), R.drawable.logoprofile);
        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DisplayDateTime.setText(Date);
                userInput(view);

                Toast.makeText(getApplicationContext(), "Data berhasil diambil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),InputDataPolling.class));
                finish();

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        TextView text = (TextView) findViewById(R.id.txtLabel);
        text.setText(pref.getString("username", "username"));

        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460").withIdentifier(100);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile/*,
                        profile2,
                        profile3,
                        profile4,
                        profile5,
                        profile6,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(100001)*/
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && profile.getIdentifier() == PROFILE_SETTING) {
                            int count = 100 + headerResult.getProfiles().size() + 1;
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman" + count).withEmail("batman" + count + "@gmail.com").withIcon(R.drawable.profile5).withIdentifier(count);
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withItemAnimator(new AlphaCrossFadeAnimator())
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new ExpandableDrawerItem().withName("Aktivitas Data Polling").withIcon(FontAwesome.Icon.faw_bar_chart).withIdentifier(19).withSelectable(false).withSubItems(
                                new SecondaryDrawerItem().withName("Masukkan Data Polling").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(1),
                                new SecondaryDrawerItem().withName("Galeri Data Polling").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(2)
                        ),
                        new ExpandableDrawerItem().withName("Aktivitas Pemilu").withIcon(FontAwesome.Icon.faw_bar_chart).withIdentifier(19).withSelectable(false).withSubItems(
                                new SecondaryDrawerItem().withName("Masukkan Situasi Pemilu").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(3),
                                new SecondaryDrawerItem().withName("Galeri Situasi Pemilu").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(4)
                        ),
                        new PrimaryDrawerItem().withName("Maps").withIcon(FontAwesome.Icon.faw_map_marker).withIdentifier(5).withSelectable(false),
                        new ExpandableDrawerItem().withName("Panduan").withIcon(FontAwesome.Icon.faw_bar_chart).withIdentifier(19).withSelectable(false).withSubItems(
                                new SecondaryDrawerItem().withName("Proses Pemungutan Suara").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(6),
                                new SecondaryDrawerItem().withName("Suara Sah/Tidak Sah").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(7),
                                new SecondaryDrawerItem().withName("Visi dan Misi Kandidat").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(8)

                        ),
                        new PrimaryDrawerItem().withName("Panduan Aplikasi").withIcon(FontAwesome.Icon.faw_bar_chart).withIdentifier(9).withSelectable(false)

                        // new PrimaryDrawerItem().withName("Portal").withIcon(FontAwesome.Icon.faw_archive).withIdentifier(5).withSelectable(false)
                ) // add the items we want to use with our Drawer

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(FormPersetujuan.this, FormPersetujuan.class);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(FormPersetujuan.this, PageFormC1.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(FormPersetujuan.this, FormPersetujuan_Situasi.class);
                            } else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(FormPersetujuan.this, PageSituasi.class);
                            } else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(FormPersetujuan.this, Maps.class);
                            } else if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(FormPersetujuan.this, Panduan1.class);
                            } else if (drawerItem.getIdentifier() == 7) {
                                intent = new Intent(FormPersetujuan.this, Panduan2.class);
                            } else if (drawerItem.getIdentifier() == 8) {
                                intent = new Intent(FormPersetujuan.this, Panduan3.class);
                            } else if (drawerItem.getIdentifier() == 9) {
                                intent = new Intent(FormPersetujuan.this, Panduan4.class);
                            } else if (drawerItem.getIdentifier() == 20) {
                                intent = new LibsBuilder()
                                        .withFields(R.string.class.getFields())
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                        .intent(FormPersetujuan.this);
                            }
                            if (intent != null) {
                                gps.stopUsingGPS();
                                gps = null;
                                FormPersetujuan.this.startActivity(intent);
                                finish();
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
//              .withShowDrawerUntilDraggedOpened(true)
                .build();

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 11
            result.setSelection(21, false);

            //set the active profile
            headerResult.setActiveProfile(profile);
        }

        result.updateBadge(4, new StringHolder(10 + ""));

    }



    public void userInput(View view) {
        if (!validar()) return;
        if (gps.canGetLocation()) {
            lanjutkan.setText("Mohon Tunggu");
            lanjutkan.setEnabled(false);

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            final TextView sUsername = (TextView) findViewById(R.id.txtLabel);
            final String sUsername_data = sUsername.getText().toString().trim();


            final TextView sDisplayDateTime = (TextView)findViewById(R.id.date);
            final String sDisplayDateTime_data = sDisplayDateTime.getText().toString().trim();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_USER_LOCATION, // url api
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  progressDialos.dismiss();
                            try{
                                JSONObject obj = new JSONObject(response);
                                //JSONObject objData = new JSONObject(obj.getString("data"));
                                Log.e("Response: ", response.toString());
                                if(obj.getBoolean("success")) { // berhasil
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // get session
                                    SharedPreferences.Editor editor = pref.edit(); // edit session
//                                editor.putString("id", obj.getString("id")); // set session
                                    editor.putString("username", sUsername_data);
//                                    editor.putString("id", obj.getString("id")); // set session
//                                editor.putString("username", sUsername_data); // set session
//                                editor.putString("username", "username"); // set session
                                    Log.i("username",sUsername_data);
                                    editor.commit(); // save session
                                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),InputDataPolling.class));
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
                    params.put("username",sUsername_data);
                    params.put("date",sDisplayDateTime_data);
                    params.put("latitude", latitude.toString());
                    params.put("longitude", longitude.toString());


                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }
        else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }



    private boolean validar() {
        boolean valid = true;


        String sUsername = username.getText().toString().trim();


        if (sUsername.isEmpty() || username.length() < 4) {
            username.setError("Enter at least 4 characters");
            valid = false;
        } else {
            username.setError(null);
        }



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
//        if(resultCode == RESULT_OK && requestCode == request_code){
//            imageView.setImageURI(data.getData());
//            bytes = imageToByte(imageView);
//
//            // para que se vea la imagen en circulo
//            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
//            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//            roundedBitmapDrawable.setCircular(true);
//            imageView.setImageDrawable(roundedBitmapDrawable);
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}


