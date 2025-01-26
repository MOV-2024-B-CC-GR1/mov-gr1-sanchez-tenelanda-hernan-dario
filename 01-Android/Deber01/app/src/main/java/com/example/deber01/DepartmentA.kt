package com.example.deber01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DepartmentA(
    private var departamentos: List<Departamento>,
    private val onItemClick: (Departamento) -> Unit
) : RecyclerView.Adapter<DepartmentA.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_departamento, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val departamento = departamentos[position]
        holder.bind(departamento)
    }

    override fun getItemCount(): Int = departamentos.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.txtNombre)

        fun bind(departamento: Departamento) {
            nombre.text = departamento.nombre
            itemView.setOnClickListener { onItemClick(departamento) }
        }
    }
}
