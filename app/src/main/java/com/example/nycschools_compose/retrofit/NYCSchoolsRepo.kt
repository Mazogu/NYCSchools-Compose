package com.example.nycschools_compose.retrofit

import com.example.nycschools_compose.model.NYCSATScores
import com.example.nycschools_compose.model.NYCSchool
import retrofit2.Response
import javax.inject.Inject

class NYCSchoolsRepo @Inject constructor(private val api:NYCSchoolsAPI) {
    suspend fun getNYCSchools():Response<List<NYCSchool>> = api.getNYCSchools()

    suspend fun getNYCSchoolSATScores(schoolId:String):Response<List<NYCSATScores>> = api.getNYCSchoolSAT(schoolId)
}