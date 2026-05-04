package com.said.inscripciones.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.said.inscripciones.R;
import com.said.inscripciones.api.EstudianteApiClient;
import com.said.inscripciones.models.Estudiante;

public class EstudianteActivity extends AppCompatActivity {

    EditText txtCedula, txtApellidos, txtNombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estudiante);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtCedula = findViewById(R.id.txtId);
        txtApellidos = findViewById(R.id.txtNombre);
        txtNombres = findViewById(R.id.txtPrecio);
    }

    public void cmdCrear_onClick(View view) {
        Estudiante estudiante = new Estudiante();
        estudiante.cedula = txtCedula.getText().toString();
        estudiante.apellidos = txtApellidos.getText().toString();
        estudiante.nombres = txtNombres.getText().toString();
        try {
            Estudiante estudiante1 = EstudianteApiClient.createEstudiante(estudiante);
            if (estudiante1 != null) {
                Toast.makeText(this, "ESTUDIANTE CREADO CON CEDULA: " + estudiante1.cedula, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "NO SE PUDO CREAR EL ESTUDIANTE", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "ERROR AL CREAR ESTUDIANTE !!", Toast.LENGTH_LONG).show();
        }
    }

    public void cmdLeer_onClick(View view) {
        try {
            Estudiante estudiante = EstudianteApiClient.getEstudiante(txtCedula.getText().toString());
            if (estudiante != null) {
                txtApellidos.setText(estudiante.apellidos);
                txtNombres.setText(estudiante.nombres);
            } else {
                Toast.makeText(this, "ESTUDIANTE NO ENCONTRADO", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "ERROR AL LEER ESTUDIANTE !!", Toast.LENGTH_LONG).show();
        }
    }

    public void cmdEditar_onClick(View view) {
        Estudiante estudiante = new Estudiante();
        estudiante.cedula = txtCedula.getText().toString();
        estudiante.apellidos = txtApellidos.getText().toString();
        estudiante.nombres = txtNombres.getText().toString();
        try {
            Estudiante estudiante1 = EstudianteApiClient.updateEstudiante(estudiante);
            if (estudiante1 != null) {
                Toast.makeText(this, "ESTUDIANTE ACTUALIZADO CON CEDULA: " + estudiante1.cedula, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "NO SE PUDO ACTUALIZAR EL ESTUDIANTE", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "ERROR AL ACTUALIZAR ESTUDIANTE !!", Toast.LENGTH_LONG).show();
        }
    }

    public void cmdEliminar_onClick(View view) {
        try {
            boolean eliminado = EstudianteApiClient.deleteEstudiante(txtCedula.getText().toString());
            if (eliminado) {
                Toast.makeText(this, "ESTUDIANTE ELIMINADO CON CEDULA: " + txtCedula.getText().toString(), Toast.LENGTH_SHORT).show();
                txtCedula.setText("");
                txtApellidos.setText("");
                txtNombres.setText("");
            } else {
                Toast.makeText(this, "NO SE PUDO ELIMINAR EL ESTUDIANTE", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "ERROR AL ELIMINAR ESTUDIANTE !!", Toast.LENGTH_LONG).show();
        }
    }

    public void cmdRegresar_onClick(View view) {
        finish();
    }
}