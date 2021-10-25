package com.nick.mpv.frame


import android.app.Dialog
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.nick.mpv.frame.base.AndBaseActivity
import com.nick.mpv.frame.base.BasePresenter
import com.nick.mpv.frame.base.BaseView
import com.nick.mpv.util.DeviceUtils
import com.nick.mpv.widget.dialog.CircleAlertDialog
import java.util.*


/**
 * 基础BaseActivity
 * zhengz
 */

abstract class BaseActivity<V : BaseView, P : BasePresenter<V>> : AndBaseActivity<V, P>() {
    /**
     * 点击current View不被隐藏软键盘
     *
     * @return
     */
    open val interceptFocusViews: ArrayList<View>?
        get() = null

    /**
     * 打开点击屏幕隐藏输入框
     */
    open var touchHideInput: Boolean = true


    private var mWaitDialog: Dialog? = null

    override fun finish() {
        // 隐藏输入框
        DeviceUtils.hideInputMethodManager(this, window.decorView)
        dismissDialog()
        super.finish()
    }


    /**
     * 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
     *
     * @param ev
     * @return
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (touchHideInput && ev.action == MotionEvent.ACTION_DOWN) {
            val intercepts = interceptFocusViews
            if (null != intercepts && intercepts.size > 0 && isRange(intercepts, ev)) {
                return super.dispatchTouchEvent(ev)
            }
            val view = currentFocus
            if (null != view && view is EditText && !isRange(view, ev)) {
                DeviceUtils.hideInputMethodManager(this, view)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param views
     * @param event
     * @return
     */
    private fun isRange(views: ArrayList<View>, event: MotionEvent): Boolean {
        var isRange = false
        for (i in views.indices) {
            isRange = isRange(views[i], event)
            if (isRange) {
                return true
            }
        }
        return isRange
    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param view
     * @param event
     * @return true 坐标在view范围内，反之在view范围外
     */
    private fun isRange(view: View, event: MotionEvent): Boolean {
        val location = intArrayOf(0, 0)
        view.getLocationInWindow(location)
        val left = location[0]
        val top = location[1]
        val bottom = top + view.height
        val right = left + view.width
        return event.x > left && event.x < right && event.y > top && event.y < bottom
    }


    fun showDialog(msg: String = "") {
        if (null == mWaitDialog) {
            mWaitDialog = CircleAlertDialog(msg, false, this)
        }
        mWaitDialog?.setTitle(msg)
        mWaitDialog?.show()
    }

    fun dismissDialog() {
        mWaitDialog?.dismiss()
    }


}
