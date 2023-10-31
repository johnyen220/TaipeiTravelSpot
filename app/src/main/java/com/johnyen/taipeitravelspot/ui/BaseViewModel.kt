package com.johnyen.taipeitravelspot.ui.taipeiOpenData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cbes.ezreturn.utils.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    val error = MutableLiveData<Exception>()
    val indicator = MutableLiveData<Boolean>()

    protected fun <T> request(execute: suspend CoroutineScope.() -> T) {
        val handler = CoroutineExceptionHandler { coroutineContext, exception ->
            Logger.logE("Error occur on $coroutineContext")
            Logger.logE("CoroutineExceptionHandler got $exception", exception)
        }

        viewModelScope.launch(handler) {
            try {
                indicator.postValue(true)
                execute()
            } catch (e: Exception) {
                error.postValue(e)
            } finally {
                indicator.postValue(false)
            }
        }
    }

}