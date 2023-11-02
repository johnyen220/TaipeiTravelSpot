package com.johnyen.taipeitravelspot.ui.taipeiOpenData

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.johnyen.taipeitravelspot.R
import com.johnyen.taipeitravelspot.api.portal.response.model.Data
import com.johnyen.taipeitravelspot.constant.C
import com.johnyen.taipeitravelspot.databinding.FragmentTaipeiTravelSpotListBinding
import com.johnyen.taipeitravelspot.ui.BaseFragment
import com.johnyen.taipeitravelspot.ui.taipeiOpenData.adapter.TaipeiSpotAdapter
import com.johnyen.taipeitravelspot.ui.taipeiOpenData.viewModel.TaipeiOpenDataViewModel

class TaipeiTravelSpotListFragment : BaseFragment(), TaipeiSpotAdapter.Callback {
    private var _binding: FragmentTaipeiTravelSpotListBinding? = null
    private val binding get() = _binding!!
    private val taipeiOpenDataViewModel: TaipeiOpenDataViewModel by activityViewModels()
    private var myDrawerLayout:DrawerLayout? = null
    private var changeLanguageImage:ImageView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTaipeiTravelSpotListBinding.inflate(inflater, container, false)
        initViewAndClickListener()
        initLiveData()
        requestTravelSpotData(C.initialLanguage)
        return binding.root

    }
    override fun initViewAndClickListener(){
        myDrawerLayout = taipeiOpenDataViewModel.myDrawerLayout
        changeLanguageImage = taipeiOpenDataViewModel.changeLanguageImage
        val toolBarTitle = requireActivity().findViewById<TextView>(R.id.toolbar_title)
        toolBarTitle.text=""
        val toolbarArrowBack = requireActivity().findViewById<TextView>(R.id.toolbar_arrow_back)
        toolbarArrowBack.visibility = View.GONE
        taipeiOpenDataViewModel.lockRightDrawerLiveData.postValue(false)
        changeLanguageImage?.setOnClickListener {
            if(myDrawerLayout?.isDrawerOpen(Gravity.RIGHT) == true){
                myDrawerLayout?.closeDrawers()
            } else {
                myDrawerLayout?.openDrawer(Gravity.RIGHT)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )
    }
    private fun initLiveData() {
        taipeiOpenDataViewModel.fetchTaipeiOpenDataResult.observe(viewLifecycleOwner){
            initRecyclerView(it.data)
            taipeiOpenDataViewModel.indicator.postValue(false)
        }
        taipeiOpenDataViewModel.fetchChangeLanguageResult.observe(requireActivity()){
            requestTravelSpotData(it)
        }

    }
    private fun requestTravelSpotData(lang:String){
        request{
            taipeiOpenDataViewModel.fetchAttractionAllByLang(lang)
        }
    }
    private fun initRecyclerView(data: List<Data>?) {
        binding.recyclerView.adapter = data?.let {
            TaipeiSpotAdapter(it,this)
        }
    }

    override fun onItemSelect(data: Data) {
        taipeiOpenDataViewModel.fetchTaipeiSpotDetailResult.postValue(data)
    }
}