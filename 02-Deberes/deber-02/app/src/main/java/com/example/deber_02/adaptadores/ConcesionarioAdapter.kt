package com.example.deber_02.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.deber_02.modelos.BConcesionario

class ConcesionarioAdapter(context: Context, concesionarios: List<BConcesionario>) : ArrayAdapter<BConcesionario>(context, 0, concesionarios) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val concesionario = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)

        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)

        text1.text = concesionario?.nombre ?: "Nombre desconocido"
        text2.text = "Ubicación: ${concesionario?.ubicacion}"

        view.tag = concesionario?.nombre

        return view
    }
}
