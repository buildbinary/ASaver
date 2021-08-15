package com.mystikcoder.statussaver.domain.repository.mitron.implementation

import com.mystikcoder.statussaver.domain.model.response.DownloadRequestResponse
import com.mystikcoder.statussaver.domain.repository.mitron.abstraction.MitronDownloadRepository
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MitronDownloadRepositoryImpl: MitronDownloadRepository {

    override suspend fun downloadMitronFile(url: String): DownloadRequestResponse {

        kotlin.runCatching {
            val mitronDocument: Document = Jsoup.connect(url).get()
            val html =
                mitronDocument.select("script[id=\"__NEXT_DATA__\"]")
                    .last()
                    .html()

            return if (!html.isNullOrEmpty()) {

                val videoUrl =
                    (JSONObject(html).getJSONObject("props").getJSONObject("pageProps")
                        .getJSONObject("video").getString("videoUrl")).toString()

                DownloadRequestResponse(isSuccess = true , downloadLink = videoUrl)
            }else{
                DownloadRequestResponse(errorMessage = "No data found")
            }
        }.getOrElse {
            return DownloadRequestResponse(errorMessage = it.message ?: "Something Went Wrong")
        }
    }
}