package br.com.newsoftwarebrasil.testeicasei.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import br.com.newsoftwarebrasil.testeicasei.R
import br.com.newsoftwarebrasil.testeicasei.adapter.VideoAdapter
import br.com.newsoftwarebrasil.testeicasei.api.YoutubeApi
import br.com.newsoftwarebrasil.testeicasei.api.YoutubeApi.KEY
import br.com.newsoftwarebrasil.testeicasei.api.response.VideoResponse
import br.com.newsoftwarebrasil.testeicasei.api.response.YoutubeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var activity: Activity
    private var youtubeResponse: ArrayList<VideoResponse>? = null
    private var videoAdapter: VideoAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var isFirstSearch: Boolean = false
    private var isResearchCompleted: Boolean = false

    private var constraint: ConstraintLayout? = null
    private var fmSearch: FrameLayout? = null
    private var fmSearchResult: FrameLayout? = null
    private var edtSearch: EditText? = null
    private var edtSearchResult: EditText? = null
    private var imgSearch: ImageView? = null
    private var tvEmptyList: TextView? = null
    private var rvVideos: RecyclerView? = null
    private var imgFirstResult: ImageView? = null
    private var imgSearchResult: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this

        setContentView(R.layout.activity_main)

        constraint = findViewById<View>(R.id.constraint) as ConstraintLayout
        fmSearch = findViewById<View>(R.id.fm_search) as FrameLayout
        fmSearchResult = findViewById<View>(R.id.fm_search_result) as FrameLayout
        edtSearch = findViewById<View>(R.id.edt_search) as EditText
        edtSearchResult = findViewById<View>(R.id.edt_search_result) as EditText
        imgFirstResult = findViewById<View>(R.id.img_first_search) as ImageView
        imgSearchResult = findViewById<View>(R.id.img_search_result) as ImageView
        imgSearch = findViewById<View>(R.id.img_search) as ImageView
        tvEmptyList = findViewById<View>(R.id.tv_empty_list) as TextView
        rvVideos = findViewById<View>(R.id.rv_videos) as RecyclerView

        isFirstSearch = true
        isResearchCompleted = true

        imgFirstResult!!.setOnClickListener { searchVideo() }
        imgSearchResult!!.setOnClickListener { searchVideo() }
    }

    override fun onStop() {
        super.onStop()
        isResearchCompleted = true
    }

    private fun searchVideo() {
        if (isResearchCompleted) {
            isResearchCompleted = false

            val parameter: String
            if (isFirstSearch) {
                isFirstSearch = false
                parameter = edtSearch!!.text.toString()
                edtSearchResult!!.setText(parameter)
            } else {
                parameter = edtSearchResult!!.text.toString()
                edtSearch!!.setText(parameter)
            }

            if (parameter.length == 0) {
                Toast.makeText(this@MainActivity, getString(R.string.empty_text_for_search), Toast.LENGTH_SHORT).show()
                isResearchCompleted = true
                closeKeyboard()
            } else {
                val callBack = YoutubeApi.getServiceClient(activity, edtSearch!!.text.toString())?.listVideos(KEY, edtSearch!!.text.toString())
                callBack?.enqueue(object : Callback<YoutubeResponse> {
                    override fun onResponse(call: Call<YoutubeResponse>, response: Response<YoutubeResponse>) {
                        if (response.errorBody() == null) {
                            youtubeResponse = response.body()!!.videos

                            if (youtubeResponse!!.size > 0) {
                                Log.i(TAG, response.body()!!.toString())
                                setViews()

                                linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                                videoAdapter = VideoAdapter(activity, youtubeResponse!!)
                                rvVideos!!.layoutManager = linearLayoutManager
                                rvVideos!!.adapter = videoAdapter
                            } else {
                                setEmptyList()
                            }
                        } else {
                            Toast.makeText(activity, getString(R.string.youtube_api_error), Toast.LENGTH_SHORT).show()
                        }
                        isResearchCompleted = true
                        closeKeyboard()
                    }

                    override fun onFailure(call: Call<YoutubeResponse>, t: Throwable) {
                        t.printStackTrace()
                        isResearchCompleted = true
                        closeKeyboard()
                    }
                })
            }
        }
    }

    private fun closeKeyboard() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                edtSearch!!.windowToken, 0)
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                edtSearchResult!!.windowToken, 0)
    }

    private fun setEmptyList() {
        fmSearch!!.visibility = View.GONE
        fmSearchResult!!.visibility = View.VISIBLE

        imgSearch!!.visibility = View.VISIBLE
        tvEmptyList!!.visibility = View.VISIBLE
        rvVideos!!.visibility = View.GONE
    }

    private fun setViews() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraint!!)
        constraintSet.connect(R.id.fm_search, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraintSet.connect(R.id.fm_search, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        constraintSet.connect(R.id.fm_search, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        constraintSet.connect(R.id.fm_search, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
        constraintSet.applyTo(constraint!!)

        fmSearch!!.visibility = View.GONE
        fmSearchResult!!.visibility = View.VISIBLE
        imgSearch!!.visibility = View.GONE
        tvEmptyList!!.visibility = View.GONE
        rvVideos!!.visibility = View.VISIBLE
    }

    companion object {
        private val TAG = MainActivity::class.java?.getName()
    }
}
