package com.johnyen.taipeitravelspot.ui.taipeiOpenData


import androidx.lifecycle.MutableLiveData
import com.johnyen.taipeitravelspot.api.portal.PortalRepository
import com.johnyen.taipeitravelspot.api.portal.response.TaipeiDataResponse
import com.johnyen.taipeitravelspot.api.portal.response.model.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaipeiOpenDataViewModel @Inject constructor(
    private val portalRepository: PortalRepository,
) : BaseViewModel() {
    val fetchTaipeiOpenDataResult = MutableLiveData<TaipeiDataResponse>()
    val fetchWebViewUrlResult = MutableLiveData<String>()
    val fetchTaipeiSpotDetailResult = MutableLiveData<Data>()
    suspend fun fetchAttractionAll() {
        val taipeiOpenData = portalRepository.fetchTaiPeiOpenData()
        fetchTaipeiOpenDataResult.postValue(taipeiOpenData)
    }

}