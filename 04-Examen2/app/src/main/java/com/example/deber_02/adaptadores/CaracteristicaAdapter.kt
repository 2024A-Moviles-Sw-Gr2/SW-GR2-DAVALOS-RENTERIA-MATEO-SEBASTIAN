package com.example.deber_02.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.deber_02.modelos.BCaracteristica

class CaracteristicaAdapter(context: Context, caracteristicas: List<BCaracteristica>) : ArrayAdapter<BCaracteristica>(context, 0, caracteristicas) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val caracteristica = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)

        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)

        text1.text = "ID: ${caracteristica?.id}"
        text2.text = "Titulo: ${caracteristica?.titulo}\nDescripción: ${caracteristica?.descripcion}"

        view.tag = caracteristica?.id

        return view
    }
}