package com.example.projet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projet.R;
import com.example.projet.entities.Habitat;
import com.example.projet.entities.Resident;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ArrayList<Resident> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        accounts= new ArrayList<Resident>();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        loadAccount();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void goToRegister(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void func(View v){

        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText password = findViewById(R.id.editTextNumberPassword);

        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        Resident user = verifyAccount(accounts, emailText, passwordText);

        if(user != null){
            Toast.makeText(v.getContext(), "Connexion réussie", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("user_id", user.getId());
            startActivity(intent);
        } else {
            Toast.makeText(v.getContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadAccount() {

        Ion.with(this)
                .load("http://192.168.1.118/powerhome_server/resident.php")
                .as(new TypeToken<ArrayList<Resident>>() {})
                .setCallback((e, result) -> {

                    if (e != null) {
                        Log.e("ION_USERS", "Erreur users", e);
                        return;
                    }

                    accounts = result;
                    Log.d("ION_USERS", "Users  chargés : " + accounts.size());
                });
    }

    private Resident verifyAccount(List<Resident> accounts, String email, String password){
        for (Resident account : accounts){
            if(email.equals(account.getEmail()) && password.equals(account.getPassword())){
                return account;
            }
        }
        return null;
    }
}