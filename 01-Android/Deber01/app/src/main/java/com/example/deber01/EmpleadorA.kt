package com.example.deber01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmpleadorA(
    private var empleados: List<Empleado>,
    private val onItemClick: (Empleado) -> Unit
) : RecyclerView.Adapter<EmpleadorA.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empleado, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado = empleados[position]
        holder.bind(empleado)
    }

    override fun getItemCount(): Int = empleados.size

    fun actualizarEmpleados(nuevosEmpleados: List<Empleado>) {
        empleados = nuevosEmpleados
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.txtNombre)

        fun bind(empleado: Empleado) {
            nombre.text = empleado.nombre
            itemView.setOnClickListener { onItemClick(empleado) }
        }
    }
}
