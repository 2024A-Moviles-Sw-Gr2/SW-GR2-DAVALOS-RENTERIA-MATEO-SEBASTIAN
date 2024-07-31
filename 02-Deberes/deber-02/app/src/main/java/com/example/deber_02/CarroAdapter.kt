package com.example.deber_02

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.deber_02.modelos.BCarro

class CarroAdapter(context: Context, carros: List<BCarro>) : ArrayAdapter<BCarro>(context, 0, carros) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val carro = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)

        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)

        text1.text = carro?.marca ?: "Marca desconocida"
        text2.text = "Modelo: ${carro?.modelo} - Año: ${carro?.year}"

        view.tag = carro?.marca

        return view
    }
}
