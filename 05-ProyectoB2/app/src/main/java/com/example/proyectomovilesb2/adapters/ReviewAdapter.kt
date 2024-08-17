package com.example.proyectomovilesb2.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomovilesb2.R
import com.example.proyectomovilesb2.entities.BReseña
import com.example.proyectomovilesb2.sqlhelpers.ESqliteHelperUsuario


class ReviewAdapter(
    private var reviews: List<BReseña>, // Cambiado a var para permitir modificaciones
    private val sqliteHelperUsuario: ESqliteHelperUsuario
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtAutor: TextView = itemView.findViewById(R.id.txtAutor)
        val txtTema: TextView = itemView.findViewById(R.id.txtTema)
        val txtDate: TextView = itemView.findViewById(R.id.txtDate)
        val btnUltimos: Button = itemView.findViewById(R.id.btnUltimos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tarjeta_resena, parent, false)
        Log.d("ReviewAdapter", "View creada para el ViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviews[position]
        Log.d("ReviewAdapter", "onBindViewHolder: review=$review")

        // Obtén el nombre del usuario por ID
        val nombreAutor = sqliteHelperUsuario.consultarUsuarioPorID(review.id_usuario)
        Log.d("ReviewAdapter", "Nombre autor obtenido para id ${review.id_usuario}: $nombreAutor")

        if (nombreAutor != null) {
            holder.txtAutor.text = nombreAutor.nombre ?: "Desconocido"
        } else {
            Log.e("ReviewAdapter", "Error: Nombre autor es null para id ${review.id_usuario}")
            holder.txtAutor.text = "Desconocido"
        }

        holder.txtTema.text = review.titulo
        holder.txtDate.text = review.fecha
        holder.btnUltimos.text = review.nombre_categoria_reseña
    }

    override fun getItemCount() = reviews.size

    // Método para actualizar la lista de reseñas
    fun updateReviews(newReviews: List<BReseña>) {
        Log.d("ReviewAdapter", "Actualizando reviews: $newReviews")
        reviews = newReviews
        notifyDataSetChanged()
    }


}
