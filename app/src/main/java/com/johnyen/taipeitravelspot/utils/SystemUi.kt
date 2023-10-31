/*
 * *
 *  * Created by johnyen on 2022/5/12 下午1:53
 *  * Retrun Helper Co., Ltd(c) 2022 . All rights reserved.
 *
 */

package com.johnyen.taipeitravelspot.utils

import android.app.Activity
import android.view.WindowManager
import com.johnyen.taipeitravelspot.R


object SystemUi {
    fun setStatusBarColor(activity: Activity) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity.window.statusBarColor = activity.getColor(R.color.colorPrimaryDark)
    }

    fun setNavigationBarColor(activity: Activity) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity.window.navigationBarColor = activity.getColor(R.color.colorPrimaryDark)
    }
}