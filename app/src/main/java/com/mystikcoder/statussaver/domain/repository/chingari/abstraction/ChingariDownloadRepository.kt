package com.mystikcoder.statussaver.domain.repository.chingari.abstraction

import com.mystikcoder.statussaver.domain.model.response.DownloadRequestResponse

interface ChingariDownloadRepository {

    suspend fun downloadChingariFile(url: String): DownloadRequestResponse
}