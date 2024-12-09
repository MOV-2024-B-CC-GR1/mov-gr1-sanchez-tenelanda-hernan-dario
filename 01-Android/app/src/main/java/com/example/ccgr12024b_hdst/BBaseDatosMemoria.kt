package com.example.ccgr12024b_hdst

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador(1,"Hernan","a@a.com"))
            arregloBEntrenador.add(BEntrenador(2,"Dario","b@b.com"))
            arregloBEntrenador.add(BEntrenador(3,"Camila","c@c.com"))
        }
    }
}