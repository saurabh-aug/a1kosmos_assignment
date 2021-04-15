package com.sample.a1kosmos.view.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sample.a1kosmos.data.repository.UserRepository
import com.sampleweather.data.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {


    fun getUserData(page: Int, per_page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(

                Resource.success(
                    data = repository.getUserDataServer(page, per_page)
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}