package com.example.linkedln

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

data class Post(var likeCount: Int, var isLiked: Boolean)

class Inicio : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var postList: List<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crea una lista inmutable de un solo post
        postList = listOf(
            Post(likeCount = 17, isLiked = false)
        )

        // Convierte la lista a MutableList al pasársela al adaptador
        postAdapter = PostAdapter(postList.toMutableList())
        recyclerView.adapter = postAdapter

        // Agrega el listener para imageView2
        val imageView2 = findViewById<ImageView>(R.id.imageView2)
        imageView2.setOnClickListener {
            val intent = Intent(this, Comentarios::class.java)
            startActivity(intent)
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
