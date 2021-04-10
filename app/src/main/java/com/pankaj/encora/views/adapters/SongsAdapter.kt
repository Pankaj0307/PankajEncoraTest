package com.pankaj.encora.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pankaj.encora.R
import com.pankaj.encora.database.SongsEntity
import com.pankaj.encora.databinding.ListItemSongsBinding
import java.lang.ref.WeakReference

class SongsAdapter(
    private val entryList: ArrayList<SongsEntity>,
    private val listener: OnEntryClickListener
) : RecyclerView.Adapter<SongsAdapter.EntryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val binding: ListItemSongsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_songs, parent, false
        )
        return EntryViewHolder(binding, listener)
    }

    inner class EntryViewHolder(val binding: ListItemSongsBinding, listener: OnEntryClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private val listenerRef: WeakReference<OnEntryClickListener> = WeakReference(listener);
        fun bind(item: SongsEntity) {
            binding.viewModel = item
            binding.cardView.setOnClickListener(this)
        }

        //setting click listener for item
        override fun onClick(view: View?) {
            listenerRef.get()?.onClickEntry(entryList[adapterPosition])
        }
    }

    override fun getItemCount(): Int = entryList.size

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) =
        holder.bind(entryList[position])
}

//interface for getting on click listener
public interface OnEntryClickListener {
    fun onClickEntry(entry: SongsEntity?)
}