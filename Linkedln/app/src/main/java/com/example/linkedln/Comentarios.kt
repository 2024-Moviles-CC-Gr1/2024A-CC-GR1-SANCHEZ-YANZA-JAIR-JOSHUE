package com.example.linkedln

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linkedln.databinding.ActivityComentariosBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class Comentarios : AppCompatActivity() {

    private lateinit var binding: ActivityComentariosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComentariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el RecyclerView
        val comentarios = listOf(
            ComentariosAdapter.Comentario(
                "Jose Perez",
                "3er+",
                "Student  2d",
                "Quisiera expresar mi agradecimiento por la excelente conferencia impartida. Fue una experiencia muy enriquecedora y educativa. Los temas abordados fueron presentados de manera clara, permitiéndome adquirir valiosos conocimientos.",
                R.drawable.user1
            ),
            ComentariosAdapter.Comentario(
                "HAIR Sancehz",
                "1d",
                "Profesora  5d",
                "La conferencia resultó sumamente inspiradora y motivadora. Me llevo una gran cantidad de ideas innovadoras y útiles para aplicar en mis clases, lo que sin duda enriquecerá mis enseñanzas y beneficiará a mis estudiantes.",
                R.drawable.user3
            ),
            ComentariosAdapter.Comentario(
                "Martin Velez",
                "2h",
                "Ingeniera  1h",
                "Me encantó la forma en que se abordaron los diversos temas durante la conferencia. La presentación fue muy clara y concisa, permitiendo que cada aspecto fuera fácilmente comprendido. El enfoque directo y preciso facilitó una absorción rápida de la información.",
                R.drawable.user4
            ),
            ComentariosAdapter.Comentario(
                "Jose Morales",
                "5d",
                "Estudiante  3d",
                "La conferencia fue muy buena, destacando especialmente la organización y la calidad del contenido presentado. Cada sección estuvo meticulosamente planificada y ejecutada, lo que permitió una experiencia educativa excepcionalmente fluida y enriquecedora.",
                R.drawable.user1
            ),
            ComentariosAdapter.Comentario(
                "Pedro Suarez",
                "1w",
                "Investigador  1w",
                "Me encantó la forma en que se abordaron los diversos temas durante la conferencia. La presentación fue muy clara y concisa, permitiendo que cada aspecto fuera fácilmente comprendido. El enfoque directo y preciso facilitó una absorción rápida de la información.",
                R.drawable.user4
            ),
            ComentariosAdapter.Comentario(
                "Sofia ",
                "2w",
                "Developer  2w",
                "Aprendí una cantidad significativa de conocimientos valiosos durante la conferencia. Cada parte de la presentación ofreció información relevante y útil, y puedo afirmar con certeza que asistir a esta conferencia fue una experiencia que valió plenamente la pena.",
                R.drawable.user3
            )
            // Agrega más comentarios aquí si lo deseas
        )

        val adapter = ComentariosAdapter(comentarios)
        binding.comentarios.apply {
            layoutManager = LinearLayoutManager(this@Comentarios)
            this.adapter = adapter
        }

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
