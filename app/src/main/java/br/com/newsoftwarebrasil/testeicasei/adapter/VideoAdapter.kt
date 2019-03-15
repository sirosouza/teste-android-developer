package br.com.newsoftwarebrasil.testeicasei.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import br.com.newsoftwarebrasil.testeicasei.R
import br.com.newsoftwarebrasil.testeicasei.activity.ShowVideoActivity
import br.com.newsoftwarebrasil.testeicasei.api.response.VideoResponse
import com.squareup.picasso.Picasso
import java.util.*

class VideoAdapter(private val activity: Activity, private val videoResponses: ArrayList<VideoResponse>?) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    private val visibleView: BooleanArray

    init {
        visibleView = BooleanArray(videoResponses!!.size)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VideoAdapter.ViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.rv_post_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.imgVideo.setOnClickListener {
            val intent = Intent(activity, ShowVideoActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(INTENT_VIDEO, videoResponses!![position])
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }


        var width = videoResponses!![position].snippet?.thumbnails?.thumbnailMedium!!.width
        var height = videoResponses[position].snippet?.thumbnails?.thumbnailMedium!!.height

        if (width == 0) {
            width = 320
        }

        if (height == 0) {
            height = 180
        }

        Picasso.get() //180hx320w
                .load(videoResponses[position].snippet?.thumbnails?.thumbnailMedium?.url)
                .resize(width, height)
                .into(viewHolder.imgVideo)

        viewHolder.tvTitle.text = videoResponses[position].snippet?.title
        viewHolder.tvChannel.text = videoResponses[position].snippet?.channelTitle
        viewHolder.tvDescription.text = videoResponses[position].snippet?.description

        viewHolder.btnDetails.setOnClickListener {
            if (visibleView[position]) {
                viewHolder.tvDescription.visibility = View.GONE
                visibleView[position] = false
            } else {
                viewHolder.tvDescription.visibility = View.VISIBLE
                visibleView[position] = true
            }
        }
    }

    override fun getItemCount(): Int {
        return videoResponses?.size ?: 0
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgVideo: ImageView
        val tvTitle: TextView
        val tvChannel: TextView
        val tvDescription: TextView
        val btnDetails: Button

        init {
            imgVideo = itemView.findViewById<View>(R.id.img_video) as ImageView
            tvTitle = itemView.findViewById<View>(R.id.tv_title) as TextView
            tvChannel = itemView.findViewById<View>(R.id.tv_channel) as TextView
            tvDescription = itemView.findViewById<View>(R.id.tv_description) as TextView
            btnDetails = itemView.findViewById<View>(R.id.btn_details) as Button
        }
    }

    companion object {
        val INTENT_VIDEO = "intent_video"
    }
}
