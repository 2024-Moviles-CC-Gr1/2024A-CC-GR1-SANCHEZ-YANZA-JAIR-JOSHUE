package com.example.a2024saccr1jjsy

class BBaseDatosMemoria {
    companion object{
        val arregloBEnrenador = arrayListOf<BEnrenador>()
        init {
            arregloBEnrenador
                .add(
                    BEnrenador(1,"Adrian","a@a.com")
                )
            arregloBEnrenador
                .add(
                    BEnrenador(2,"Vicente","b@b.com")

                )

            arregloBEnrenador
                .add(
                    BEnrenador(3,"Carlonia","c@c.com")
                )
        }
    }
}