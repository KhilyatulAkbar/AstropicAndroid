package com.nurmuhammadkhilyatulakbar.astropic

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat


class DetailActivity : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if(intent.getSerializableExtra("apod") != null){
            val apod:ApodModel = intent.getSerializableExtra("apod") as ApodModel

            tvTitle.text = apod.title
            tvDeskripsi.text = apod.description
            tvCopyright.text = "Copyright - " + apod.copyright
            tvUrl.text = apod.apodSite
            if(apod.mediaType.equals("image")){
                ivImage.load(apod.url)
                ytView.visibility = View.GONE
                ivImage.visibility = View.VISIBLE
            }
            else if(apod.mediaType.equals("video")){
                if(apod.url!!.contains("youtube")){
                    ivImage.load(apod.thumbnailUrl)
                    ytView.visibility = View.VISIBLE
                    ivImage.visibility = View.GONE
                    lifecycle.addObserver(ytView)

                    ytView.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            val videoId = apod.url!!.substring(apod.url.lastIndexOf("/")+1, apod.url.indexOf("?"))
                            Log.d("cekurl", videoId)
                            youTubePlayer.loadVideo(videoId, 0f)
                        }
                    })
                }
                else{
                    ivImage.load(apod.thumbnailUrl)
                    ytView.visibility = View.GONE
                    ivImage.visibility = View.VISIBLE
                }


            }

            val parser = SimpleDateFormat("yyyy-MM-dd")
            val formatter = SimpleDateFormat("d MMMM yyyy")
            val output: String = formatter.format(parser.parse(apod.date))
            tvDate.text = output
        }
        else {
            Toast.makeText(this, "Date not found", Toast.LENGTH_SHORT).show()
            finish()
        }



    }
}