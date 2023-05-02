package com.example.nycschools_compose.retrofit

import com.example.nycschools_compose.model.NYCSATScores
import com.example.nycschools_compose.model.NYCSchool
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NYCSchoolsAPI {
    @GET("s3k6-pzi2.json")
    suspend fun getNYCSchools():Response<List<NYCSchool>>

    @GET("f9bf-2cp4.json")
    suspend fun getNYCSchoolSAT(@Query("dbn") schoolId:String):Response<List<NYCSATScores>>
}