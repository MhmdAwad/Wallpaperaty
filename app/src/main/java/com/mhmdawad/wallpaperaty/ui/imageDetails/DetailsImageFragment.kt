package com.mhmdawad.wallpaperaty.ui.imageDetails

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
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
        val image: UnsplashPhoto = args.image
        detailImage.apply {
            transitionName = image.urls.small
            loadImage(image.urls.regular)
        }
    }
}