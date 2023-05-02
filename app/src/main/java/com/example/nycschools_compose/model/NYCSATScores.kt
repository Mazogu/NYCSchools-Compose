package com.example.nycschools_compose.model

import com.google.gson.annotations.SerializedName

data class NYCSATScores(
    @SerializedName("school_name")
    val schoolName:String,
    @SerializedName("num_of_sat_test_takers")
    val numTestTakers:String,
    @SerializedName("sat_critical_reading_avg_score")
    val avgCriticalReadingScore:String,
    @SerializedName("sat_math_avg_score")
    val avgMathScore:String,
    @SerializedName("sat_writing_avg_score")
    val avgWritingScore:String
)