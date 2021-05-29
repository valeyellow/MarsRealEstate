package com.example.marsrealestate.data

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class MarsData(
    val id: String?,
    val img_src: String?,
    val price: Int,
    val type: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(img_src)
        parcel.writeInt(price)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MarsData> {
        override fun createFromParcel(parcel: Parcel): MarsData {
            return MarsData(parcel)
        }

        override fun newArray(size: Int): Array<MarsData?> {
            return arrayOfNulls(size)
        }
    }
}