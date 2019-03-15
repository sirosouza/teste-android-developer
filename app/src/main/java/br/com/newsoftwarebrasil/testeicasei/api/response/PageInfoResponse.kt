package br.com.newsoftwarebrasil.testeicasei.api.response

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class PageInfoResponse : Serializable {
    @SerializedName("totalResults")
    var integer: Int? = null
    @SerializedName("resultsPerPage")
    var resultsPerPage: Int? = null
}
