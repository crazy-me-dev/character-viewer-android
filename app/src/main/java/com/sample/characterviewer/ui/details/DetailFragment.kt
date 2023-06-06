package com.sample.characterviewer.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sample.characterviewer.R
import com.sample.characterviewer.data.model.Topic
import com.sample.characterviewer.databinding.FragmentItemDetailBinding

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class DetailFragment : Fragment() {

    /**
     * The placeholder content this fragment is presenting.
     */
    private var item: Topic? = null

    lateinit var characterTitleTextView: TextView
    lateinit var characterDescriptionTextView: TextView
    private var characterIconView: AppCompatImageView? = null
    private var characterIconLoaderView: ProgressBar? = null
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentItemDetailBinding? = null

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM)) {
                // Load the placeholder content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = it.getSerializable(ARG_ITEM) as Topic
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

//        toolbarLayout = binding.toolbarLayout
        characterTitleTextView = binding.characterTitle
        characterDescriptionTextView = binding.characterDescription
        characterIconView = binding.characterIcon
        characterIconLoaderView = binding.iconLoader

        updateContent()

        return rootView
    }

    private fun updateContent() {
//        toolbarLayout?.title = item?.characterName

        // Show the placeholder content as text in a TextView.
        item?.let {
            characterTitleTextView.text = it.characterName
            characterDescriptionTextView.text = it.characterDescription
            if (characterIconView != null && characterIconLoaderView != null) {
                if (it.Icon.iconURLString == "") {
                    characterIconLoaderView?.visibility = View.GONE
                    characterIconView?.setImageResource(R.mipmap.img_placeholder)
                } else {
                    characterIconLoaderView?.visibility = View.VISIBLE
                    Glide.with(this)
                        .load(it.Icon.iconURLString)
                        .listener(object: RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                characterIconLoaderView?.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                characterIconLoaderView?.visibility = View.GONE
                                return false
                            }
                        })
                        .into(characterIconView!!)
                }

            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM = "topic"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}