package com.example.nycschools_compose.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschools_compose.model.NYCSATScores
import com.example.nycschools_compose.model.NYCSchool
import com.example.nycschools_compose.retrofit.NYCSchoolsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NYCViewModel @Inject constructor(private val repo:NYCSchoolsRepo) :ViewModel(){
    private val _schoolList = MutableLiveData<List<NYCSchool>>()
    val schoolList get() = _schoolList as LiveData<List<NYCSchool>>
    private val _satScores = MutableLiveData<NYCSATScores>()
    val satScores get() = _satScores as LiveData<NYCSATScores>

    fun getSchools(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val schools = repo.getNYCSchools()
                if (schools.isSuccessful)
                    _schoolList.postValue(schools.body())
            }
            catch (e:Exception){
                Timber.e(e)
            }
        }
    }

    fun getScores(schoolId:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val scores = repo.getNYCSchoolSATScores(schoolId)
                if (scores.isSuccessful && scores.body()?.isNotEmpty() == true)
                    _satScores.postValue(scores.body()!![0])
            }
            catch (e:Exception){
                Timber.e(e)
            }
        }
    }


}