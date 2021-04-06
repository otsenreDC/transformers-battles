package io.banana.transformersbattle.framework.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.banana.transformersbattle.R
import io.banana.transformersbattle.databinding.ActivitySplashBinding
import io.banana.transformersbattle.framework.extensions.observe
import io.banana.transformersbattle.framework.ui.transformer_list.TransformerListActivity
import org.koin.android.ext.android.inject

private const val ERROR_DIALOG_REQUEST_CODE = 1

class SplashActivity : AppCompatActivity() {

    private var retryProviderInstall: Boolean = false

    private lateinit var binding: ActivitySplashBinding
    private val mainViewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_splash
        )
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

        registerEvents()

    }

    override fun onStart() {
        super.onStart()
        mainViewModel.start()
    }

    private fun registerEvents() {
        observe(mainViewModel.onNavigateMainScreen) {
            if (it) {
                startActivity(Intent(this, TransformerListActivity::class.java))
                finish()
            }
        }
    }
}