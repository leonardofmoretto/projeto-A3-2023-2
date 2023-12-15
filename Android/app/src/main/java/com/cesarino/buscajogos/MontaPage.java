package com.cesarino.buscajogos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class MontaPage extends AppCompatActivity {

    private ImageView ivImagem;
    private TextView tvNomeJogo, tvDescricao;
    private Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monta_page);

        tvNomeJogo = findViewById(R.id.tv_nomeJogo_monta_page);
        tvDescricao = findViewById(R.id.tv_descricao_monta_page);
        ivImagem = findViewById(R.id.iv_imagem_monta_page);

        intentMain = getIntent();
        if (intentMain.hasExtra("appid")) {
            int appid = intentMain.getIntExtra("appid", 0);
            fetchGameDetails(appid);
        }
    }

    private void fetchGameDetails(int appid) {
        String url = "http://192.168.100.34:3000/games/gameId?id=" + appid;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                        JSONObject gameData = response.getJSONObject(String.valueOf(appid));
                                        if (gameData.has("data")) {
                                            JSONObject dataObject = gameData.getJSONObject("data");


                                            String appName = dataObject.getString("name");
                                            String capsuleImage = dataObject.getString("capsule_image");
                                            String detailedDescription = dataObject.getString("detailed_description");

                                            tvNomeJogo.setText(appName);

                                            Glide.with(MontaPage.this).load(capsuleImage).into(ivImagem);

                                            CharSequence htmlCode = HtmlCompat.fromHtml(detailedDescription, HtmlCompat.FROM_HTML_MODE_LEGACY);
                                            tvDescricao.setText(htmlCode);



                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Snackbar.make(MontaPage.this.getCurrentFocus(), "Deu ruim " + error, Snackbar.LENGTH_LONG).show();
                            }
                        });

        queue.add(jsonRequest);
    }
}
