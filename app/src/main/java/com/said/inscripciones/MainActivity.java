package com.said.inscripciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.said.inscripciones.activities.CursoActivity;
import com.said.inscripciones.activities.EstudianteActivity;
import com.said.inscripciones.activities.InscripcionActivity;

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
    }

    public void cmdEstudiantes_onClick(View view) {
        Intent intent = new Intent(this, EstudianteActivity.class);
        startActivity(intent);
    }

    public void cmdCursos_onClick(View view) {
        Intent intent = new Intent(this, CursoActivity.class);
        startActivity(intent);
    }

    public void cmdInscripciones_onClick(View view) {
        Intent intent = new Intent(this, InscripcionActivity.class);
        startActivity(intent);
    }
}