package io.banana.transformersbattle.framework.ui.data_binders

import android.view.View
import androidx.databinding.BindingAdapter

object ViewDataBindingAdapters {

    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

}