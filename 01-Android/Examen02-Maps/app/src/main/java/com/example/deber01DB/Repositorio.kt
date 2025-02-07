package com.example.deber01DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.deber01DB.DatabaseHelper
import com.example.deber01DB.Departamento
import com.example.deber01DB.Empleado

object Repositorio {
    private lateinit var dbHelper: DatabaseHelper

    fun init(context: Context) {
        dbHelper = DatabaseHelper(context)
    }

    fun agregarDepartamento(context: Context, departamento: Departamento): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_DEPT_NOMBRE, departamento.nombre)
            put(DatabaseHelper.COL_DEPT_PRESUPUESTO, departamento.presupuestoAnual)
        }
        return db.insert(DatabaseHelper.TABLE_DEPARTAMENTOS, null, values)
    }

    fun obtenerDepartamentos(): List<Departamento> {
        val departamentos = mutableListOf<Departamento>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_DEPARTAMENTOS,
            null,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val departamento = Departamento(
                    getInt(getColumnIndexOrThrow(DatabaseHelper.COL_DEPT_ID)),
                    getString(getColumnIndexOrThrow(DatabaseHelper.COL_DEPT_NOMBRE)),
                    getInt(getColumnIndexOrThrow(DatabaseHelper.COL_DEPT_PRESUPUESTO))
                )
                departamentos.add(departamento)
            }
        }
        cursor.close()
        return departamentos
    }

    fun eliminarDepartamento(context: Context, id: Int) {
        val db = dbHelper.writableDatabase
        db.delete(DatabaseHelper.TABLE_DEPARTAMENTOS, "${DatabaseHelper.COL_DEPT_ID} = ?", arrayOf(id.toString()))
    }

    fun obtenerEmplDeDept(deptId: Int): List<Empleado> {
        val empleados = mutableListOf<Empleado>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_EMPLEADOS,
            null,
            "${DatabaseHelper.COL_EMP_DEPT_ID} = ?",
            arrayOf(deptId.toString()),
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val empleado = Empleado(
                    getInt(getColumnIndexOrThrow(DatabaseHelper.COL_EMP_ID)),
                    getString(getColumnIndexOrThrow(DatabaseHelper.COL_EMP_NOMBRE)),
                    getInt(getColumnIndexOrThrow(DatabaseHelper.COL_EMP_SALARIO)),
                    getInt(getColumnIndexOrThrow(DatabaseHelper.COL_EMP_DEPT_ID))
                )
                empleados.add(empleado)
            }
        }
        cursor.close()
        return empleados
    }

    fun agregarEmpleado(context: Context, empleado: Empleado): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_EMP_NOMBRE, empleado.nombre)
            put(DatabaseHelper.COL_EMP_SALARIO, empleado.salario)
            put(DatabaseHelper.COL_EMP_DEPT_ID, empleado.deptId)
        }
        return db.insert(DatabaseHelper.TABLE_EMPLEADOS, null, values)
    }

    fun eliminarEmpleado(context: Context, id: Int) {
        val db = dbHelper.writableDatabase
        db.delete(DatabaseHelper.TABLE_EMPLEADOS, "${DatabaseHelper.COL_EMP_ID} = ?", arrayOf(id.toString()))
    }

    fun editarNombreDepartamento(context: Context, id: Int, nuevoNombre: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_DEPT_NOMBRE, nuevoNombre)
        }
        db.update(
            DatabaseHelper.TABLE_DEPARTAMENTOS,
            values,
            "${DatabaseHelper.COL_DEPT_ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun editarEmpleado(context: Context, id: Int, nuevoNombre: String, nuevoSalario: Int) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_EMP_NOMBRE, nuevoNombre)
            put(DatabaseHelper.COL_EMP_SALARIO, nuevoSalario)
        }
        db.update(
            DatabaseHelper.TABLE_EMPLEADOS,
            values,
            "${DatabaseHelper.COL_EMP_ID} = ?",
            arrayOf(id.toString())
        )
    }
}