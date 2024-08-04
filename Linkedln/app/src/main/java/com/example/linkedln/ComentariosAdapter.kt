package com.example.linkedln

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linkedln.databinding.ItemComentarioBinding

class ComentariosAdapter(private val comentarios: List<Comentario>) :
    RecyclerView.Adapter<ComentariosAdapter.ComentarioViewHolder>() {

    // Definir la clase Comentario dentro del adaptador
    data class Comentario(
        val usuario: String,
        val tiempo: String,
        val trabajo: String,
        val textoComentario: String,
        val imagenUsuario: Int // Referencia al recurso de la imagen
    )

    inner class ComentarioViewHolder(private val binding: ItemComentarioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comentario: Comentario) {
            binding.likeCountTextView.text = comentario.usuario
            binding.job.text = comentario.trabajo
            binding.comentario.text = comentario.textoComentario
            binding.likeImageView.setImageResource(comentario.imagenUsuario)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val binding = ItemComentarioBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ComentarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        holder.bind(comentarios[position])
    }

    override fun getItemCount(): Int {
        return comentarios.size
    }
}

