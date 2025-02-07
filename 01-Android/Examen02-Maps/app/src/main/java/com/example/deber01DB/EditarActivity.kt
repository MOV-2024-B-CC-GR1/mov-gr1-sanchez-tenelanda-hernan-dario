package com.example.deber01DB

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditarActivity : AppCompatActivity() {
    private var itemId: Int = 0
    private var tipo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        itemId = intent.getIntExtra("itemId", 0)
        tipo = intent.getStringExtra("tipo") ?: ""

        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextSalario = findViewById<EditText>(R.id.editTextSalario)

        if (tipo == "empleado") {
            editTextNombre.setText(intent.getStringExtra("nombre"))
            editTextSalario.setText(intent.getIntExtra("salario", 0).toString())
        } else {
            editTextNombre.setText(intent.getStringExtra("nombre"))
        }

        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            val nuevoNombre = editTextNombre.text.toString()
            if (tipo == "departamento") {
                Repositorio.editarNombreDepartamento(this, itemId, nuevoNombre)
            } else {
                val nuevoSalario = editTextSalario.text.toString().toIntOrNull() ?: 0
                Repositorio.editarEmpleado(this, itemId, nuevoNombre, nuevoSalario)
            }
            finish()
        }
    }
}