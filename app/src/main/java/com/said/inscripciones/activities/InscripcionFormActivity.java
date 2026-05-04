package com.said.inscripciones.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.said.inscripciones.R;
import com.said.inscripciones.adapters.CursoAdapter;
import com.said.inscripciones.api.CursoApiClient;
import com.said.inscripciones.api.EstudianteApiClient;
import com.said.inscripciones.api.InscripcionApiClient;
import com.said.inscripciones.models.Curso;
import com.said.inscripciones.models.Estudiante;
import com.said.inscripciones.models.InscripcionRequest;
import com.said.inscripciones.models.InscripcionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InscripcionFormActivity extends AppCompatActivity {
    TextView tvInscripcionTitle;
    RecyclerView rvCursos;
    Spinner spnEstudiantes;
    EditText txtDate;
    private int idInscripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscripcion_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rvCursos = findViewById(R.id.rvCursos);
        spnEstudiantes = findViewById(R.id.spnEstudiantes);
        txtDate = findViewById(R.id.txtDate);
        tvInscripcionTitle = findViewById(R.id.tvInscripcionTitle);
        idInscripcion = getIntent().getIntExtra("idInscripcion", -1);
        rvCursos.setLayoutManager(new LinearLayoutManager(this));
        cargarCursos();
        cargarEstudiantes();
        if (idInscripcion != -1 ) {
            tvInscripcionTitle.setText("Editar Inscripción");
            cargarDatosInscripcion(idInscripcion);
        } else {
            tvInscripcionTitle.setText("Nueva Inscripción");
        }
    }

    public void cmdGuardar_onClick(View view) {
        Estudiante estudiante = (Estudiante) spnEstudiantes.getSelectedItem();
        if (estudiante == null) {
            Toast.makeText(this, "Seleccione un estudiante", Toast.LENGTH_SHORT).show();
            return;
        }
        CursoAdapter cursoAdapter = (CursoAdapter) rvCursos.getAdapter();
        List<Integer> cursosSeleccionadosIds = cursoAdapter != null ? cursoAdapter.getCursosSeleccionadosIds() : new ArrayList<>();
        if (cursosSeleccionadosIds.isEmpty()) {
            Toast.makeText(this, "Seleccione al menos un curso", Toast.LENGTH_SHORT).show();
            return;
        }
        InscripcionRequest inscripcionRequest = new InscripcionRequest();
        inscripcionRequest.fecha = txtDate.getText().toString();
        inscripcionRequest.cedula = estudiante.cedula;
        inscripcionRequest.detalles = cursosSeleccionadosIds;
        try {
            boolean guardado;
            if (idInscripcion == -1) {
                guardado = InscripcionApiClient.createInscripcion(inscripcionRequest);
                if (guardado) {
                    Toast.makeText(this, "Inscripción creada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error al crear inscripción", Toast.LENGTH_SHORT).show();
                }
            } else {
                guardado = InscripcionApiClient.updateInscripcion(idInscripcion, inscripcionRequest);
                if (guardado) {
                    Toast.makeText(this, "Inscripción actualizada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error al actualizar inscripción", Toast.LENGTH_SHORT).show();
                    }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar inscripción: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarCursos() {
        try {
            List<Curso> cursos = CursoApiClient.getCursos();
            if (cursos != null) {
                CursoAdapter adapter = new CursoAdapter(cursos);
                rvCursos.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se encontraron cursos", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar cursos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarEstudiantes() {
        try {
            List<Estudiante> estudiantes = EstudianteApiClient.getEstudiantes();
            if (estudiantes != null) {
                ArrayAdapter<Estudiante> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estudiantes);
                spnEstudiantes.setAdapter(adapter);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar estudiantes: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatosInscripcion(int id) {
        try {
            InscripcionResponse inscripcionResponse = InscripcionApiClient.getInscripcion(id);
            if (inscripcionResponse != null) {
                txtDate.setText(inscripcionResponse.fecha);
                for (int i = 0; i < spnEstudiantes.getCount(); i++) {
                    Estudiante estudiante = (Estudiante) spnEstudiantes.getItemAtPosition(i);
                    if (Objects.equals(estudiante.cedula, inscripcionResponse.ins_estudiante.cedula)) {
                        spnEstudiantes.setSelection(i);
                        break;
                    }
                }
                List<Integer> cursosSeleccionadosIds = new ArrayList<>();
                if (inscripcionResponse.ins_inscripcion_det != null)  {
                    for (int i = 0; i<inscripcionResponse.ins_inscripcion_det.size(); i++) {
                        cursosSeleccionadosIds.add(inscripcionResponse.ins_inscripcion_det.get(i).id_curso);
                    }
                }
                CursoAdapter cursoAdapter = (CursoAdapter) rvCursos.getAdapter();
                if (cursoAdapter != null) {
                    cursoAdapter.setCursosSeleccionados(cursosSeleccionadosIds);
                }
            } else {
                Toast.makeText(this, "No se encontró la inscripción", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar datos de inscripción: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void cmdRegresar_onClick(View view) {
        finish();
    }
}