package io.banana.transformersbattle.framework.ui.battle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.banana.transformersbattle.R
import io.banana.transformersbattle.databinding.FragmentBattleBinding
import io.banana.transformersbattle.domain.models.Transformer
import io.banana.transformersbattle.framework.extensions.observe
import org.koin.android.ext.android.inject

class BattleFragment : Fragment() {

    private lateinit var binding: FragmentBattleBinding
    private val mainViewModel: BattleViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = savedInstanceState ?: requireActivity().intent.extras

        val transformers =
            extras?.getParcelableArrayList<Transformer>(BattleActivity.EXTRA_IN_TRANSFORMERS)
                ?: throw RuntimeException("MISSING LIST OF TRANSFORMERS")

        mainViewModel.init(transformers)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            BattleActivity.EXTRA_IN_TRANSFORMERS,
            arrayListOf(*mainViewModel.transformers.toTypedArray())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_battle,
            container,
            false
        )
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        registerEvents()

        return binding.root
    }

    private fun registerEvents() {
        observe(mainViewModel.onClose) {
            requireActivity().finish()
        }
    }

}