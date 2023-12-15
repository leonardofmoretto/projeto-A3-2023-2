package com.cesarino.buscajogos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText etNomeJogo;
    private Button btnBuscar;
    private ScrollView svScrollView;
    private LinearLayout lnLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNomeJogo = (EditText) findViewById(R.id.et_nomeJogo);
        btnBuscar = (Button) findViewById(R.id.btn_buscar);
        svScrollView = (ScrollView) findViewById(R.id.sv_scrollView);
        lnLinearLayout = (LinearLayout) findViewById(R.id.ln_linearLayout);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscarJogo();
            }
        });
    }

    private void BuscarJogo(){
        String url = "http://192.168.100.34:3000/games/gameName?name=" + etNomeJogo.getText();
        RequestQueue queue = Volley.newRequestQueue(this);
        lnLinearLayout.removeAllViews();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Assuming your JSON structure is an array directly, not nested in an object
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject app = response.getJSONObject(i);
                                int appid = app.getInt("appid");
                                String appName = app.getString("name");

                                // Create a new TextView for each app
                                TextView textView = new TextView(MainActivity.this);
                                textView.setTextSize(21);
                                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                                textView.setText(appName);

                                // Add the TextView to the LinearLayout
                                lnLinearLayout.addView(textView);
                            }
                        } catch (JSONException e) {
                            Snackbar.make(MainActivity.this.getCurrentFocus(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(MainActivity.this.getCurrentFocus(), "Deu xabu: " + error.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonArrayRequest);
    }


}