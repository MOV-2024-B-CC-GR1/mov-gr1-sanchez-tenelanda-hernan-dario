package com.example.deber01

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, DepartamentosAct::class.java)
        Repositorio.init(this)
        startActivity(intent)
        finish()
    }
}
