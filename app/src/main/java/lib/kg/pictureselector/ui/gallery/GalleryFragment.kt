package lib.kg.pictureselector.ui.gallery


import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import lib.kg.pictureselector.R
import lib.kg.pictureselector.databinding.FragmentGalleryBinding
import lib.kg.pictureselector.showToast
import lib.kg.pictureselector.ui.gallery.adapter.GalleryAdapter

class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding
    private var pickedImageList: ArrayList<String> = arrayListOf()
    private val adapter = GalleryAdapter()

    companion object {
        const val REQUEST_PERMISSION_CODE = 1
        const val KEY_FOR_LIST = "KEEYYY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListeners()

        if (checkPermission()) {
            loadImages()
        } else {
            requestPermission()
        }
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    private fun initListeners() {
        adapter.onItemClick = {mainIt ->
            if (pickedImageList.contains(mainIt.toString())){
                pickedImageList.removeIf{it == mainIt.toString()}
            } else {
                pickedImageList.add(mainIt.toString())
            }
            binding.cvChosenPic.isVisible = pickedImageList.isNotEmpty()
            binding.tvCounter.setText("Выбрано ${pickedImageList.size} фоток")
        }
        binding.btnReady.setOnClickListener {
            findNavController().navigate(R.id.action_galleryFragment_to_selectedPhotosFragment, bundleOf(KEY_FOR_LIST to pickedImageList))
        }
    }


    private fun initAdapter() {
        setUpRV()
        binding.recyclerView.adapter = adapter
    }

    private fun checkPermission(): Boolean {
        val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        val result = ContextCompat.checkSelfPermission(requireContext(), permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(permission),
            REQUEST_PERMISSION_CODE
        )
    }

    @SuppressLint("Range")
    private fun loadImages() {
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME)
        val sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " DESC"
        val cursor = requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        cursor?.use {
            while (it.moveToNext()) {
                val imageId = it.getLong(it.getColumnIndex(MediaStore.Images.Media._ID))
                val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendPath(imageId.toString()).build()
                adapter.addImage(imageUri)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setUpRV() {
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()
        pickedImageList.clear()
    }
}