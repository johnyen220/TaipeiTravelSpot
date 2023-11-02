package com.johnyen.taipeitravelspot.ui.taipeiOpenData


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.johnyen.taipeitravelspot.api.portal.response.model.Data
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.johnyen.taipeitravelspot.R
import com.johnyen.taipeitravelspot.databinding.FragmentWebviewBinding
import com.johnyen.taipeitravelspot.ui.BaseFragment
import com.johnyen.taipeitravelspot.ui.taipeiOpenData.viewModel.TaipeiOpenDataViewModel


class WebViewFragment : BaseFragment(){
    private var _binding: FragmentWebviewBinding? = null
    private val binding get() = _binding!!
    private val taipeiOpenDataViewModel: TaipeiOpenDataViewModel by activityViewModels()
    private var title : String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        val bundle = this.arguments
        if (bundle != null) {
            val dataGson = bundle.getString("data", "")
            if(dataGson.isNotEmpty()){
                val data: Data = Gson().fromJson(dataGson, object: TypeToken<Data>(){}.type)
                val url = data.url
                binding.webview.settings.javaScriptEnabled = true

                binding.webview.webViewClient = object : WebViewClient() {
                    @Deprecated("Deprecated in Java")
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        if (url != null) {
                            view?.loadUrl(url)
                        }
                        return true
                    }
                }
                binding.webview.loadUrl(url)
                title = data.name
            }
        }
        initViewAndClickListener()
        setupOnBackPressOn()
        return binding.root
    }

    override fun initViewAndClickListener(){
        val toolBarTitle = requireActivity().findViewById<TextView>(R.id.toolbar_title)
        toolBarTitle.text=title
        val toolbarArrowBack = requireActivity().findViewById<TextView>(R.id.toolbar_arrow_back)
        toolbarArrowBack.visibility = View.VISIBLE
        toolbarArrowBack.setOnClickListener { parentFragmentManager.popBackStack() }
        taipeiOpenDataViewModel.lockRightDrawerLiveData.postValue(true)
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