package com.johnyen.taipeitravelspot.ui.taipeiOpenData

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.johnyen.taipeitravelspot.R
import com.johnyen.taipeitravelspot.constant.C
import com.johnyen.taipeitravelspot.databinding.ActivityTaipeiTravelBinding
import com.johnyen.taipeitravelspot.databinding.ToolbarBinding
import com.johnyen.taipeitravelspot.ui.BaseActivity
import com.johnyen.taipeitravelspot.ui.taipeiOpenData.viewModel.TaipeiOpenDataViewModel

class TaipeiTravelSpotListActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityTaipeiTravelBinding
    private lateinit var toolbarBinding: ToolbarBinding
    private val firstFragment = TaipeiTravelSpotListFragment()
    private val taipeiOpenDataViewModel: TaipeiOpenDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaipeiTravelBinding.inflate(layoutInflater)
        toolbarBinding = ToolbarBinding.bind(binding.root)
        setContentView(binding.root)
        initView()
        initLiveData()
        addFragment(firstFragment)
    }

    private fun initView() {
        showLoadingIndicator(true, binding)
        taipeiOpenDataViewModel.myDrawerLayout = binding.myDrawerLayout
        taipeiOpenDataViewModel.changeLanguageImage = toolbarBinding.changeLanguageImage
        binding.navigation.setNavigationItemSelectedListener(this)
    }

    private fun initLiveData() {
        taipeiOpenDataViewModel.fetchWebViewUrlResult.observe(this) {
            val fragment = WebViewFragment()
            val bundle = Bundle()
            bundle.putString("url", it)
            fragment.arguments = bundle
            addBackStackFragment(fragment)
        }
        taipeiOpenDataViewModel.fetchTaipeiSpotDetailResult.observe(this) {
            val fragment = TaipeiSpotDetailFragment()
            val bundle = Bundle()
            bundle.putString("data", Gson().toJson(it))
            fragment.arguments = bundle
            addBackStackFragment(fragment)
        }
        taipeiOpenDataViewModel.indicator.observe(this) {
            showLoadingIndicator(it, binding)
        }
        taipeiOpenDataViewModel.lockRightDrawerLiveData.observe(this) {
            if (it) {
                binding.myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                toolbarBinding.changeLanguageImage.visibility = View.GONE
            } else {
                binding.myDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                toolbarBinding.changeLanguageImage.visibility = View.VISIBLE
            }
        }
        taipeiOpenDataViewModel.openDrawerLiveData.observe(this) {
            if (it) {
                binding.myDrawerLayout.openDrawer(Gravity.RIGHT)
            } else {
                binding.myDrawerLayout.closeDrawers()
            }
        }
    }

    private fun addFragment(f: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, f, "Fragment1")
        transaction.commit()
    }

    private fun addBackStackFragment(f: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack("Fragment2")
        transaction.replace(R.id.fragment_container, f, "Fragment2")
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.zh_tw -> {refreshListByLanguage(C.ZH_TW)}
            R.id.zh_cn -> {refreshListByLanguage(C.ZH_CN)}
            R.id.en -> {refreshListByLanguage(C.EN)}
            R.id.ja -> {refreshListByLanguage(C.JA)}
            R.id.ko -> {refreshListByLanguage(C.KO)}
            R.id.es -> {refreshListByLanguage(C.ES)}
            R.id.th -> {refreshListByLanguage(C.TH)}
            R.id.vi -> {refreshListByLanguage(C.VI)}
        }
        binding.myDrawerLayout.closeDrawers()
        return true
    }
    private fun refreshListByLanguage(lang:String){
        showLoadingIndicator(true, binding)
        C.initialLanguage = lang
        taipeiOpenDataViewModel.fetchChangeLanguageResult.postValue(lang)
    }
}

