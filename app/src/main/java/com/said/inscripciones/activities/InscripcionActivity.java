package com.said.inscripciones.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.said.inscripciones.R;
import com.said.inscripciones.adapters.InscripcionAdapter;
import com.said.inscripciones.api.InscripcionApiClient;
import com.said.inscripciones.models.InscripcionResponse;

import java.util.List;

public class InscripcionActivity extends AppCompatActivity {

    RecyclerView rvInscripciones;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscripcion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvInscripciones = findViewById(R.id.rvInscripciones);
        rvInscripciones.setLayoutManager(new LinearLayoutManager(this));
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> cargarInscripciones()
        );

        cargarInscripciones();
    }

    public void cmdCrear_onClick(View view) {
        Intent intent = new Intent(InscripcionActivity.this, InscripcionFormActivity.class);
        launcher.launch(intent);
    }

    private void cargarInscripciones() {
        try {
            List<InscripcionResponse> inscripciones = InscripcionApiClient.getInscripciones();
            if (inscripciones != null) {
                InscripcionAdapter adapter = new InscripcionAdapter(inscripciones, new InscripcionAdapter.OnItemClickListener() {
                    @Override
                    public void onEditar(int idInscripcion) {
                        Intent intent = new Intent(InscripcionActivity.this, InscripcionFormActivity.class);
                        intent.putExtra("idInscripcion", idInscripcion);
                        launcher.launch(intent);
                    }

                    @Override
                    public void onEliminar(int idInscripcion) {
                        try {
                            boolean eliminado = InscripcionApiClient.deleteInscripcion(idInscripcion);
                            if (eliminado) {
                                cargarInscripciones();
                                Toast.makeText(InscripcionActivity.this, "Inscripción eliminada", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InscripcionActivity.this, "No se pudo eliminar la inscripción", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(InscripcionActivity.this, "Error al intentar eliminar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                rvInscripciones.setAdapter(adapter);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar inscripciones: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void cmdRegresar_onClick(View view) {
        finish();
    }
}