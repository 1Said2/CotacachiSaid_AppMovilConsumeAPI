package com.said.inscripciones.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.said.inscripciones.R;
import com.said.inscripciones.models.InscripcionResponse;

import java.util.List;

public class InscripcionAdapter extends RecyclerView.Adapter<InscripcionAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onEditar(int idInscripcion);
        void onEliminar(int idInscripcion);
    }
    private List<InscripcionResponse> inscripciones;
    private OnItemClickListener listener;

    public InscripcionAdapter(List<InscripcionResponse> inscripciones, OnItemClickListener listener) {
        this.inscripciones = inscripciones;
        this.listener = listener;
    }


    @NonNull
    @Override
    public InscripcionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inscripcion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InscripcionAdapter.ViewHolder holder, int position) {
        InscripcionResponse inscripcionResponse = inscripciones.get(position);
        if (inscripcionResponse.ins_estudiante != null) {
            String nombreCompleto = inscripcionResponse.ins_estudiante.nombres + " " + inscripcionResponse.ins_estudiante.apellidos;
            holder.tvNombreEstudiante.setText(nombreCompleto);
        } else {
            holder.tvNombreEstudiante.setText("Estudiante sin registrar");
        }
        holder.tvFechaInscripcion.setText("Fecha: " + inscripcionResponse.fecha);
        if (inscripcionResponse.ins_inscripcion_det != null) {
            holder.tvCantidadCursos.setText("Cursos inscritos: " + inscripcionResponse.ins_inscripcion_det.size());
        } else {
            holder.tvCantidadCursos.setText("Cursos inscritos: 0");
        }
        holder.cmdEditar.setOnClickListener(v -> listener.onEditar(inscripcionResponse.id_inscripcion_cab));
        holder.cmdEliminar.setOnClickListener(v -> listener.onEliminar(inscripcionResponse.id_inscripcion_cab));
    }

    @Override
    public int getItemCount() {
        return inscripciones != null? inscripciones.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreEstudiante, tvFechaInscripcion, tvCantidadCursos;
        Button cmdEditar, cmdEliminar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreEstudiante = itemView.findViewById(R.id.tvNombreCurso);
            tvFechaInscripcion = itemView.findViewById(R.id.tvPrecio);
            tvCantidadCursos = itemView.findViewById(R.id.tvCantidadCursos);
            cmdEditar = itemView.findViewById(R.id.cmdEditar);
            cmdEliminar = itemView.findViewById(R.id.cmdEliminar);
        }
    }
}
