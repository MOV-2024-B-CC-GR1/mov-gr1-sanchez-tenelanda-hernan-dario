package com.example.deber01DB

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class DepartamentosAct : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DepartmentA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_departamentos)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = DepartmentA(Repositorio.obtenerDepartamentos(), ::onDepartamentoClick)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btnAgregarEmpleado).setOnClickListener {
            val id = (Repositorio.obtenerDepartamentos().size + 1)
            val nombre= (Repositorio.obtenerDepartamentos().size + 2)
            val presupuestoAnual = (Repositorio.obtenerDepartamentos().size + 3)
            val nuevoDepartamento = Departamento(id = id, nombre = "nombre", presupuestoAnual = 50000)
            println(id)
            println(nombre)
            println(presupuestoAnual)
            Repositorio.agregarDepartamento(this, nuevoDepartamento)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()

        adapter = DepartmentA(Repositorio.obtenerDepartamentos(), ::onDepartamentoClick)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun onDepartamentoClick(departamento: Departamento) {
        val options = arrayOf("Editar", "Eliminar", "Ver departamentos", "Ir Mapa")
        AlertDialog.Builder(this)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editarDepratamentos(departamento)
                    1 -> eliminarDepartamentos(departamento)
                    2 -> verDepratamentos(departamento)
                    3 -> irMapa(departamento)
                }
            }
            .show()
    }

    private fun editarDepratamentos(departamento: Departamento) {
        val intent = Intent(this, EditarActivity::class.java)
        intent.putExtra("itemId", departamento.id)
        intent.putExtra("nombre", departamento.nombre)
        intent.putExtra("Presupuesto", departamento.presupuestoAnual)
        intent.putExtra("tipo", "departamento")
        startActivity(intent)
    }

    private fun eliminarDepartamentos(departamento: Departamento) {
        Repositorio.eliminarDepartamento(this, departamento.id)
        adapter.notifyDataSetChanged()
    }

    private fun verDepratamentos(departamento: Departamento) {
        val intent = Intent(this, EmpleadosAct::class.java)
        intent.putExtra("universidadId", departamento.id)
        startActivity(intent)
    }

    private fun irMapa(departamento: Departamento) {
        try {
            val intent = Intent(this, GGoogleMaps::class.java)
            // Opcionalmente puedes pasar datos del departamento
            intent.putExtra("departamento_id", departamento.id)
            intent.putExtra("departamento_nombre", departamento.nombre)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            // Opcionalmente muestra un mensaje de error
            Toast.makeText(this, "Error al abrir el mapa: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

}
