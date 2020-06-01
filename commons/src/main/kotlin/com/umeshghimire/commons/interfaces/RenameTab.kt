package com.umeshghimire.commons.interfaces

import com.umeshghimire.commons.activities.BaseSimpleActivity

interface RenameTab {
    fun initTab(activity: BaseSimpleActivity, paths: ArrayList<String>)

    fun dialogConfirmed(callback: (success: Boolean) -> Unit)
}
