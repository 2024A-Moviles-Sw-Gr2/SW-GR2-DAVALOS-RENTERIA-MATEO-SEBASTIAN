package com.example.deber_02.modelos

import android.os.Parcel
import android.os.Parcelable

class BCaracteristica(
    var id: Int,
    var titulo: String,
    var descripcion: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(titulo)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BCaracteristica> {
        override fun createFromParcel(parcel: Parcel): BCaracteristica {
            return BCaracteristica(parcel)
        }

        override fun newArray(size: Int): Array<BCaracteristica?> {
            return arrayOfNulls(size)
        }
    }
}