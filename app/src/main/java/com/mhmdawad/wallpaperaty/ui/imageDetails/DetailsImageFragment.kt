package com.mhmdawad.wallpaperaty.ui.imageDetails

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mhmdawad.wallpaperaty.R
import com.mhmdawad.wallpaperaty.models.UnsplashPhoto
import com.mhmdawad.wallpaperaty.utils.addNoLimitFlag
import com.mhmdawad.wallpaperaty.utils.loadImage
import kotlinx.android.synthetic.main.fragment_details_image.*

class DetailsImageFragment : Fragment(R.layout.fragment_details_image) {

    private val args by navArgs<DetailsImageFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNoLimitFlag()
        fabListener()
        val image: UnsplashPhoto = args.image
        detailImage.apply {
            transitionName = image.urls.small
            loadImage(image.urls.regular)
        }


    }

    private fun fabListener() {
        detailImageFAB.setOnClickListener {
            if(!downloadContainer.isVisible) {
                detailImageFAB.setImageResource(R.drawable.ic_close_24)
                downloadContainer.isVisible = true
                previewContainer.isVisible = true
            }else{
                detailImageFAB.setImageResource(R.drawable.ic_add_24)
                downloadContainer.isVisible = false
                previewContainer.isVisible = false
            }
        }
    }
}