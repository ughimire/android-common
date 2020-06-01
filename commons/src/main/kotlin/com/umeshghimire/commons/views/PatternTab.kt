package com.umeshghimire.commons.views

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import com.umeshghimire.commons.R
import com.umeshghimire.commons.extensions.baseConfig
import com.umeshghimire.commons.extensions.toast
import com.umeshghimire.commons.extensions.updateTextColors
import com.umeshghimire.commons.helpers.PROTECTION_PATTERN
import com.umeshghimire.commons.interfaces.HashListener
import com.umeshghimire.commons.interfaces.SecurityTab
import kotlinx.android.synthetic.main.tab_pattern.view.*

class PatternTab(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs), SecurityTab {
    private var hash = ""
    private var requiredHash = ""
    private var scrollView: MyScrollView? = null
    lateinit var hashListener: HashListener

    override fun onFinishInflate() {
        super.onFinishInflate()
        val textColor = context.baseConfig.textColor
        context.updateTextColors(pattern_lock_holder)

        pattern_lock_view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> scrollView?.isScrollable = false
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> scrollView?.isScrollable = true
            }
            false
        }

        pattern_lock_view.correctStateColor = context.baseConfig.primaryColor
        pattern_lock_view.normalStateColor = textColor
        pattern_lock_view.addPatternLockListener(object : PatternLockViewListener {
            override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                receivedHash(PatternLockUtils.patternToSha1(pattern_lock_view, pattern))
            }

            override fun onCleared() {}

            override fun onStarted() {}

            override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {}
        })
    }

    override fun initTab(requiredHash: String, listener: HashListener, scrollView: MyScrollView) {
        this.requiredHash = requiredHash
        this.scrollView = scrollView
        hash = requiredHash
        hashListener = listener
    }

    private fun receivedHash(newHash: String) {
        when {
            hash.isEmpty() -> {
                hash = newHash
                pattern_lock_view.clearPattern()
                pattern_lock_title.setText(R.string.repeat_pattern)
            }
            hash == newHash -> {
                pattern_lock_view.setViewMode(PatternLockView.PatternViewMode.CORRECT)
                Handler().postDelayed({
                    hashListener.receivedHash(hash, PROTECTION_PATTERN)
                }, 300)
            }
            else -> {
                pattern_lock_view.setViewMode(PatternLockView.PatternViewMode.WRONG)
                context.toast(R.string.wrong_pattern)
                Handler().postDelayed({
                    pattern_lock_view.clearPattern()
                    if (requiredHash.isEmpty()) {
                        hash = ""
                        pattern_lock_title.setText(R.string.insert_pattern)
                    }
                }, 1000)
            }
        }
    }

    override fun visibilityChanged(isVisible: Boolean) {}
}
