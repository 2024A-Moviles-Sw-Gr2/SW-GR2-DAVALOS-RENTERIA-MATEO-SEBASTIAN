package com.example.deber_02.modelos

import android.os.Parcel
import android.os.Parcelable
import java.util.*
class BPlaneta(
    var id: Int,
    var nombre: String,
    var diametro: Double,
    var tieneSatelites: Boolean,
    var fechaDescubrimiento: Date,
    var caracteristicas: MutableList<BCaracteristica> = mutableListOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        Date(parcel.readLong()),
        mutableListOf<BCaracteristica>().apply {
            parcel.readList(this as List<*>, BCaracteristica::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeDouble(diametro)
        parcel.writeByte(if (tieneSatelites) 1 else 0)
        parcel.writeLong(fechaDescubrimiento.time)
        parcel.writeList(caracteristicas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BPlaneta> {
        override fun createFromParcel(parcel: Parcel): BPlaneta {
            return BPlaneta(parcel)
        }

        override fun newArray(size: Int): Array<BPlaneta?> {
            return arrayOfNulls(size)
        }
    }
}