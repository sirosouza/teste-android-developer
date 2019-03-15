package br.com.newsoftwarebrasil.testeicasei.api.response

import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.util.ArrayList

class YoutubeResponse : Serializable {
    @SerializedName("kind")
    var kind: String? = null
    @SerializedName("etag")
    var etag: String? = null
    @SerializedName("nextPageToken")
    var nextPageToken: String? = null
    @SerializedName("prevPageToken")
    var prevPageToken: String? = null
    @SerializedName("pageInfoResponse")
    var pageInfoResponse: PageInfoResponse? = null
    @SerializedName("items")
    var videos: ArrayList<VideoResponse>? = null
}
