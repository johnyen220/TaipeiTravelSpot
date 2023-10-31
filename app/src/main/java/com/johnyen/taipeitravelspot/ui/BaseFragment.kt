package com.johnyen.taipeitravelspot.ui


import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cbes.ezreturn.utils.Logger
import com.cbes.ezreturn.utils.hint
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.johnyen.taipeitravelspot.R
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    private val mCompositeDisposable get() = CompositeDisposable()
    fun showLoadingIndicator(isShow: Boolean) {

        if (view?.findViewById<CircularProgressIndicator>(R.id.progress_indicator) != null) {
            val progressIndicator: CircularProgressIndicator =
                view!!.findViewById(R.id.progress_indicator)
            progressIndicator.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
        }
        if (view?.findViewById<View>(R.id.progress_indicator_background) != null) {
            val mView: View = view!!.findViewById(R.id.progress_indicator_background)
            mView.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
        }
    }

    protected fun <T> request(execute: suspend CoroutineScope.() -> T) {
        val handler = CoroutineExceptionHandler { coroutineContext, exception ->
            Logger.logE("Error occur on $coroutineContext")
            Logger.logE("CoroutineExceptionHandler got $exception", exception)
        }

        lifecycleScope.launch(handler) {
            try {
                showLoadingIndicator(true)
                execute()
            } catch (e: Exception) {
                hint(e.message)
            } finally {
                showLoadingIndicator(false)
            }
        }
    }

    fun addDisposable(mDisposable: Disposable) {
        mDisposable.let { mCompositeDisposable.add(it) }
    }

    /**
     * 取消所有訂閱
     */
    private fun clearDisposable() {
        mCompositeDisposable.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearDisposable()
    }

    protected open fun initViewAndClickListener() {}

}