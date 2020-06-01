package com.umeshghimire.commons.interfaces

import com.umeshghimire.commons.views.MyScrollView

interface SecurityTab {
    fun initTab(requiredHash: String, listener: HashListener, scrollView: MyScrollView)

    fun visibilityChanged(isVisible: Boolean)
}
