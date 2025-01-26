package com.example.deber01
import java.time.LocalDateTime

data class Empleado(
    val id: Int,
    var nombre: String,
    var salario: Int,
    var deptId: Int,
)