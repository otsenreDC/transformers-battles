package io.banana.transformersbattle.framework.extensions

import android.app.AlertDialog
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

fun <T> Fragment.observe(
    liveData: MutableLiveData<T>, block: (value: T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, block)
}

fun Fragment.snack(message: String) {
    requireActivity().snack(message)
}

fun Fragment.showError(message: String) {
    AlertDialog.Builder(requireContext())
        .setMessage(message)
        .setPositiveButton(android.R.string.ok) { _, _ ->

        }.create().show()
}
