package com.example.a2024saccr1jjsy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar


class ACicloVida : AppCompatActivity() {
    var textoGlobal = ""
    fun mostrarSnackbar(texto:String){
        textoGlobal += texto
        val snack=Snackbar.make(
            findViewById(R.id.cl_ciclo_vida),
            textoGlobal,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)

        mostrarSnackbar("OnCreate")
    }
    override  fun onStart(){
        super.onStart()
        mostrarSnackbar("onStart")

    }

    override  fun onResume(){
        super.onResume()
        mostrarSnackbar("onResume")

    }

    override  fun onStop(){
        super.onStop()
        mostrarSnackbar("onStop")

    }


    override  fun onRestart(){
        super.onRestart()
        mostrarSnackbar("onRestart")

    }


    override  fun onPause(){
        super.onPause()
        mostrarSnackbar("onPause")

    }

    override  fun onDestroy(){
        super.onDestroy()
        mostrarSnackbar("onDestroy")

    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("variableTextoGuardado", textoGlobal)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // RECUPERAR LA VARIABLE
        val textoRecuperadoVariable: String? = savedInstanceState.getString("variableTextoGuardado")
        if (textoRecuperadoVariable != null) {
            mostrarSnackbar(textoRecuperadoVariable)
            textoGlobal = textoRecuperadoVariable
        }
    }


}