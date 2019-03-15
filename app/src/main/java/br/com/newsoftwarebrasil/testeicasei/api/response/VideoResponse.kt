package br.com.newsoftwarebrasil.testeicasei.api.response

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class VideoResponse : Serializable {
    @SerializedName("kind")
    val kind: String? = null
    @SerializedName("etag")
    val etag: String? = null
    @SerializedName("id")
    val youtubeId: YoutubeId? = null
    @SerializedName("snippet")
    val snippet: Snippet? = null

    inner class YoutubeId : Serializable {
        @SerializedName("kind")
        val kind: String? = null
        @SerializedName("videoId")
        val videoId: String? = null
    }

    inner class Snippet : Serializable {
        @SerializedName("publishedAt")
        val publishedAt: String? = null
        @SerializedName("channelId")
        val channelId: String? = null
        @SerializedName("title")
        val title: String? = null
        @SerializedName("description")
        val description: String? = null
        @SerializedName("thumbnails")
        val thumbnails: ThumbnailData? = null
        @SerializedName("channelTitle")
        val channelTitle: String? = null
        @SerializedName("liveBroadcastContent")
        val liveBroadcastContent: String? = null
    }

    inner class ThumbnailData : Serializable {
        @SerializedName("default")
        var thumbnailDefault: ThumbnailDefaultData? = null
            internal set
        @SerializedName("medium")
        var thumbnailMedium: ThumbnailDefaultData? = null
            internal set
        @SerializedName("high")
        var thumbnailHigh: ThumbnailDefaultData? = null
            internal set
    }

    inner class ThumbnailDefaultData : Serializable {
        @SerializedName("url")
        val url: String? = null
        @SerializedName("width")
        val width: Int = 0
        @SerializedName("height")
        val height: Int = 0
    }
}
