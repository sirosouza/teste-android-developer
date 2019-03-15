package br.com.newsoftwarebrasil.testeicasei.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.com.newsoftwarebrasil.testeicasei.R
import br.com.newsoftwarebrasil.testeicasei.adapter.VideoAdapter.Companion.INTENT_VIDEO
import br.com.newsoftwarebrasil.testeicasei.api.response.VideoResponse
import com.squareup.picasso.Picasso

class ShowVideoActivity : AppCompatActivity() {
    private var activity: Activity? = null
    private var videoResponse: VideoResponse? = null

    private var imgBack: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvDescription: TextView? = null
    private var imgThumbnail: ImageView? = null
    private var tvChannelName: TextView? = null
    private var tvLike: TextView? = null
    private var tvDislike: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        setContentView(R.layout.activity_show_video)

        imgBack = findViewById<View>(R.id.img_back) as ImageView
        tvTitle = findViewById<View>(R.id.tv_title) as TextView
        tvDescription = findViewById<View>(R.id.tv_description) as TextView
        imgThumbnail = findViewById<View>(R.id.img_thumbnail) as ImageView
        tvChannelName = findViewById<View>(R.id.tv_channel_name) as TextView
        tvLike = findViewById<View>(R.id.tv_like) as TextView
        tvDislike = findViewById<View>(R.id.tv_dislike) as TextView

        imgBack!!.setOnClickListener { finish() }

        if (intent.hasExtra(INTENT_VIDEO)) {
            videoResponse = intent.getSerializableExtra(INTENT_VIDEO) as VideoResponse
        }

        tvTitle!!.text = videoResponse!!.snippet?.title
        tvDescription!!.text = videoResponse!!.snippet?.description


        var width = videoResponse!!.snippet?.thumbnails?.thumbnailHigh!!.width
        var height = videoResponse!!.snippet?.thumbnails?.thumbnailHigh!!.height

        if (width == 0) {
            width = 320
        }

        if (height == 0) {
            height = 180
        }

        Picasso.get() //180hx320w
                .load(videoResponse!!.snippet?.thumbnails?.thumbnailMedium?.url)
                .resize(width, height)
                .into(imgThumbnail)

        if (videoResponse!!.snippet?.channelTitle != null)
            tvChannelName!!.text = videoResponse!!.snippet?.channelTitle
        tvLike!!.text = "9.999"
        tvDislike!!.text = "9.999"
    }
}
