package com.mikepenz.materialdrawer.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PageFormC1 extends AppCompatActivity {
    private static final int PROFILE_SETTING = 100000;

    private AccountHeader headerResult = null;
    private Drawer result = null;

    private ListView lvData;
    private ArrayList<ModelDokumentasi> data;
    String idWilayah, status_ops;
    private GaleriAdapter adapter;
    Context context = this;
    TextView txtPoint;
    TextView txtUsername;
    String idDok;
    String nPoint;
    int newPoint;
    GridView gridView;
    AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sample_actionbar);
        setContentView(R.layout.page_formc1);
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = new ArrayList<>();
        //lvData = (ListView) findViewById(R.id.listGaleri);
        gridView = (GridView)findViewById(R.id.gridView);

        aq = new AQuery(getApplicationContext());
        if (!PolingHelper.isOnline(getApplicationContext())) {
            startActivity(new Intent(getApplicationContext(), No_Internet.class));
            finish();

        } else {

            getDataGaleri();

        }





        //untuk membuat adapter list kota

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
                                intent = new Intent(PageFormC1.this, FormPersetujuan.class);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(PageFormC1.this, PageFormC1.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(PageFormC1.this, FormPersetujuan_Situasi.class);
                            } else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(PageFormC1.this, PageSituasi.class);
                            } else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(PageFormC1.this, Maps.class);
                            } else if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(PageFormC1.this, Panduan1.class);
                            } else if (drawerItem.getIdentifier() == 7) {
                                intent = new Intent(PageFormC1.this, Panduan2.class);
                            } else if (drawerItem.getIdentifier() == 8) {
                                intent = new Intent(PageFormC1.this, Panduan3.class);
                            } else if (drawerItem.getIdentifier() == 9) {
                                intent = new Intent(PageFormC1.this, Panduan4.class);
                            } else if (drawerItem.getIdentifier() == 20) {
                                intent = new LibsBuilder()
                                        .withFields(R.string.class.getFields())
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                        .intent(PageFormC1.this);
                            }
                            if (intent != null) {
                                PageFormC1.this.startActivity(intent);
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
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

    private void getDataGaleri() {
        data.clear();
        //ambil data dari server
        String url = PolingHelper.BASE_URL + "get_galeri";
        Map<String, String> parampa = new HashMap<>();


        //menambahkan progres dialog loading
        ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setMessage("Loading..");
        try {
            //mencari url dan parameter yang dikirimkan
            PolingHelper.pre("Url : " + url + ", params " + parampa.toString());
            //koneksi ke server meggunakan aquery
            aq.progress(progressDialog).ajax(url, parampa, String.class,
                    new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String hasil, AjaxStatus status) {
                            //cek apakah hasilnya null atau tidak
                            if (hasil != null) {
                                PolingHelper.pre("Respon : " + hasil);
                                //merubah string menjadi json
                                try {
                                    JSONObject json = new JSONObject(hasil);
                                    String result = json.getString("result");
                                    String pesan = json.getString("msg");
                                    // NurHelper.pesan(getActivity(), pesan);
                                    if (result.equalsIgnoreCase("true")) {
                                        JSONArray jsonArray = json.getJSONArray("data");
                                        for (int a = 0; a < jsonArray.length(); a++) {
                                            JSONObject object = jsonArray.getJSONObject(a);
                                            //ambil data perbooking dan masukkan ke kelas object model
                                            ModelDokumentasi b = new ModelDokumentasi();
                                            b.setUsername(object.getString("username"));
                                            b.setNama_dok(object.getString("nama_dok"));
                                            b.setFoto_dok(object.getString("foto_dok"));
                                            b.setPoint_dok(object.getString("point_dok"));
                                            b.setId_dok(object.getString("id_dok"));



                                            //memasukkan data kedalam model booking
                                            data.add(b);
                                            //masukkan data arraylist kedalam custom adapter
                                            adapter = new GaleriAdapter(PageFormC1.this, data);
                                            gridView.setAdapter(adapter);

                                        }
                                    } else {
                                        //  NurHelper.pesan(getActivity(), pesan);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    // NurHelper.pesan(getActivity(), "Error parsing data");
                                }
                            }
                        }
                    });

        } catch (Exception e) {
            //  NurHelper.pesan(getActivity(), "Error get data");
            e.printStackTrace();
        }
    }

    private class GaleriAdapter extends BaseAdapter {
        private Activity c;
        private ArrayList<ModelDokumentasi> datas;
        private LayoutInflater inflater = null;

        public GaleriAdapter(Activity c, ArrayList<ModelDokumentasi> data) {
            this.c = c;
            datas = new ArrayList<>();
            this.datas = data;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            TextView judul, point, username;
            ImageView imgGambar, imgPoint;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder holder = null;
            if (v == null) {
                inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.item_galeri, null);
                holder = new ViewHolder();

                holder.username = (TextView) v.findViewById(R.id.txtUsername);
                //holder.judul = (TextView) v.findViewById(R.id.txtJudul);
                holder.point = (TextView)v.findViewById(R.id.TeksPoint);
                //holder.imgPoint = (ImageView) v.findViewById(R.id.imgLove);
                holder.imgGambar = (ImageView) v.findViewById(R.id.imgGambar);

                v.setTag(holder);

            } else {
                holder = (ViewHolder) v.getTag();
            }
            //masukkan data booking
            final ModelDokumentasi b = datas.get(position);

            holder.username.setText( "Nama Pengguna :"+ b.getUsername());
            holder.point.setText("Point : " + b.getPoint_dok());
            idDok = b.getId_dok();
            nPoint = b.getPoint_dok();
            int a = Integer.parseInt(nPoint);
            newPoint = a + 10;
            holder.imgGambar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newI = new Intent(getApplicationContext(), DetailGaleri.class);
                    newI.putExtra("id_dok", idDok);
                    newI.putExtra("nama_dok", b.getNama_dok());
                    newI.putExtra("point", nPoint);
                    newI.putExtra("gambar_dok", b.getFoto_dok());

                    startActivity(newI);
                }
            });




            Picasso.with(context).load(PolingHelper.BASE_URL_IMAGE+b.getFoto_dok()).placeholder(R.drawable.no_image).
                    into(holder.imgGambar);
//            holder.imgPoint.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    updateLove();
//
//
//                }
//            });

//
            return v;
        }
    }

//    private void updateLove() {
//
//        final boolean cancel = false;
//        View focusView = null;
//
//
//        if (cancel) {
//            focusView.requestFocus();
//        } else {
//            //check jam inputnya
//
//            String url = PolingHelper.BASE_URL + "update_love_dok";
//
//            Map<String, Object> params = new HashMap<String, Object>();
//
//            params.put("id_dok", idDok);
//            params.put("point_dok", newPoint);
//
//            Toast.makeText(getApplicationContext(), "ID DOK : " + idDok + "Point : " + newPoint, Toast.LENGTH_SHORT).show();
//
//            //coba d  run mba , kebaca gak id dok sama point ini
//            //ke
//            ProgressDialog dialog = new ProgressDialog(context);
//            dialog.setIndeterminate(true);
//            dialog.setCancelable(false);
//            dialog.setInverseBackgroundForced(false);
//            dialog.setCanceledOnTouchOutside(true);
//            dialog.setMessage("Loading...");
//            try {
//                PolingHelper.log("url : " + url + ", param :" + params.toString());
//                aq.progress(dialog).ajax(url, params, String.class,
//                        new AjaxCallback<String>() {
//
//                            @Override
//                            public void callback(String url, String jsonx,
//                                                 AjaxStatus status) {
//
//                                try {
//
//                                    PolingHelper.log("log aquery respon "
//                                            + jsonx);
//                                    JSONObject json = new JSONObject(jsonx);
//                                    String result = json.getString("result");
//                                    String pesan = json.getString("msg");
//
//                                    if (result.equalsIgnoreCase("true")) {
//                                        PolingHelper.pesan(context, pesan);
//                                        Intent a = new Intent(getApplicationContext(), DrawerActivity.class);
//                                        startActivity(a);
//                                        onBackPressed();
//                                    } else {
//                                        PolingHelper.pesan(context, pesan);
//
//                                    }
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//
//
//                                    PolingHelper.pesan(context,
//                                            "Error parsing data, please try again.");
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    PolingHelper.pesan(context,
//                                            "Error submit upload photo , silahkan coba lagi.");
//                                }
//
//                            }
//                        });
//            } catch (Exception e) {
//                e.printStackTrace();
//                PolingHelper.pesan(context,
//                        "Error get task, please try again.");
//            }
//
//
//        }
//
//    }

}
