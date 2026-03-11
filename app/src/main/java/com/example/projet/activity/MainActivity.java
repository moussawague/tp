package com.example.projet.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projet.R;
import com.example.projet.entities.Habitat;
import com.example.projet.entities.Resident;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvName = findViewById(R.id.tvName);

        String url = "http://10.0.2.2/powerhome_server/getUser.php?id=1";

        Ion.with(this)
                .load(url)
                .asString()
                .setCallback((e, result) -> {

                    if (e == null && result != null) {

                        Gson gson = new Gson();
                        Resident resident = gson.fromJson(result, Resident.class);

                        tvName.setText(resident.getFirstname() + " " + resident.getLastname());
                    }
                });
    }

    ProgressDialog pDialog;
    public void getRemoteHabitats() {

        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting list of habitats...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        String urlString = "http://10.0.2.2/powerhome_server/getHabitats.php";

        Ion.with(this)
                .load(urlString)
                .asString()
                .setCallback((e, result) -> {

                    pDialog.dismiss();

                    if (e != null) {
                        Log.e("HTTP_ERROR", e.getMessage());
                        Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (result == null) {
                        Log.e("SERVER_ERROR", "No response from server");
                        Toast.makeText(this, "No response from server", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Habitat>>() {}.getType();
                        List<Habitat> habitatList = gson.fromJson(result, type);

                        Log.d("PARSED_SIZE", "Nb habitats: " + habitatList.size());

                    } catch (Exception ex) {
                        Log.e("JSON_ERROR", ex.getMessage());
                    }
                });
    }


}