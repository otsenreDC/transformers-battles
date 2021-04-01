package io.banana.transformersbattle.framework.extensions

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar

fun <T> AppCompatActivity.observe(
    liveData: MutableLiveData<T>, block: (value: T) -> Unit
) {
    liveData.observe(this, block)
}

fun Activity.snack(message: String) {
    Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
}
