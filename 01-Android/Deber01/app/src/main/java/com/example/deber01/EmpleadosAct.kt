package com.example.deber01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog

class EmpleadosAct : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter_c: EmpleadorA
    private var departamentoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_empleados)

        departamentoId = intent.getIntExtra("DepartamentoId", 0)

        recyclerView = findViewById(R.id.recyclerView)
        val empleados = Repositorio.obtenerEmplDeDept(departamentoId)
        adapter_c = EmpleadorA(empleados, ::onEmpleadoClick)
        recyclerView.adapter = adapter_c
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btnAgregarEmpleado).setOnClickListener {
            val nuevoEmpleado= Empleado(id = (empleados.size + 1), nombre = "emplado nuevo", salario = 750, deptId = departamentoId)
            Repositorio.agregarEmpleado(this, nuevoEmpleado)
            adapter_c.actualizarEmpleados(Repositorio.obtenerEmplDeDept(departamentoId))
        }
    }

    private fun onEmpleadoClick(empleado: Empleado) {
        val options = arrayOf("Editar", "Eliminar")
        AlertDialog.Builder(this)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editarEmpleado(empleado)
                    1 -> eliminarEmpleados(empleado)
                }
            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        adapter_c.actualizarEmpleados(Repositorio.obtenerEmplDeDept(departamentoId))
    }

    private fun editarEmpleado(empleado: Empleado) {
        val intent = Intent(this, EditarActivity::class.java)
        intent.putExtra("itemId", empleado.id)
        intent.putExtra("nombre", empleado.nombre)
        intent.putExtra("salario", empleado.salario)
        intent.putExtra("tipo", "empleado")
        startActivity(intent)
    }

    private fun eliminarEmpleados(empleado: Empleado) {
        Repositorio.eliminarEmpleado(this, empleado.id)
        adapter_c.actualizarEmpleados(Repositorio.obtenerEmplDeDept(departamentoId))
    }
}
