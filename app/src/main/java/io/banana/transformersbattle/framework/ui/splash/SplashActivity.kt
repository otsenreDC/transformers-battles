package io.banana.transformersbattle.framework.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.security.ProviderInstaller
import io.banana.transformersbattle.R
import io.banana.transformersbattle.databinding.ActivitySplashBinding
import io.banana.transformersbattle.framework.extensions.observe
import io.banana.transformersbattle.framework.ui.transformer_list.TransformerListActivity
import org.koin.android.ext.android.inject

private const val ERROR_DIALOG_REQUEST_CODE = 1

class SplashActivity : AppCompatActivity(), ProviderInstaller.ProviderInstallListener {

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

        ProviderInstaller.installIfNeededAsync(this, this)

    }

    private fun registerEvents() {
        observe(mainViewModel.onNavigateMainScreen) {
            if (it) {
                startActivity(Intent(this, TransformerListActivity::class.java))
                finish()
            }
        }
    }

    /**
     * This method is only called if the provider is successfully updated
     * (or is already up-to-date).
     */
    override fun onProviderInstalled() {
        // Provider is up-to-date, app can make secure network calls.
        mainViewModel.start()
    }

    override fun onProviderInstallFailed(errorCode: Int, recoveryIntent: Intent?) {
        GoogleApiAvailability.getInstance().apply {
            if (isUserResolvableError(errorCode)) {
                // Recoverable error. Show a dialog prompting the user to
                // install/update/enable Google Play services.
                showErrorDialogFragment(this@SplashActivity, errorCode, ERROR_DIALOG_REQUEST_CODE) {
                    // The user chose not to take the recovery action
                    onProviderInstallerNotAvailable()
                }
            } else {
                onProviderInstallerNotAvailable()
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ERROR_DIALOG_REQUEST_CODE) {
            // Adding a fragment via GoogleApiAvailability.showErrorDialogFragment
            // before the instance state is restored throws an error. So instead,
            // set a flag here, which will cause the fragment to delay until
            // onPostResume.
            retryProviderInstall = true
        }
    }

    /**
     * On resume, check to see if we flagged that we need to reinstall the
     * provider.
     */
    override fun onPostResume() {
        super.onPostResume()
        if (retryProviderInstall) {
            // We can now safely retry installation.
            ProviderInstaller.installIfNeededAsync(this, this)
        }
        retryProviderInstall = false
    }

    private fun onProviderInstallerNotAvailable() {
        // This is reached if the provider cannot be updated for some reason.
        // App should consider all HTTP communication to be vulnerable, and take
        // appropriate action.
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show()

    }
}