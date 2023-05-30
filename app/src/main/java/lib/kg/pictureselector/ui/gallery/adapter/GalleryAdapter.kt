package lib.kg.pictureselector.ui.gallery.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import lib.kg.pictureselector.R
import lib.kg.pictureselector.databinding.ItemPhotoBinding
import lib.kg.pictureselector.loadImage

class GalleryAdapter(var onItemClick: ((Uri) -> Unit)? = null): RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    private val imageList: ArrayList<Uri> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addImage(imageItem: Uri) {
        imageList.add(imageItem)
        notifyDataSetChanged()
    }

    inner class GalleryViewHolder(private var binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(img: Uri) {
            binding.imgPhoto.loadImage(img.toString())
            itemView.setOnClickListener { mainIt ->
                onItemClick?.invoke(imageList[adapterPosition])
                if (!binding.galochka.isVisible){
                    mainIt.alpha = 0.5f
                    binding.mainOfItem.setBackgroundResource(R.drawable.bg_photo)
                    binding.galochka.isVisible = true
                } else{
                    binding.mainOfItem.setBackgroundResource(R.drawable.bg_photo2)
                    mainIt.alpha = 1.0f
                    binding.galochka.isVisible = false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return minOf(imageList.size, 100)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(imageList[position])
    }
}