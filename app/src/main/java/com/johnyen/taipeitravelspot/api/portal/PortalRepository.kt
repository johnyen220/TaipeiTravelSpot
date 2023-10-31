package com.johnyen.taipeitravelspot.api.portal

import com.cbes.ezreturn.di.IoDispatcher
import com.johnyen.taipeitravelspot.api.portal.response.TaipeiDataResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PortalRepository @Inject constructor(
    private val taipeiOpenDataService: TaipeiOpenDataService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    private suspend fun <T> request(execute: suspend CoroutineScope.() -> T): T {
        return withContext(ioDispatcher) {
            execute()
        }
    }

    suspend fun fetchTaiPeiOpenData(): TaipeiDataResponse {
        return taipeiOpenDataService.getAttractionAll()
    }
}