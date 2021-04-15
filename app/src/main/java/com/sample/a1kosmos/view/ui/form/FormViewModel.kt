package com.sample.a1kosmos.view.ui.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.a1kosmos.data.model.form.UserForm
import com.sample.a1kosmos.data.repository.FormRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FormViewModel @Inject constructor(private val repository: FormRepository) : ViewModel() {


    fun saveUsers(user: UserForm) {
        viewModelScope.launch {
            repository.saveUsers(user)
        }
    }

    val user = repository.user


}