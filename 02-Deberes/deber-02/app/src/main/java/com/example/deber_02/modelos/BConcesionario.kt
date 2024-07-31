package com.example.deber_02.modelos

import android.os.Parcel
import android.os.Parcelable
import java.util.*
data class BConcesionario(
    val nombre: String,
    val ubicacion: String,
    val isOpen: Boolean,
    val numeroEmpleados: Int,
    val listaCarros: MutableList<BCarro> = mutableListOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        mutableListOf<BCarro>().apply {
            parcel.readList(this as List<*>, BCarro::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(ubicacion)
        parcel.writeByte(if (isOpen) 1 else 0)
        parcel.writeInt(numeroEmpleados)
        parcel.writeList(listaCarros)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BConcesionario> {
        override fun createFromParcel(parcel: Parcel): BConcesionario {
            return BConcesionario(parcel)
        }

        override fun newArray(size: Int): Array<BConcesionario?> {
            return arrayOfNulls(size)
        }
    }
}