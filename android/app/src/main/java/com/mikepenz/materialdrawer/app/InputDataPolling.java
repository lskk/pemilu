package com.mikepenz.materialdrawer.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.afollestad.materialdialogs.MaterialDialog;


public class InputDataPolling extends AppCompatActivity {
    private static final int PROFILE_SETTING = 100000;
    private static final int CAMERA_REQUEST = 1;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private Button ambil_gambar;

    private Spinner kabupaten;
    private Spinner kecamatan;
    private Spinner kelurahan;
    private Spinner notps;
    private EditText polling1;
    private EditText polling2;
    private EditText polling3;
    private ImageView imgUpload;
    private int request_code = 1;
    private Bitmap bitmap_foto;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private byte[] bytes;
    private List<String> item = new ArrayList<>();
    private List<String> item2 = new ArrayList<>();
    private List<String> item3 = new ArrayList<>();
    private List<String> item4 = new ArrayList<>();
    private Spinner spKabupaten;
    private List<String> listIdKabupaten = new ArrayList<>();
    private String idKabupaten;
    private Spinner spKecamatan;
    private List<String> listIdKecamatan = new ArrayList<>();
    private String idKecamatan;
    private Spinner spKelurahan;
    private List<String> listIdKelurahan = new ArrayList<>();
    private String idKelurahan;
    private Spinner spNoTps;
    private List<String> listNoTps = new ArrayList<>();
    private String idNoTps;
    private Button submit;
    private TextView username;



    private GPSTracker gps;

    private Double latitude;
    private Double longitude;

    private String path = "";
    private boolean isImage = false;
    private String namaFile;
    private Bitmap bitmap = null;
    private int fromWO = 1;
    protected AQuery aQuery;
    private  int takeTo = 1;

    Context c = this;
    public static final int TAKE_PHOTO = 354;
    public static final int SELECT_PHOTO = 1;
    String picturePath;

    FileUtility fileUtility;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sample_actionbar);
        setContentView(R.layout.input_datapolling);
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        TextView text = (TextView) findViewById(R.id.txtLabel);
        text.setText(pref.getString("username", "username"));

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Silahkan tunggu");
        pDialog.setCancelable(false);
        pDialog.show();


        spKabupaten = (Spinner) findViewById(R.id.kabupaten);
        spKecamatan = (Spinner) findViewById(R.id.kecamatan);
        spKelurahan = (Spinner) findViewById(R.id.kelurahan);
        spNoTps = (Spinner) findViewById(R.id.notps);

        imgUpload = (ImageView)findViewById(R.id.upload);

        bitmap_foto = BitmapFactory.decodeResource(getResources(), R.drawable.logoprofile);
        roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap_foto);
        roundedBitmapDrawable.setCircular(true);
        imgUpload.setImageDrawable(roundedBitmapDrawable);

        gps = new GPSTracker(InputDataPolling.this);

        username = (TextView) findViewById(R.id.txtLabel);
        submit = (Button) findViewById(R.id.buttonsubmit);
        polling1 = (EditText) findViewById(R.id.polling1);
        polling2 = (EditText) findViewById(R.id.polling2);
        polling3 = (EditText) findViewById(R.id.polling3);

        aQuery = new AQuery(c);


        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userSubmit(view);
                simpanAction();

            }
        });


// ambil data kabupaten
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_GET_KABUPATEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();
                        try {
                            JSONArray obj = new JSONArray(response);
                            Integer num;
                            JSONObject objKabupaten;
                            for(num = 0; num < obj.length(); num++) {
                                objKabupaten = obj.getJSONObject(num);
                                listIdKabupaten.add(objKabupaten.getString("wilayah_id"));
                                item.add(objKabupaten.getString("nama"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(InputDataPolling.this, android.R.layout.simple_spinner_dropdown_item, item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spKabupaten.setAdapter(new NothingSelectedKabupaten(adapter, R.layout.contact_spinner_row_nothing_kab, getApplicationContext()));
                            pDialog.hide();
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
                                error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        ){
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

// ambil kecamatan
        stringRequest = new StringRequest(Request.Method.POST, Constant.URL_GET_KECAMATAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //              progressDialog.dismiss();
                        try {
                            JSONArray obj = new JSONArray(response);
                            Integer num;
                            JSONObject objKecamatan;
                            for (num = 0; num < obj.length(); num++) {
                                objKecamatan = obj.getJSONObject(num);
                                listIdKecamatan.add(objKecamatan.getString("wilayah_id"));
                                item2.add(objKecamatan.getString("nama"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(InputDataPolling.this, android.R.layout.simple_spinner_dropdown_item, item2);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spKecamatan.setAdapter(new NothingSelectedKecamatan(adapter, R.layout.contact_spinner_row_nothing_kec, getApplicationContext()));
                            pDialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        ) {

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("id",listIdKabupaten.get(spKabupaten.getSelectedItemPosition() - 1));
//                return params;
//            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


// ambil data kelurahan
        stringRequest = new StringRequest(Request.Method.POST, Constant.URL_GET_KELURAHAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //              progressDialog.dismiss();
                        try {
                            JSONArray obj = new JSONArray(response);
                            Integer num;
                            JSONObject objKelurahan;
                            for (num = 0; num < obj.length(); num++) {
                                objKelurahan = obj.getJSONObject(num);
                                listIdKelurahan.add(objKelurahan.getString("wilayah_id"));
                                item3.add(objKelurahan.getString("nama"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(InputDataPolling.this, android.R.layout.simple_spinner_dropdown_item, item3);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spKelurahan.setAdapter(new NothingSelectedKabupaten(adapter, R.layout.contact_spinner_row_nothing_kel, getApplicationContext()));
                            pDialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        ) {
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


        // ambil data TPS
        stringRequest = new StringRequest(Request.Method.POST, Constant.URL_GET_NOTPS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //              progressDialog.dismiss();
                        try {
                            JSONArray obj = new JSONArray(response);
                            Integer num;
                            JSONObject objNoTps;
                            for (num = 0; num < obj.length(); num++) {
                                objNoTps = obj.getJSONObject(num);
                                listNoTps.add(objNoTps.getString("tps_id"));
                                item4.add(objNoTps.getString("nama"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(InputDataPolling.this, android.R.layout.simple_spinner_dropdown_item, item4);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spNoTps.setAdapter(new NothingSelectedKabupaten(adapter, R.layout.contact_spinner_row_nothing_notps, getApplicationContext()));
                            pDialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        ) {
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


        //List jenis kab
        kabupaten = (Spinner) findViewById(R.id.kabupaten);
        List<String> item = new ArrayList<String>();
        item.add("Kabupaten");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(InputDataPolling.this,android.R.layout.simple_spinner_dropdown_item,
                item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kabupaten.setAdapter(adapter2);

        //List jenis kec
        kecamatan = (Spinner) findViewById(R.id.kecamatan);
        List<String> item2 = new ArrayList<String>();
        item2.add("Kecamatan");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(InputDataPolling.this,android.R.layout.simple_spinner_dropdown_item,
                item2);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kecamatan.setAdapter(adapter3);

        //List jenis kel
        kelurahan = (Spinner) findViewById(R.id.kelurahan);
        List<String> item3 = new ArrayList<String>();
        item3.add("Kelurahan");
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(InputDataPolling.this,android.R.layout.simple_spinner_dropdown_item,
                item3);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kelurahan.setAdapter(adapter4);

        //List jenis tps
        notps = (Spinner) findViewById(R.id.notps);
        List<String> item4 = new ArrayList<String>();
        item4.add("No Tps");
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(InputDataPolling.this,android.R.layout.simple_spinner_dropdown_item,
                item4);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notps.setAdapter(adapter5);




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
                                intent = new Intent(InputDataPolling.this, FormPersetujuan.class);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(InputDataPolling.this, PageFormC1.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(InputDataPolling.this, FormPersetujuan_Situasi.class);
                            } else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(InputDataPolling.this, PageSituasi.class);
                            } else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(InputDataPolling.this, Maps.class);
                            } else if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(InputDataPolling.this, Panduan1.class);
                            } else if (drawerItem.getIdentifier() == 7) {
                                intent = new Intent(InputDataPolling.this, Panduan2.class);
                            } else if (drawerItem.getIdentifier() == 8) {
                                intent = new Intent(InputDataPolling.this, Panduan3.class);
                            } else if (drawerItem.getIdentifier() == 9) {
                                intent = new Intent(InputDataPolling.this, Panduan4.class);
                            } else if (drawerItem.getIdentifier() == 20) {
                                intent = new LibsBuilder()
                                        .withFields(R.string.class.getFields())
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                        .intent(InputDataPolling.this);
                            }
                            if (intent != null) {
                                gps.stopUsingGPS();
                                gps = null;
                                InputDataPolling.this.startActivity(intent);
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

//    public void callCamera() {
//
//        fileUtility = new FileUtility(c);
//        Uri uriSavedImage = Uri.fromFile(fileUtility.getTempJpgImageFile());
//
//        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
//        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        startActivityForResult(i, TAKE_PHOTO);
//    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            fileUtility = new FileUtility(c);
            Uri uriSavedImage = Uri.fromFile(fileUtility.getTempJpgImageFile());

            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            imgUpload.setImageBitmap(mphoto);
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    public void userSubmit(View view) {
        if (!validar()) return;
//        if (!validateSpinner(spKabupaten, "Pilih Kabupaten")) return;
//        if (!validateSpinner(spKecamatan, "Pilih Kecamatan")) return;
//        if (!validateSpinner(spKelurahan, "Pilih Kelurahan")) return;
//        if (!validateSpinner(spNoTps, "Pilih Nomor TPS")) return;




        if (gps.canGetLocation()) {
            submit.setText("Memproses");
            submit.setEnabled(false);

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            final TextView sUsername = (TextView) findViewById(R.id.txtLabel);
            final String sUsername_data = sUsername.getText().toString().trim();

            final String sPolling1 = polling1.getText().toString().trim();
            final String sPolling2 = polling2.getText().toString().trim();
            final String sPolling3 = polling3.getText().toString().trim();

            final Spinner spinner_kabupaten = (Spinner) findViewById(R.id.kabupaten);
            final String spinner_kabupaten_data = spinner_kabupaten.getSelectedItem().toString();

            final Spinner spinner_kecamatan = (Spinner) findViewById(R.id.kecamatan);
            final String spinner_kecamatan_data = spinner_kecamatan.getSelectedItem().toString();

            final Spinner spinner_kelurahan = (Spinner) findViewById(R.id.kelurahan);
            final String spinner_kelurahan_data = spinner_kelurahan.getSelectedItem().toString();

            final Spinner spinner_notps = (Spinner) findViewById(R.id.notps);
            final String spinner_notps_data = spinner_notps.getSelectedItem().toString();

//page form c1 sama detail galeri
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_SUBMIT_POLLING, // url api
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  progressDialos.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                //JSONObject objData = new JSONObject(obj.getString("data"));
                                if (obj.getBoolean("success")) { // berhasil
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // get session
                                    SharedPreferences.Editor editor = pref.edit(); // edit session
                                    editor.putString("id", "id");
//                                editor.putString("username", sUsername_data); // set session
//                                editor.putString("notps", sNotps);on // set session
//                                editor.putString("polling1", sPolling1); // set session
//                                editor.putString("polling2", sPolling2); // set session
//                                editor.putString("polling3", sPolling3); // set session
//                                editor.putString("kabupaten", spinner_kabupaten_data); // set session
//                                editor.putString("kecamatan", spinner_kecamatan_data); // set session
//                                editor.putString("kelurahan", spinner_kelurahan_data); // set session
                                    editor.commit(); // save sessi
                                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), PageFormC1.class));
                                    finish();
                                } else { // gagal
//                                JSONArray arrFail = new JSONArray(obj.getString("data"));
//                                JSONObject objFail = arrFail.getJSONObject(0);
                                    Toast.makeText(getApplicationContext(),
                                            obj.getString("message"),
                                            Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            submit.setText("Kirim Data Polling");
                            submit.setEnabled(true);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // progressDialog.hide();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>(); // parameter
                    params.put("username", sUsername_data);
                    params.put("polling1", sPolling1);
                    params.put("polling2", sPolling2);
                    params.put("polling3", sPolling3);
                    params.put("latitude", latitude.toString());
                    params.put("longitude", longitude.toString());
                    params.put("kabupaten", spinner_kabupaten_data);
                    params.put("kecamatan", spinner_kecamatan_data);
                    params.put("kelurahan", spinner_kelurahan_data);
                    params.put("notps", spinner_notps_data);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }


    private void simpanAction() {

        boolean cancel = false;
        View focusView = null;


        if (cancel) {
            focusView.requestFocus();
        } else {
            //check jam inputnya

            String url = PolingHelper.BASE_URL + "upload_gambar";

            Map<String, Object> params = new HashMap<String, Object>();
//
//            final Spinner spinner_kelurahan = (Spinner) findViewById(R.id.kelurahan);
//            final String spinner_kelurahan_data = spinner_kelurahan.getSelectedItem().toString();
//            params.put("kelurahan", spinner_kelurahan_data);

            final TextView sUsername = (TextView) findViewById(R.id.txtLabel);
            final String sUsername_data = sUsername.getText().toString().trim();
            params.put("username", username);

            final Spinner spinner_notps = (Spinner) findViewById(R.id.notps);
            final String spinner_notps_data = spinner_notps.getSelectedItem().toString();
            params.put("nama_dok", spinner_notps_data);

            params.put("foto_dok", path);
            if (isImage) {
                File f = new File(path);
                String ektensi = PolingHelper.getExtension(f);
                PolingHelper.pre("ektensi " + ektensi);
                namaFile = path.substring(path.lastIndexOf("/") + 1);
//                Toast.makeText(getApplicationContext(),"nama photo " + namaFile, Toast.LENGTH_SHORT).show();
                params.put("foto_dok", namaFile);
                params.put("userfile", f);
                params.put("f_isImage", "true");
            } else {
                namaFile = "";
                params.put("foto_dok", "");
                params.put("f_isImage", "false");
            }


            ProgressDialog dialog = new ProgressDialog(c);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setInverseBackgroundForced(false);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setMessage("Loading...");
            try {
                PolingHelper.log("url : " + url + ", param :" + params.toString());
                aQuery.progress(dialog).ajax(url, params, String.class,
                        new AjaxCallback<String>() {

                            @Override
                            public void callback(String url, String jsonx,
                                                 AjaxStatus status) {

                                try {

                                    PolingHelper.log("log aquery respon "
                                            + jsonx);
                                    JSONObject json = new JSONObject(jsonx);
                                    String result = json.getString("result");
                                    String pesan = json.getString("msg");

                                    if (result.equalsIgnoreCase("true")) {
                                        PolingHelper.pesan(c, pesan);
                                        Intent a = new Intent(getApplicationContext(), DrawerActivity.class);
                                        startActivity(a);
                                        onBackPressed();
                                    } else {
                                        PolingHelper.pesan(c, pesan);

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();


                                    PolingHelper.pesan(c,
                                            "Error parsing data, please try again.");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    PolingHelper.pesan(c,
                                            "Error submit upload photo , silahkan coba lagi.");
                                }

                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
                PolingHelper.pesan(c,
                        "Error get task, please try again.");
            }


        }

    }






    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case R.id.menu_1:
                /*Fragment f = DrawerFragment.newInstance("Demo");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
                return true;*/
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            case R.id.menu_2:
                /*Fragment f2 = SecondDrawerFragment.newInstance("Demo 2");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f2).commit();
                return true;*/
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private boolean validateSpinner(Spinner spinner, String error){
//        View selectedView = spinner.getSelectedView();
//        if (selectedView != null && selectedView instanceof TextView) {
//            TextView selectedTextView = (TextView) selectedView;
//            if (selectedTextView.getText().equals("")) {
//                selectedTextView.setError(error);
//                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
//                return false;
//            }
//        }
//        return true;
//    }


    private boolean validar() {
        boolean valid = true;

        String spolling1 = polling1.getText().toString().trim();
        String spolling2 = polling2.getText().toString().trim();
        String spolling3 = polling3.getText().toString().trim();

//        Spinner spinner_kabupaten = (Spinner) findViewById(R.id.kabupaten);
//        String spinner_kabupaten_data = spinner_kabupaten.getSelectedItem().toString();
//
//        if (spinner_kabupaten_data.isEmpty()) {
//            Toast.makeText(InputDataPolling.this,
//                    "Silahkan pilih kabupaten", Toast.LENGTH_LONG)
//                    .show();
//        } else {
//
//}



        if (spolling1.isEmpty() || polling1.length() < 2) {
            polling1.setError("Enter at least 2 characters");
            valid = false;
        } else {
            polling1.setError(null);
        }

        if (spolling2.isEmpty() || polling2.length() < 2) {
            polling2.setError("Enter at least 4 characters");
            valid = false;
        } else {
            polling2.setError(null);
        }

        if (spolling3.isEmpty() || polling3.length() < 2) {
            polling3.setError("Enter at least 10 characters");
            valid = false;
        } else {
            polling3.setError(null);
        }




        return valid;
    }
    //
//    private byte[] imageToByte(ImageView image) {
//        Bitmap bitmapFoto = ((BitmapDrawable)image.getDrawable()).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmapFoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//        return byteArray;
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
//            imageView.setImageURI(data.getData());
//            bytes = imageToByte(imageView);
//
//            // para que se vea la imagen en circulo
//            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
//            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//            roundedBitmapDrawable.setCircular(true);
//            imageView.setImageDrawable(roundedBitmapDrawable);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//


}