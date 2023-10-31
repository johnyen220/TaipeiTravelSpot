package com.johnyen.taipeitravelspot.ui.taipeiOpenData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.johnyen.taipeitravelspot.R
import com.johnyen.taipeitravelspot.api.portal.response.model.Data
import com.johnyen.taipeitravelspot.databinding.FragmentTaipeiTravelSpotListBinding
import com.johnyen.taipeitravelspot.ui.BaseFragment

class TaipeiTravelSpotListFragment : BaseFragment(), TaipeiSpotAdapter.Callback {
    private var _binding: FragmentTaipeiTravelSpotListBinding? = null
    private val binding get() = _binding!!
    private val taipeiOpenDataViewModel: TaipeiOpenDataViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTaipeiTravelSpotListBinding.inflate(inflater, container, false)
        initViewAndClickListener()
        initLiveData()
        return binding.root

    }
    override fun initViewAndClickListener(){
        val toolBarTitle = requireActivity().findViewById<TextView>(R.id.toolbar_title)
        toolBarTitle.text=""
        val toolbarArrowBack = requireActivity().findViewById<TextView>(R.id.toolbar_arrow_back)
        toolbarArrowBack.visibility = View.GONE
    }
    private fun initLiveData() {
        taipeiOpenDataViewModel.fetchTaipeiOpenDataResult.observe(viewLifecycleOwner){
            initRecyclerView(it.data)
            taipeiOpenDataViewModel.indicator.postValue(false)
        }
        request{
            taipeiOpenDataViewModel.fetchAttractionAll()
        }
    }

    private fun initRecyclerView(data: List<Data>?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerView.adapter = data?.let {
            TaipeiSpotAdapter(it,this)
        }
    }

    override fun onItemSelect(data: Data) {
        taipeiOpenDataViewModel.fetchTaipeiSpotDetailResult.postValue(data)
    }
}