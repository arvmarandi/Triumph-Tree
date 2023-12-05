package com.example.triumphtree

import android.os.Parcel
import android.os.Parcelable

data class GoalModel(
    val name: String,
    val details: String,
    val emblem: String,
    val threshold: Int,
    var days: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(details)
        parcel.writeString(emblem)
        parcel.writeInt(threshold)
        parcel.writeInt(days)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GoalModel> {
        override fun createFromParcel(parcel: Parcel): GoalModel {
            return GoalModel(parcel)
        }

        override fun newArray(size: Int): Array<GoalModel?> {
            return arrayOfNulls(size)
        }
    }

    fun addProgress()
    {
        days++
    }
}