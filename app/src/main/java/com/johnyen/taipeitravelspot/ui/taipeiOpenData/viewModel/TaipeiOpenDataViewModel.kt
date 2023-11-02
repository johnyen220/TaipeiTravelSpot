package com.johnyen.taipeitravelspot.ui.taipeiOpenData.viewModel


import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import com.johnyen.taipeitravelspot.api.portal.PortalRepository
import com.johnyen.taipeitravelspot.api.portal.response.TaipeiDataResponse
import com.johnyen.taipeitravelspot.api.portal.response.model.Data
import com.johnyen.taipeitravelspot.ui.taipeiOpenData.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaipeiOpenDataViewModel @Inject constructor(
    private val portalRepository: PortalRepository,
) : BaseViewModel() {
    var myDrawerLayout:DrawerLayout? = null
    var changeLanguageImage:ImageView? = null
    val fetchTaipeiOpenDataResult = MutableLiveData<TaipeiDataResponse>()
    val fetchWebViewUrlResult = MutableLiveData<String>()
    val fetchTaipeiSpotDetailResult = MutableLiveData<Data>()
    val lockRightDrawerLiveData = MutableLiveData<Boolean>()
    val openDrawerLiveData = MutableLiveData<Boolean>()
    val fetchChangeLanguageResult = MutableLiveData<String>()
    suspend fun fetchAttractionAllByLang(lang:String) {
        val taipeiOpenData = portalRepository.fetchTaiPeiOpenData(lang)
        fetchTaipeiOpenDataResult.postValue(taipeiOpenData)
    }

}