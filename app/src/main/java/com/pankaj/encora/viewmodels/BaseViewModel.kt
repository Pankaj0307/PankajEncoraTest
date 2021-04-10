package com.pankaj.encora.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**base class viewModel class all viewModels
 * */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    public val isProgressVisible = MutableLiveData<Boolean>();

    init {
        isProgressVisible.value = false
    }
}