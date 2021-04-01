package io.banana.transformersbattle.framework.ui.data_binders

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import io.banana.transformersbattle.R
import io.banana.transformersbattle.domain.models.Teams

object RadioGroupBindingAdapters {

    @JvmStatic
    @BindingAdapter("onOptionSelected")
    fun onItemSelected(view: RadioButton, callback: OnItemSelected) {
        view.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) callback.onItemSelected()
        }
    }

    @JvmStatic
    @BindingAdapter("teamSelected")
    fun setInitValue(radioGroup: RadioGroup, value: Teams) {
        when (value) {
            Teams.Decepticons -> {
                radioGroup.check(R.id.decepticonsButton)
            }
            Teams.Autobots -> {
                radioGroup.check(R.id.autobotsButton)
            }
            Teams.Unknown -> {
                radioGroup.clearCheck()
            }
        }
    }

    interface OnItemSelected {
        fun onItemSelected()
    }
}