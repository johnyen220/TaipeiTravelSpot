package com.johnyen.taipeitravelspot.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.cbes.ezreturn.utils.Logger
import com.johnyen.taipeitravelspot.utils.SystemUi
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.johnyen.taipeitravelspot.R
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private val mCompositeDisposable get() = CompositeDisposable()

    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemUi.setNavigationBarColor(this)
        SystemUi.setStatusBarColor(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (isPermissionsGranted(permissions)) {
            true -> onPermissionGranted(requestCode)
            false -> onPermissionDenied(requestCode)
        }
    }

    private fun isPermissionsGranted(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    protected open fun onPermissionGranted(requestCode: Int) {}

    protected open fun onPermissionDenied(requestCode: Int) {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(R.string.title_permission_failed)
            .setMessage(
                when (requestCode) {
                    CODE_PERMISSION_CAMERA -> R.string.hint_permission_camera_failed
                    CODE_PERMISSION_STORAGE -> R.string.hint_permission_storage_failed
                    else -> -1
                }
            )
            .setPositiveButton(R.string.btn_confirm) { _, _ -> finish() }
            .show()
    }

    protected fun showLoadingIndicator(isShow: Boolean, binding: ViewBinding) {
        isLoading = isShow
        binding.root.findViewById<CircularProgressIndicator>(R.id.progress_indicator).visibility =
            if (isShow) View.VISIBLE else View.GONE
        binding.root.findViewById<View>(R.id.progress_indicator_background).visibility =
            if (isShow) View.VISIBLE else View.GONE
        binding.root.findViewById<View>(R.id.progress_indicator_background).setOnClickListener { }
        val linearlayout: LinearLayout = binding.root.findViewById(R.id.text_container)
        if (!isShow) {
            linearlayout.visibility = View.GONE
        }
    }

    companion object {
        const val CODE_PERMISSION_CAMERA = 1
        const val CODE_PERMISSION_STORAGE = 2

        val PERMISSION_CAMERA = arrayOf(
            Manifest.permission.CAMERA
        )

        val PERMISSION_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    /**
     * 添加訂閱
     */
    fun addDisposable(mDisposable: Disposable) {
        mDisposable.let { mCompositeDisposable.add(it) }
    }

    /**
     * 取消所有訂閱
     */
    private fun clearDisposable() {
        mCompositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearDisposable()
    }

    protected open fun initViewAndClickListener() {}

    protected fun <T> request(execute: suspend CoroutineScope.() -> T) {
        val handler = CoroutineExceptionHandler { coroutineContext, exception ->
            Logger.logE("Error occur on $coroutineContext")
            Logger.logE("CoroutineExceptionHandler got $exception", exception)
        }
        CoroutineScope(handler).launch {
            try {
                execute()
            } catch (e: Exception) {

            }
        }
    }
}