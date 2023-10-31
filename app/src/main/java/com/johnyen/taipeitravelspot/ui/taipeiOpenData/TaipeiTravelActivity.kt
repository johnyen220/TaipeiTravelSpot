package com.johnyen.taipeitravelspot.ui.taipeiOpenData

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.johnyen.taipeitravelspot.R
import com.johnyen.taipeitravelspot.databinding.ActivityTaipeiTravelBinding
import com.johnyen.taipeitravelspot.ui.BaseActivity

class TaipeiTravelSpotListActivity : BaseActivity() {
    private lateinit var binding: ActivityTaipeiTravelBinding
    private val firstFragment = TaipeiTravelSpotListFragment()
    private val taipeiOpenDataViewModel: TaipeiOpenDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaipeiTravelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragment(firstFragment)
        initView()
        initLiveData()
    }

    private fun initView(){
        showLoadingIndicator(true,binding)
    }

    private fun initLiveData(){
        taipeiOpenDataViewModel.fetchWebViewUrlResult.observe(this){
            val fragment = WebViewFragment()
            val bundle = Bundle()
            bundle.putString("url", it)
            fragment.arguments = bundle
            addBackStackFragment(fragment)
        }
        taipeiOpenDataViewModel.fetchTaipeiSpotDetailResult.observe(this){
            val fragment = TaipeiSpotDetailFragment()
            val bundle = Bundle()
            bundle.putString("data", Gson().toJson(it))
            fragment.arguments = bundle
            addBackStackFragment(fragment)
        }
        taipeiOpenDataViewModel.indicator.observe(this){
            showLoadingIndicator(it,binding)
        }
    }

    private fun addFragment(f: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, f,"Fragment1")
        transaction.commit()
    }
    private fun addBackStackFragment(f: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack("Fragment2")
        transaction.replace(R.id.fragment_container,f,"Fragment2")
        transaction.commit()
    }
}

