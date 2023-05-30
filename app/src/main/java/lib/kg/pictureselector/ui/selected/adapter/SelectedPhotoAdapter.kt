package lib.kg.pictureselector.ui.selected.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lib.kg.pictureselector.databinding.ItemPhotoBinding
import lib.kg.pictureselector.loadImage

class SelectedPhotoAdapter: RecyclerView.Adapter<SelectedPhotoAdapter.SelectedPhotoViewHolder>() {
    private val imageList: ArrayList<String> = arrayListOf()

    fun addImage(imageItem: ArrayList<String>) {
        imageList.addAll(imageItem)
    }

    fun clearImage() {
        imageList.clear()
    }

    inner class SelectedPhotoViewHolder(private var binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(img: String) {
            binding.imgPhoto.loadImage(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedPhotoViewHolder {
        return SelectedPhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: SelectedPhotoViewHolder, position: Int) {
        holder.bind(imageList[position])
    }
}