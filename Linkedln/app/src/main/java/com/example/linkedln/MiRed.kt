package com.example.linkedln

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MiRed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mi_red)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura el BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, Inicio::class.java))
                    true
                }
                R.id.search -> {
                    startActivity(Intent(this, MiRed::class.java))
                    true
                }
                R.id.add -> {
                    // Agrega la lógica para publicar
                    true
                }
                R.id.message -> {
                    startActivity(Intent(this, Notificaciones::class.java))
                    true
                }
                R.id.user2 -> {
                    startActivity(Intent(this, Empleado::class.java))
                    true
                }
                else -> false
            }
        }
    }
}