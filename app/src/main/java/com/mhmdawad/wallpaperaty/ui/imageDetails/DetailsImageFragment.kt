package com.mhmdawad.wallpaperaty.ui.imageDetails

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mhmdawad.wallpaperaty.R
import com.mhmdawad.wallpaperaty.models.UnsplashPhoto
import com.mhmdawad.wallpaperaty.utils.loadImage
import kotlinx.android.synthetic.main.fragment_details_image.*
import kotlinx.android.synthetic.main.main_image_layout_rv.view.*

class DetailsImageFragment : Fragment(R.layout.fragment_details_image) {

    private val args by navArgs<DetailsImageFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        detailImage.transitionName = resources.getString(R.string.transitionName)

        val image: UnsplashPhoto = args.image
        detailImage.apply {
            transitionName = image.urls.small
            loadImage(image.urls.regular)
        }
    }
}