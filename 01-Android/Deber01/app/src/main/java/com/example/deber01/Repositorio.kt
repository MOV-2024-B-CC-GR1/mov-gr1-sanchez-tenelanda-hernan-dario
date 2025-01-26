package com.example.deber01

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter

object Repositorio {
    public val departamentos = mutableListOf<Departamento>()
    public val empleados = mutableListOf<Empleado>()

    fun init(context: Context) {
        cargarDatos(context)
    }

    fun agregarDepartamento(context: Context, departamento: Departamento) {
        departamentos.add(departamento)
        guardarDatos(context)
    }

    fun eliminarDepartamento(context: Context, id: Int) {
        departamentos.removeAll { it.id == id }
        empleados.removeAll { it.deptId == id }
        guardarDatos(context)
    }

    fun obtenerEmplDeDept(deptid: Int): List<Empleado> {
        return empleados.filter { it.deptId == deptid }
    }

    fun agregarEmpleado(context: Context, empleado: Empleado) {
        empleados.add(empleado)
        guardarDatos(context)
    }

    fun eliminarEmpleado(context: Context, id: Int) {
        empleados.removeAll { it.id == id }
        guardarDatos(context)
    }

    fun editarNombreDepartamento(context: Context, id: Int, nuevoNombre: String) {
        val departamento = departamentos.find { it.id == id }
        departamento?.nombre = nuevoNombre
        guardarDatos(context)
    }

    fun editarNombreEmpleado(context: Context, id: Int, nuevoNombre: String) {
        val empleado = empleados.find { it.id == id }
        empleado?.nombre = nuevoNombre
        guardarDatos(context)
    }

    fun editarEmpleado(context: Context, id: Int, nuevoNombre: String, nuevoSalario: Int) {
        val empleado = empleados.find { it.id == id }
        empleado?.apply {
            nombre = nuevoNombre
            salario = nuevoSalario
        }
        guardarDatos(context)
    }

    private fun guardarDatos(context: Context) {
        val departamentosJson = JSONArray()
        departamentos.forEach { departamento ->
            val departamentoJson = JSONObject()
            departamentoJson.put("id", departamento.id)
            departamentoJson.put("nombre", departamento.nombre)
            departamentoJson.put("presupuestoAnual", departamento.presupuestoAnual)
            departamentosJson.put(departamentoJson)
        }

        val empleadosJson = JSONArray()
        empleados.forEach { empleado ->
            val empleadoJson = JSONObject()
            empleadoJson.put("id", empleado.id)
            empleadoJson.put("nombre", empleado.nombre)
            empleadoJson.put("salario", empleado.salario)
            empleadoJson.put("deptId", empleado.deptId)
            empleadosJson.put(empleadoJson)
        }

        val file = File(context.filesDir, "data.json")
        val fileWriter = FileWriter(file)
        fileWriter.write("{\"departamentos\": $departamentosJson, \"empleados\": $empleadosJson}")
        fileWriter.close()
    }

    private fun cargarDatos(context: Context) {
        val file = File(context.filesDir, "data.json")
        if (file.exists()) {
            val jsonString = file.readText()
            val jsonObject = JSONObject(jsonString)
            val departamentosJson = jsonObject.getJSONArray("departamentos")
            for (i in 0 until departamentosJson.length()) {
                val departamentoJson = departamentosJson.getJSONObject(i)
                departamentos.add(
                    Departamento(
                        departamentoJson.getInt("id"),
                        departamentoJson.getString("nombre"),
                        departamentoJson.getInt("presupuestoAnual")
                    )
                )
            }
            val empleadosJson = jsonObject.getJSONArray("empleados")
            for (i in 0 until empleadosJson.length()) {
                val empleadoJson = empleadosJson.getJSONObject(i)
                empleados.add(
                    Empleado(
                        empleadoJson.getInt("id"),
                        empleadoJson.getString("nombre"),
                        empleadoJson.getInt("salario"),
                        empleadoJson.getInt("deptId")
                    )
                )
            }
        }
    }
}
