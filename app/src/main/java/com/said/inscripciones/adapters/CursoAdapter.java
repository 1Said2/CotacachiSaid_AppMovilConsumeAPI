package com.said.inscripciones.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.said.inscripciones.R;
import com.said.inscripciones.models.Curso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.ViewHolder> {

    private final List<Curso> cursos;
    private final Set<Integer> cursosSeleccionados = new HashSet<>();

    public CursoAdapter(List<Curso> cursos) {
        this.cursos = cursos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_curso_seleccion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Curso curso = cursos.get(position);
        holder.tvNombreCurso.setText(curso.nombre);
        holder.tvPrecio.setText("$" + String.format(java.util.Locale.getDefault(), "%.2f", curso.precio));
        holder.chckSeleccionado.setOnCheckedChangeListener(null);
        holder.chckSeleccionado.setChecked(cursosSeleccionados.contains(curso.id_curso));
        holder.chckSeleccionado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cursosSeleccionados.add(curso.id_curso);
            } else {
                cursosSeleccionados.remove(curso.id_curso);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursos != null ? cursos.size() : 0;
    }

    public List<Integer> getCursosSeleccionadosIds() {
        return new ArrayList<>(cursosSeleccionados);
    }

    public void setCursosSeleccionados(List<Integer> ids) {
        cursosSeleccionados.clear();
        cursosSeleccionados.addAll(ids);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCurso, tvPrecio;
        CheckBox chckSeleccionado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCurso = itemView.findViewById(R.id.tvNombreCurso);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            chckSeleccionado = itemView.findViewById(R.id.chckSeleccionado);
        }
    }
}
