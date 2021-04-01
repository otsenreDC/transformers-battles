package io.banana.transformersbattle.framework.ui.create_transformer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.banana.transformersbattle.R
import io.banana.transformersbattle.databinding.FragmentTransformersWorkshopBinding
import io.banana.transformersbattle.domain.models.Transformer
import io.banana.transformersbattle.framework.extensions.observe
import io.banana.transformersbattle.framework.extensions.snack
import org.koin.android.ext.android.inject

class TransformersWorkshopFragment : Fragment() {

    private val mainWorkshopViewModel: TransformersWorkshopViewModel by inject()
    private lateinit var binding: FragmentTransformersWorkshopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = savedInstanceState ?: requireActivity().intent.extras
        if (bundle?.containsKey(EXTRA_IN_TRANSFORMER) == true) {
            val transformer: Transformer = bundle
                .getParcelable(EXTRA_IN_TRANSFORMER)
                ?: throw RuntimeException("MISSING TRANSFORMER")

            mainWorkshopViewModel.startForEdition(transformer)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_transformers_workshop,
            null,
            false
        )
        binding.viewModel = mainWorkshopViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        registerEvents()

        return binding.root
    }

    private fun registerEvents() {
        observe(mainWorkshopViewModel.onTransformerCreated) {
            with(requireActivity()) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

        observe(mainWorkshopViewModel.onErrorLiveData) {
            snack(it)
        }
    }

    companion object {
        private const val EXTRA_IN_TRANSFORMER: String = "transformer"

        fun startForCreation(context: Activity, fragment: Fragment, requestCode: Int) {
            fragment.startActivityForResult(
                Intent(context, TransformersWorkshopActivity::class.java),
                requestCode
            )
        }

        fun startForEdition(context: Activity, fragment: Fragment, requestCode: Int, transformer: Transformer) {
            fragment.startActivityForResult(
                Intent(context, TransformersWorkshopActivity::class.java).apply {
                    putExtras(
                        bundleOf(
                            EXTRA_IN_TRANSFORMER to transformer
                        )
                    )
                },
                requestCode
            )
        }
    }

}