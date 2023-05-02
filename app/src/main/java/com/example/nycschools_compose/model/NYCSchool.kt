package com.example.nycschools_compose.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NYCSchool(
    @SerializedName("dbn")
    val schoolId:String,
    @SerializedName("school_name")
    val schoolName:String,
    @SerializedName("school_email")
    val email:String?,
    @SerializedName("phone_number")
    val phone:String?
):Parcelable