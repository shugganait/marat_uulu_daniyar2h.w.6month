package lib.kg.pictureselector.ui.selected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import lib.kg.pictureselector.databinding.FragmentSelectedPhotosBinding
import lib.kg.pictureselector.ui.gallery.GalleryFragment.Companion.KEY_FOR_LIST
import lib.kg.pictureselector.ui.selected.adapter.SelectedPhotoAdapter

class SelectedPhotosFragment : Fragment() {

    private lateinit var binding: FragmentSelectedPhotosBinding
    private var adapter = SelectedPhotoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectedPhotosBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListeners()
    }

    private fun initAdapter() {
        val data = arguments?.getStringArrayList(KEY_FOR_LIST)
        adapter.addImage(data!!)
        binding.recyclerView.adapter = adapter
        setUpRV()
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun setUpRV() {
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = layoutManager
    }

}