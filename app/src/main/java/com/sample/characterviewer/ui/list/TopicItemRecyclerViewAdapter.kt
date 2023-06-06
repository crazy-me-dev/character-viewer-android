package com.sample.characterviewer.ui.list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sample.characterviewer.R
import com.sample.characterviewer.data.model.Topic
import com.sample.characterviewer.databinding.ItemListContentBinding
import com.sample.characterviewer.placeholder.PlaceholderContent
import com.sample.characterviewer.ui.details.DetailFragment

class TopicItemRecyclerViewAdapter(
    private val itemDetailFragmentContainer: View?
) : RecyclerView.Adapter<TopicItemRecyclerViewAdapter.ViewHolder>() {

    private var data: ArrayList<Topic>? = null

    fun setData(list: ArrayList<Topic>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)
        holder.characterNameView.text = item?.characterName
        holder.characterDescriptionView.text = item?.characterDescription

        with(holder.itemView) {
            tag = item
            setOnClickListener { itemView ->
                val item = itemView.tag as Topic
                val bundle = Bundle()
                bundle.putSerializable(
                    DetailFragment.ARG_ITEM,
                    item
                )
                if (itemDetailFragmentContainer != null) {
                    itemDetailFragmentContainer.findNavController()
                        .navigate(R.id.fragment_item_detail, bundle)
                } else {
                    itemView.findNavController().navigate(R.id.show_item_detail, bundle)
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                /**
                 * Context click listener to handle Right click events
                 * from mice and trackpad input to provide a more native
                 * experience on larger screen devices
                 */
                setOnContextClickListener { v ->
                    val item = v.tag as PlaceholderContent.PlaceholderItem
                    Toast.makeText(
                        v.context,
                        "Context click of item " + item.id,
                        Toast.LENGTH_LONG
                    ).show()
                    true
                }
            }
        }
    }

    override fun getItemCount() = data?.size ?: 0

    inner class ViewHolder(binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val characterNameView: TextView = binding.idCharacterName
        val characterDescriptionView: TextView = binding.idCharacterDescription
    }

}