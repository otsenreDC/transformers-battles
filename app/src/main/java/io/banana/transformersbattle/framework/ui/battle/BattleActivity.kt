package io.banana.transformersbattle.framework.ui.battle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import io.banana.transformersbattle.R
import io.banana.transformersbattle.domain.models.Transformer

class BattleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_battle)
    }

    companion object {
        const val EXTRA_IN_TRANSFORMERS: String = "transformers"
        fun startActivity(context: Activity, transformers: List<Transformer>) {
            context.startActivity(Intent(context, BattleActivity::class.java).apply {
                putExtras(
                    bundleOf(
                        EXTRA_IN_TRANSFORMERS to arrayListOf(*transformers.toTypedArray())
                    )
                )
            })
        }
    }
}