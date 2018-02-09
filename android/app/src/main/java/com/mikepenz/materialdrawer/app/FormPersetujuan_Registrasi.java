package com.mikepenz.materialdrawer.app;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;

/**
 * Created by Maycol Meza on 15/04/2017.
 */

public class FormPersetujuan_Registrasi extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_persetujuan_registrasi);


        lanjutkan = (Button) findViewById(R.id.btn_input);

        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Silahkan isi data registrasi", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
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
                                new SecondaryDrawerItem().withName("Input Data Polling").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(1),
                                new SecondaryDrawerItem().withName("Gallery Data Polling").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(2)
                        ),
                        new ExpandableDrawerItem().withName("Aktivitas Pemilu").withIcon(FontAwesome.Icon.faw_bar_chart).withIdentifier(19).withSelectable(false).withSubItems(
                                new SecondaryDrawerItem().withName("Input Situasi Pemilu").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(3),
                                new SecondaryDrawerItem().withName("Gallery Situasi Pemilu").withLevel(2).withIcon(GoogleMaterial.Icon.gmd_8tracks).withIdentifier(4)
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
                                intent = new Intent(FormPersetujuan_Registrasi.this, FormPersetujuan_Registrasi.class);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(FormPersetujuan_Registrasi.this, PageFormC1.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(FormPersetujuan_Registrasi.this, FormPersetujuan_Situasi.class);
                            } else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(FormPersetujuan_Registrasi.this, PageSituasi.class);
                            } else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(FormPersetujuan_Registrasi.this, Maps.class);
                            } else if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(FormPersetujuan_Registrasi.this, Panduan1.class);
                            } else if (drawerItem.getIdentifier() == 7) {
                                intent = new Intent(FormPersetujuan_Registrasi.this, Panduan2.class);
                            } else if (drawerItem.getIdentifier() == 8) {
                                intent = new Intent(FormPersetujuan_Registrasi.this, Panduan3.class);
                            } else if (drawerItem.getIdentifier() == 9) {
                                intent = new Intent(FormPersetujuan_Registrasi.this, Panduan4.class);
                            } else if (drawerItem.getIdentifier() == 20) {
                                intent = new LibsBuilder()
                                        .withFields(R.string.class.getFields())
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                        .intent(FormPersetujuan_Registrasi.this);
                            }
                            if (intent != null) {
                                gps.stopUsingGPS();
                                gps = null;
                                FormPersetujuan_Registrasi.this.startActivity(intent);
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


