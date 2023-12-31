package com.johnyen.taipeitravelspot.ui.taipeiOpenData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.johnyen.taipeitravelspot.api.portal.response.model.Data
import com.cbes.ezreturn.utils.loadInternalImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.johnyen.taipeitravelspot.R
import com.johnyen.taipeitravelspot.databinding.FragmentTaipeiSpotDetailBinding
import com.johnyen.taipeitravelspot.ui.BaseFragment
import com.johnyen.taipeitravelspot.ui.taipeiOpenData.adapter.TravelSpotPhotoGridAdapter
import com.johnyen.taipeitravelspot.ui.taipeiOpenData.viewModel.TaipeiOpenDataViewModel


class TaipeiSpotDetailFragment : BaseFragment(){
    private var _binding: FragmentTaipeiSpotDetailBinding? = null
    private val binding get() = _binding!!
    private val taipeiOpenDataViewModel: TaipeiOpenDataViewModel by activityViewModels()
    private var title : String = ""
    private var itemList: MutableList<String> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaipeiSpotDetailBinding.inflate(inflater, container, false)
        val bundle = this.arguments
        if (bundle != null) {
            val dataGson = bundle.getString("data")
            if (dataGson != null) {
                if(dataGson.isNotEmpty()) {
                    val data: Data = Gson().fromJson(dataGson, object:TypeToken<Data>(){}.type)
                    val url = data.url
                    var src :String? = ""
                    if(data.images?.size!! >0) {
                        src = data.images[0].src
                        data.images.forEach {
                            it.src?.let { it1 -> itemList.add(it1) }
                        }
                    }
                    binding.link.text = url
                    binding.link.setOnClickListener {
                        goToWebViewFragment(dataGson)
                    }
                    if (src != null && src.isNotEmpty()) {
                        binding.imageView.loadInternalImage(src)
                    }
                    if(data.introduction.isNotEmpty()) {
                        binding.introduction.text = data.introduction
                    }
                    title=data.name
                }
            }
        }
        setupOnBackPressOn()
        initViewAndClickListener()
        return binding.root
    }
    override fun initViewAndClickListener(){
        val toolBarTitle = requireActivity().findViewById<TextView>(R.id.toolbar_title)
        toolBarTitle.text=title
        val toolbarArrowBack = requireActivity().findViewById<TextView>(R.id.toolbar_arrow_back)
        toolbarArrowBack.visibility = View.VISIBLE
        toolbarArrowBack.setOnClickListener { parentFragmentManager.popBackStack() }
        taipeiOpenDataViewModel.lockRightDrawerLiveData.postValue(true)
        binding.gridRecyclerview.layoutManager =
            GridLayoutManager(requireContext(),3, LinearLayoutManager.VERTICAL, false)
        if(itemList!=null && itemList.size>0) {
            binding.gridRecyclerview.adapter = TravelSpotPhotoGridAdapter(itemList)
        }
    }
    private fun goToWebViewFragment(dataGson:String){
        val f: Fragment = WebViewFragment()
        val bundle = Bundle()
        bundle.putString("data", dataGson)
        f.arguments = bundle
        val transaction = parentFragmentManager.beginTransaction()
        transaction.addToBackStack("Fragment3")
        transaction.replace(R.id.fragment_container,f,"Fragment3")
        transaction.commit()
    }
    private fun setupOnBackPressOn(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            }
        )
    }
}