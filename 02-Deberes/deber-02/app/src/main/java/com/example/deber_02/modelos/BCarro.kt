package com.example.deber_02.modelos

import android.os.Parcel
import android.os.Parcelable

class BCarro(
    var marca: String,
    var modelo: String,
    var year: Int,
    var precio: Double,
    var estado: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(marca)
        parcel.writeString(modelo)
        parcel.writeInt(year)
        parcel.writeDouble(precio)
        parcel.writeString(estado)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BCarro> {
        override fun createFromParcel(parcel: Parcel): BCarro {
            return BCarro(parcel)
        }

        override fun newArray(size: Int): Array<BCarro?> {
            return arrayOfNulls(size)
        }
    }
}
