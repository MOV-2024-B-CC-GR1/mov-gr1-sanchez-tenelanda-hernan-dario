package com.example.deber01DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "empresa.db"
        const val DATABASE_VERSION = 1

        const val TABLE_DEPARTAMENTOS = "departamentos"
        const val TABLE_EMPLEADOS = "empleados"

        const val COL_DEPT_ID = "id"
        const val COL_DEPT_NOMBRE = "nombre"
        const val COL_DEPT_PRESUPUESTO = "presupuesto_anual"

        const val COL_EMP_ID = "id"
        const val COL_EMP_NOMBRE = "nombre"
        const val COL_EMP_SALARIO = "salario"
        const val COL_EMP_DEPT_ID = "dept_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createDepartamentos = """
            CREATE TABLE $TABLE_DEPARTAMENTOS (
                $COL_DEPT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_DEPT_NOMBRE TEXT NOT NULL,
                $COL_DEPT_PRESUPUESTO INTEGER NOT NULL
            )
        """.trimIndent()

        val createEmpleados = """
            CREATE TABLE $TABLE_EMPLEADOS (
                $COL_EMP_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_EMP_NOMBRE TEXT NOT NULL,
                $COL_EMP_SALARIO INTEGER NOT NULL,
                $COL_EMP_DEPT_ID INTEGER NOT NULL,
                FOREIGN KEY($COL_EMP_DEPT_ID) REFERENCES $TABLE_DEPARTAMENTOS($COL_DEPT_ID) ON DELETE CASCADE
            )
        """.trimIndent()

        db.execSQL(createDepartamentos)
        db.execSQL(createEmpleados)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLEADOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DEPARTAMENTOS")
        onCreate(db)
    }
}