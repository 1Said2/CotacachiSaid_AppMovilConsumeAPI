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
import com.said.inscripciones.api.CursoApiClient;
import com.said.inscripciones.models.Curso;

public class CursoActivity extends AppCompatActivity {

    EditText txtId, txtNombre, txtPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_curso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtId = findViewById(R.id.txtId);
        txtNombre = findViewById(R.id.txtNombre);
        txtPrecio = findViewById(R.id.txtPrecio);
    }

    public void cmdCrear_onClick(View view) {
        Curso curso = new Curso();
        curso.id_curso = Integer.parseInt(txtId.getText().toString());
        curso.nombre = txtNombre.getText().toString();
        curso.precio = Double.parseDouble(txtPrecio.getText().toString());
        try {
            Curso nuevo = CursoApiClient.createCurso(curso);
            if (nuevo != null) {
                Toast.makeText(this, "Curso creado en la API con el id: " + nuevo.id_curso, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "No se pudo crear el curso en la API", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void cmdLeer_onClick(View view) {
        try {
            Curso curso = CursoApiClient.getCurso(Integer.parseInt(txtId.getText().toString()));
            if (curso != null) {
                txtNombre.setText(curso.nombre);
                txtPrecio.setText(String.valueOf(curso.precio));
            } else {
                Toast.makeText(this, "Curso no encontrado en la API", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void cmdEditar_onClick(View view) {
        Curso curso = new Curso();
        curso.id_curso = Integer.parseInt(txtId.getText().toString());
        curso.nombre = txtNombre.getText().toString();
        curso.precio = Double.parseDouble(txtPrecio.getText().toString());
        try {
            Curso actualizado = CursoApiClient.updateCurso(curso);
            if (actualizado != null) {
                Toast.makeText(this, "Curso actualizado en la API con el id: " + actualizado.id_curso, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "No se pudo actualizar el curso en la API", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

     public void cmdEliminar_onClick(View view) {
        try {
            boolean eliminado = CursoApiClient.deleteCurso(Integer.parseInt(txtId.getText().toString()));
            if (eliminado) {
                Toast.makeText(this, "Curso eliminado de la API", Toast.LENGTH_SHORT).show();
                txtId.setText("");
                txtNombre.setText("");
                txtPrecio.setText("");
            } else {
                Toast.makeText(this, "No se pudo eliminar el curso de la API", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void cmdRegresar_onClick(View view) {
        finish();
    }
}