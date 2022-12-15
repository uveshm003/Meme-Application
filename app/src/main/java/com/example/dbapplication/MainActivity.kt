package com.example.dbapplication

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    private lateinit var memeImage: ImageView
    private lateinit var nextBtn: Button
    private lateinit var shareBtn: Button
    private lateinit var captiontv: TextView
    private lateinit var bar: ProgressBar
    private var imageurl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memeImage = findViewById(R.id.memeImage)
        nextBtn = findViewById(R.id.nextButton)
        shareBtn = findViewById(R.id.shareButton)
        captiontv = findViewById(R.id.textView)
        bar = findViewById(R.id.progress_circular)
        memeLoad()
        nextBtn.setOnClickListener {
            memeLoad()
        }
        shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT, "Hey , Check out this cool meme I found on Reddit  $imageurl"
            )
            val chooser = Intent.createChooser(intent,"Share this meme using..")
            startActivity(chooser)
        }


    }

    private fun memeLoad() {
        val url = "https://meme-api.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest =
            JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, { response ->
                val uri = response.getString("url")
                val caption = response.getString("title")
                captiontv.text = caption
                imageurl = uri
                Glide.with(this).load(uri).listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        bar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        bar.visibility = View.GONE
                        return false
                    }

                }).into(memeImage)
            }, { })

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

}