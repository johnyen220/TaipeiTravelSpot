package com.cbes.ezreturn.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.RESULT_UNCHANGED_SHOWN
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.material.dismisses
import com.johnyen.taipeitravelspot.R
import java.lang.reflect.Method


fun View.showKeyBoard(isShow: Boolean) {
    this.requestFocus()
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    when (isShow) {
        true -> imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        false -> imm.hideSoftInputFromWindow(this.windowToken, RESULT_UNCHANGED_SHOWN)
    }
}

inline fun <reified T : View> Activity.findOptional(@IdRes id: Int): T? = findViewById(id) as? T

inline val Activity.contentView: View?
    get() = findOptional<ViewGroup>(android.R.id.content)?.getChildAt(0)

inline fun <reified T : View> Fragment.findOptional(@IdRes id: Int): T? =
    activity?.findViewById(id) as? T

inline val Fragment.contentView: View?
    get() = findOptional<ViewGroup>(android.R.id.content)?.getChildAt(0)

fun View.hint(@StringRes resId: Int) {
    val snackBar = Snackbar.make(this, resId, Snackbar.LENGTH_SHORT)
    val view = snackBar.view
    view.setBackgroundColor(this.context.getColor(R.color.colorPrimaryBlack))
    val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.setTextColor(Color.WHITE)
    snackBar.show()
}

fun Activity.hint(@StringRes resId: Int) {
    contentView?.hint(resId)
}

fun Fragment.hint(@StringRes resId: Int) {
    contentView?.hint(resId) ?: activity?.hint(resId)
}

fun View.hint(msg: String?) {
    val snackBar = Snackbar.make(this, msg.orEmpty(), Snackbar.LENGTH_SHORT)
    val view = snackBar.view
    view.setBackgroundColor(this.context.getColor(R.color.colorPrimaryBlack))
    val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.setTextColor(Color.WHITE)
    snackBar.show()
}

fun Activity.hint(msg: String?) {
    contentView?.hint(msg)
}

fun Fragment.hint(msg: String?) {
    contentView?.hint(msg) ?: activity?.hint(msg)
}

fun View.alert(msg: String?, title: String?) {
    AlertDialog.Builder(this.context)
        .setCancelable(false)
        .setTitle(R.string.title_permission_failed)
        .setMessage(msg)
        .setTitle(title)
        .setPositiveButton(R.string.btn_confirm) { _, _ -> dismisses() }
        .show()
}

fun Activity.alert(msg: String?, title: String?) {
    contentView?.alert(msg, title)
}

fun Fragment.alert(msg: String?, title: String?) {
    contentView?.alert(msg, title) ?: activity?.alert(msg, title)
}

fun ImageView.loadInternalImage(path: String?) {

    Glide.with(this)
        .load(path)
        .error(R.mipmap.ic_placeholder)
        .placeholder(R.mipmap.ic_placeholder)
        .into(this)
}

//fragment

fun AppCompatActivity.navigateToFragment(navController: NavController, fragmentId: Int) {
    if (navController.currentDestination?.id != fragmentId) {
        navController.navigate(fragmentId)
    }
}

fun Fragment.navigateToFragment(
    navController: NavController,
    fragmentId: Int,
    bundle: Bundle
) {
    if (navController.currentDestination?.id != fragmentId) {
        navController.navigate(fragmentId, bundle)
    }
}

val Context.isConnected: Boolean
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capability =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

val Fragment.isConnected: Boolean
    get() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capability =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

fun EditText.shouldShowSoftInputOnFocus(show: Boolean) {
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
            this.showSoftInputOnFocus = show
        }
        Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 -> {
            val method: Method = EditText::class.java.getMethod(
                "setSoftInputShownOnFocus", *arrayOf<Class<*>?>(Boolean::class.javaPrimitiveType)
            )
            method.isAccessible = true
            method.invoke(this, show)
        }
        else -> {
            val method: Method = EditText::class.java.getMethod(
                "setShowSoftInputOnFocus", *arrayOf<Class<*>?>(Boolean::class.javaPrimitiveType)
            )
            method.isAccessible = true
            method.invoke(this, show)
        }
    }
}