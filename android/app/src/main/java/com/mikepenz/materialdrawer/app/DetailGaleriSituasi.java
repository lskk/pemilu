package com.mikepenz.materialdrawer.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailGaleriSituasi extends AppCompatActivity {

    TextView txtKabupaten, txtKecamatan, txtKelurahan ,txtKeterangan, txtPoint;
    ImageView imgGambar, imgLike, imgUnLike;
    String idDok, pointDok;
    int newPoint, minusPoint, plusPoint;

    Context context = this;
    AQuery aq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_galeri_situasi);
        aq = new AQuery(getApplicationContext());
        Intent a1 = getIntent();
        idDok = a1.getStringExtra("id_dok");

        pointDok = a1.getStringExtra("point");
        String gambarDok = a1.getStringExtra("gambar_dok");
        int a = Integer.parseInt(pointDok);
        newPoint = a + 5;

        String keterangan = a1.getStringExtra("keterangan");
        String namaKab = a1.getStringExtra("kabupaten");
        String namaKec = a1.getStringExtra("kecamatan");
        String namaKel = a1.getStringExtra("kelurahan");

        txtKabupaten = (TextView)findViewById(R.id.dtlKabupaten);
        txtKecamatan = (TextView)findViewById(R.id.dtlKecamatan);
        txtKelurahan = (TextView)findViewById(R.id.dtlKelurahan);

        txtKeterangan = (TextView)findViewById(R.id.dtlKeterangan);
        imgGambar = (ImageView) findViewById(R.id.dtlGambar);
        imgLike = (ImageView) findViewById(R.id.dtlLike);
        imgUnLike = (ImageView) findViewById(R.id.dtlUnlike);

        txtKabupaten.setText("Kabupaten : " + namaKab);
        txtKecamatan.setText("Kecamatan : " + namaKec);
        txtKelurahan.setText("Kelurahan : " + namaKel);
        txtKeterangan.setText("Keterangan : " + keterangan);

        Picasso.with(getApplicationContext()).load(PolingHelper.BASE_URL_IMAGE_situasi+ gambarDok).placeholder(R.drawable.no_image).
                into(imgGambar);

        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateLove();

            }
        });

        imgUnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUnLove();
            }
        });
    }

    private void updateUnLove() {

        final boolean cancel = false;
        View focusView = null;


        if (cancel) {
            focusView.requestFocus();
        } else {
            //check jam inputnya

            int a = Integer.parseInt(pointDok);
            minusPoint = a - 5;

            String url = PolingHelper.BASE_URL + "update_unlove_dok_situasi";

            Map<String, Object> params = new HashMap<String, Object>();

            params.put("id_dok", idDok);
            params.put("point_dok", minusPoint);


            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setInverseBackgroundForced(false);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setMessage("Loading...");
            try {
                PolingHelper.log("url : " + url + ", param :" + params.toString());
                aq.progress(dialog).ajax(url, params, String.class,
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
                                        PolingHelper.pesan(getApplicationContext(), pesan);
                                        Intent a = new Intent(getApplicationContext(), PageSituasi.class);
                                        startActivity(a);
                                        onBackPressed();
                                    } else {
                                        PolingHelper.pesan(getApplicationContext(), pesan);

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();


                                    PolingHelper.pesan(getApplicationContext(),
                                            "Error parsing data, please try again.");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    PolingHelper.pesan(getApplicationContext(),
                                            "Error submit upload photo , silahkan coba lagi.");
                                }

                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
                PolingHelper.pesan(getApplication(),
                        "Error get task, please try again.");
            }


        }

    }

    private void updateLove() {

        final boolean cancel = false;
        View focusView = null;


        if (cancel) {
            focusView.requestFocus();
        } else {

//            int a = Integer.parseInt(pointDok);
//            plusPoint = a + 5;

            String url = PolingHelper.BASE_URL + "update_love_dok_situasi";

            Map<String, Object> params = new HashMap<String, Object>();

            params.put("id_dok", idDok);
            params.put("point_dok", newPoint);


            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setInverseBackgroundForced(false);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setMessage("Loading...");
            try {
                PolingHelper.log("url : " + url + ", param :" + params.toString());
                aq.progress(dialog).ajax(url, params, String.class,
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
                                        PolingHelper.pesan(getApplicationContext(), pesan);
                                        Intent a = new Intent(getApplicationContext(), PageSituasi.class);
                                        startActivity(a);
                                        onBackPressed();
                                    } else {
                                        PolingHelper.pesan(getApplicationContext(), pesan);

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();


                                    PolingHelper.pesan(getApplicationContext(),
                                            "Error parsing data, please try again.");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    PolingHelper.pesan(getApplicationContext(),
                                            "Error submit upload photo , silahkan coba lagi.");
                                }

                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
                PolingHelper.pesan(getApplication(),
                        "Error get task, please try again.");
            }


        }

    }
}
