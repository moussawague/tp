package com.example.projet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projet.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void func(View v){
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText password = findViewById(R.id.editTextNumberPassword);

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        Toast t1 = Toast.makeText(v.getContext(), "Connexion réussie", Toast.LENGTH_SHORT);
        Toast t2 = Toast.makeText(v.getContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT);
        if(emailText.equals("moussa") && passwordText.equals("moussa")){
            t1.show();
            startActivity(intent);
        }
        else{
            t2.show();
        }
    }
}