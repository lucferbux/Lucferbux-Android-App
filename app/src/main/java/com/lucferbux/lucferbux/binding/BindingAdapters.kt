package com.lucferbux.lucferbux.binding

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.text.SimpleDateFormat
import java.util.*


/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {

    /**
     * Makes the View [View.INVISIBLE] unless the condition is met.
     */
    @Suppress("unused")
    @BindingAdapter("invisibleUnless")
    @JvmStatic
    fun invisibleUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    /**
     * Makes the View [View.GONE] unless the condition is met.
     */
    @Suppress("unused")
    @BindingAdapter("goneUnless")
    @JvmStatic
    fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * Transform the timestamp into date
     */
    @Suppress("unused")
    @BindingAdapter("timestampToDate")
    @JvmStatic
    fun timetsampToDate(view: TextView, timestamp: Date?) {
        timestamp?.let {
            val dateFormat = SimpleDateFormat
                .getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
            view.text = dateFormat.format(timestamp)
        }

    }

    /**
     * Set url
     */
    @Suppress("unused")
    @BindingAdapter("onButtonClick")
    @JvmStatic
    fun onButtonClick(view: Button, url: String) {
        view.visibility = if(url.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        view.setOnClickListener {
            var urlAux = url
            if (!url.startsWith("http://") && !url.startsWith("https://")){
                urlAux = "https://" + url
            }
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(urlAux))
            val context = view.context
            if (browserIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(browserIntent);
            } else {
                Toast.makeText(context, "No Web browser found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(imageView: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(imageView.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }


}
