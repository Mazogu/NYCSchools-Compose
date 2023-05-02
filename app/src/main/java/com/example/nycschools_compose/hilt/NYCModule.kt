package com.example.nycschools_compose.hilt

import com.example.nycschools_compose.retrofit.NYCSchoolsAPI
import com.example.nycschools_compose.retrofit.RetrofitHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NYCModule {
    @Provides
    fun providesRetrofit():NYCSchoolsAPI{
        val retrofit = RetrofitHelper.getRetrofit("https://data.cityofnewyork.us/resource/")
        return retrofit.create(NYCSchoolsAPI::class.java)
    }
}