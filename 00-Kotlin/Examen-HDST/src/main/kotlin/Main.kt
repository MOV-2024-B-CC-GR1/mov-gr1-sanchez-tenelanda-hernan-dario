package org.example

import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Empleado(
    val id: Int,
    var nombre: String,
    var salario: Double,
    var fechaContratacion: LocalDate,
    var activo: Boolean
) {
    override fun toString(): String {
        return "$id|$nombre|$salario|$fechaContratacion|$activo"
    }

    companion object {
        fun fromString(line: String): Empleado {
            val parts = line.split("|")
            return Empleado(
                id = parts[0].toInt(),
                nombre = parts[1],
                salario = parts[2].toDouble(),
                fechaContratacion = LocalDate.parse(parts[3]),
                activo = parts[4].toBoolean()
            )
        }
    }
}

data class Departamento(
    val id: Int,
    var nombre: String,
    var presupuestoAnual: Double,
    var fechaCreacion: LocalDateTime,
    var empleados: MutableList<Int> = mutableListOf()
) {
    override fun toString(): String {
        return "$id|$nombre|$presupuestoAnual|$fechaCreacion|${empleados.joinToString(",")}"
    }

    companion object {
        fun fromString(line: String): Departamento {
            val parts = line.split("|")
            return Departamento(
                id = parts[0].toInt(),
                nombre = parts[1],
                presupuestoAnual = parts[2].toDouble(),
                fechaCreacion = LocalDateTime.parse(parts[3]),
                empleados = if (parts[4].isNotEmpty())
                    parts[4].split(",").map { it.toInt() }.toMutableList()
                else mutableListOf()
            )
        }
    }
}

class CRUDManager {

    private val rutaBase = "C:\\Users\\escritorio.virtual30\\Downloads"

    private val departamentosFile: File
    private val empleadosFile: File
    private var departamentos = mutableListOf<Departamento>()
    private var empleados = mutableListOf<Empleado>()

    init {

        val directorio = File(rutaBase)
        if (!directorio.exists()) {
            directorio.mkdirs()
            println("Directorio creado en: ${rutaBase}")
        }

        departamentosFile = File(rutaBase + "departamentos.txt")
        empleadosFile = File(rutaBase + "empleados.txt")

        println("Archivos almacenados en: ${rutaBase}")
        cargarDatos()
    }

    private fun mostrarEncabezado(titulo: String) {
        println("\n============================================================")
        println("                 SERVICIOS TECNOLÓGICOS")
        println("             EXAMEN APLICACIONES MÓVILES")
        println("               Desarrollado por: Hernán Sánchez")
        println("============================================================")
        println("                    $titulo")
        println("============================================================\n")
    }

    private fun mostrarPie() {
        println("\n============================================================")
        println("                 © 2024 Servicios Tecnológicos")
        println("============================================================\n")
    }

    private fun cargarDatos() {
        try {
            if (departamentosFile.exists()) {
                departamentos = departamentosFile.readLines().map { Departamento.fromString(it) }.toMutableList()
                println("✓ Datos de departamentos cargados correctamente")
            }
            if (empleadosFile.exists()) {
                empleados = empleadosFile.readLines().map { Empleado.fromString(it) }.toMutableList()
                println("✓ Datos de empleados cargados correctamente")
            }
        } catch (e: Exception) {
            println("✗ Error al cargar los datos: ${e.message}")
        }
    }

    private fun guardarDatos() {
        try {
            departamentosFile.writeText(departamentos.joinToString("\n"))
            empleadosFile.writeText(empleados.joinToString("\n"))
            println("✓ Datos guardados correctamente")
        } catch (e: Exception) {
            println("✗ Error al guardar los datos: ${e.message}")
        }
    }

    fun crearDepartamento() {
        mostrarEncabezado("CREAR NUEVO DEPARTAMENTO")

        try {
            println("→ Ingrese ID del departamento:")
            print("► ")
            val id = readLine()!!.toInt()

            if (departamentos.any { it.id == id }) {
                println("\n✗ Error: Ya existe un departamento con ese ID!")
                return
            }

            println("→ Ingrese nombre del departamento:")
            print("► ")
            val nombre = readLine()!!

            println("→ Ingrese presupuesto anual:")
            print("► ")
            val presupuesto = readLine()!!.toDouble()

            val departamento = Departamento(
                id = id,
                nombre = nombre,
                presupuestoAnual = presupuesto,
                fechaCreacion = LocalDateTime.now()
            )
            departamentos.add(departamento)
            guardarDatos()
            println("\n✓ Departamento creado exitosamente!")
        } catch (e: Exception) {
            println("\n✗ Error al crear departamento: ${e.message}")
        }
        mostrarPie()
    }

    fun crearEmpleado() {
        mostrarEncabezado("CREAR NUEVO EMPLEADO")

        try {
            println("→ Ingrese ID del empleado:")
            print("► ")
            val id = readLine()!!.toInt()

            if (empleados.any { it.id == id }) {
                println("\n✗ Error: Ya existe un empleado con ese ID!")
                return
            }

            println("→ Ingrese nombre del empleado:")
            print("► ")
            val nombre = readLine()!!

            println("→ Ingrese salario:")
            print("► ")
            val salario = readLine()!!.toDouble()

            println("→ ¿Está activo? (true/false):")
            print("► ")
            val activo = readLine()!!.toBoolean()

            val empleado = Empleado(
                id = id,
                nombre = nombre,
                salario = salario,
                fechaContratacion = LocalDate.now(),
                activo = activo
            )

            println("→ Ingrese ID del departamento al que pertenece:")
            print("► ")
            val deptoId = readLine()!!.toInt()

            val departamento = departamentos.find { it.id == deptoId }
            if (departamento != null) {
                empleados.add(empleado)
                departamento.empleados.add(empleado.id)
                guardarDatos()
                println("\n✓ Empleado creado exitosamente!")
            } else {
                println("\n✗ Error: Departamento no encontrado!")
            }
        } catch (e: Exception) {
            println("\n✗ Error al crear empleado: ${e.message}")
        }
        mostrarPie()
    }

    fun listarDepartamentos() {
        mostrarEncabezado("LISTA DE DEPARTAMENTOS")

        if (departamentos.isEmpty()) {
            println("✗ No hay departamentos registrados")
        } else {
            departamentos.forEach { depto ->
                println("╔════════════════════════════════════════")
                println("║ ID: ${depto.id}")
                println("║ Nombre: ${depto.nombre}")
                println("║ Presupuesto: $${String.format("%,.2f", depto.presupuestoAnual)}")
                println("║ Fecha de creación: ${depto.fechaCreacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))}")
                println("║ Número de empleados: ${depto.empleados.size}")
                println("╚════════════════════════════════════════")
            }
        }
        mostrarPie()
    }

    fun listarEmpleados() {
        mostrarEncabezado("LISTA DE EMPLEADOS")

        if (empleados.isEmpty()) {
            println("✗ No hay empleados registrados")
        } else {
            empleados.forEach { emp ->
                println("╔════════════════════════════════════════")
                println("║ ID: ${emp.id}")
                println("║ Nombre: ${emp.nombre}")
                println("║ Salario: $${String.format("%,.2f", emp.salario)}")
                println("║ Fecha de contratación: ${emp.fechaContratacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}")
                println("║ Estado: ${if (emp.activo) "Activo" else "Inactivo"}")
                // Encontrar el departamento al que pertenece
                val departamento = departamentos.find { it.empleados.contains(emp.id) }
                println("║ Departamento: ${departamento?.nombre ?: "No asignado"}")
                println("╚════════════════════════════════════════")
            }
        }
        mostrarPie()
    }

    fun actualizarDepartamento() {
        mostrarEncabezado("ACTUALIZAR DEPARTAMENTO")

        try {
            println("→ Ingrese ID del departamento a actualizar:")
            print("► ")
            val id = readLine()!!.toInt()
            val departamento = departamentos.find { it.id == id }

            if (departamento != null) {
                println("→ Ingrese nuevo nombre (actual: ${departamento.nombre}):")
                print("► ")
                val nombre = readLine()!!

                println("→ Ingrese nuevo presupuesto (actual: $${String.format("%,.2f", departamento.presupuestoAnual)}):")
                print("► ")
                val presupuesto = readLine()!!.toDouble()

                departamento.nombre = nombre
                departamento.presupuestoAnual = presupuesto
                guardarDatos()
                println("\n✓ Departamento actualizado exitosamente!")
            } else {
                println("\n✗ Error: Departamento no encontrado!")
            }
        } catch (e: Exception) {
            println("\n✗ Error al actualizar departamento: ${e.message}")
        }
        mostrarPie()
    }

    fun actualizarEmpleado() {
        mostrarEncabezado("ACTUALIZAR EMPLEADO")

        try {
            println("→ Ingrese ID del empleado a actualizar:")
            print("► ")
            val id = readLine()!!.toInt()
            val empleado = empleados.find { it.id == id }

            if (empleado != null) {
                println("→ Ingrese nuevo nombre (actual: ${empleado.nombre}):")
                print("► ")
                val nombre = readLine()!!

                println("→ Ingrese nuevo salario (actual: $${String.format("%,.2f", empleado.salario)}):")
                print("► ")
                val salario = readLine()!!.toDouble()

                println("→ ¿Está activo? (actual: ${empleado.activo}):")
                print("► ")
                val activo = readLine()!!.toBoolean()

                empleado.nombre = nombre
                empleado.salario = salario
                empleado.activo = activo
                guardarDatos()
                println("\n✓ Empleado actualizado exitosamente!")
            } else {
                println("\n✗ Error: Empleado no encontrado!")
            }
        } catch (e: Exception) {
            println("\n✗ Error al actualizar empleado: ${e.message}")
        }
        mostrarPie()
    }

    fun eliminarDepartamento() {
        mostrarEncabezado("ELIMINAR DEPARTAMENTO")

        try {
            println("→ Ingrese ID del departamento a eliminar:")
            print("► ")
            val id = readLine()!!.toInt()
            val departamento = departamentos.find { it.id == id }

            if (departamento != null) {
                if (departamento.empleados.isNotEmpty()) {
                    println("\n✗ Error: No se puede eliminar un departamento con empleados")
                    return
                }

                if (departamentos.removeIf { it.id == id }) {
                    guardarDatos()
                    println("\n✓ Departamento eliminado exitosamente!")
                }
            } else {
                println("\n✗ Error: Departamento no encontrado!")
            }
        } catch (e: Exception) {
            println("\n✗ Error al eliminar departamento: ${e.message}")
        }
        mostrarPie()
    }

    fun eliminarEmpleado() {
        mostrarEncabezado("ELIMINAR EMPLEADO")

        try {
            println("→ Ingrese ID del empleado a eliminar:")
            print("► ")
            val id = readLine()!!.toInt()
            if (empleados.removeIf { it.id == id }) {
                departamentos.forEach { depto ->
                    depto.empleados.remove(id)
                }
                guardarDatos()
                println("\n✓ Empleado eliminado exitosamente!")
            } else {
                println("\n✗ Error: Empleado no encontrado!")
            }
        } catch (e: Exception) {
            println("\n✗ Error al eliminar empleado: ${e.message}")
        }
        mostrarPie()
    }
}

fun main() {
    val crudManager = CRUDManager()

    while (true) {
        println("\n============================================================")
        println("                 SERVICIOS TECNOLÓGICOS")
        println("             EXAMEN APLICACIONES MÓVILES")
        println("               Desarrollado por: Hernán Sánchez")
        println("============================================================")
        println("                    MENÚ PRINCIPAL")
        println("============================================================")
        println("1. ➕ Crear Departamento")
        println("2. ➕ Crear Empleado")
        println("3. 📋 Listar Departamentos")
        println("4. 📋 Listar Empleados")
        println("5. ✏️  Actualizar Departamento")
        println("6. ✏️  Actualizar Empleado")
        println("7. ❌ Eliminar Departamento")
        println("8. ❌ Eliminar Empleado")
        println("9. 🚪 Salir")
        println("============================================================")
        println("→ Seleccione una opción:")
        print("► ")

        try {
            when (readLine()!!.toInt()) {
                1 -> crudManager.crearDepartamento()
                2 -> crudManager.crearEmpleado()
                3 -> crudManager.listarDepartamentos()
                4 -> crudManager.listarEmpleados()
                5 -> crudManager.actualizarDepartamento()
                6 -> crudManager.actualizarEmpleado()
                7 -> crudManager.eliminarDepartamento()
                8 -> crudManager.eliminarEmpleado()
                9 -> {
                    println("\n============================================================")
                    println("                ¡Gracias por usar el sistema!")
                    println("                Desarrollado por: Hernán Sánchez")
                    println("             Servicios Tecnológicos © 2024")
                    println("============================================================")
                    return
                }

                else -> println("✗ Error: Opción no válida")
            }
        } catch (e: Exception) {
            println("\n✗ Error: Opción no válida. Por favor, ingrese un número del 1 al 9.")
        }
    }
}