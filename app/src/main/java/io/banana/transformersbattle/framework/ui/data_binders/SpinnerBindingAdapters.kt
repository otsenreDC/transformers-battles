package io.banana.transformersbattle.framework.ui.data_binders

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter

object SpinnerBindingAdapters {

    @JvmStatic
    @BindingAdapter("onIntOptionSelected")
    fun onOptionSelected(spinner: Spinner, callback: OnOptionSelected) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position)
                val value = item?.toString()?.toIntOrNull() ?: -1
                callback.onSelected(value)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    @JvmStatic
    @BindingAdapter("initTechSpecValue")
    fun setInitValue(spinner: Spinner, value: Int) {
        spinner.setSelection(value)
    }

    interface OnOptionSelected {
        fun onSelected(value: Int)
    }
}