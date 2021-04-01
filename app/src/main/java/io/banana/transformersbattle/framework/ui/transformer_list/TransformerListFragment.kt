package io.banana.transformersbattle.framework.ui.transformer_list

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomappbar.BottomAppBar
import io.banana.transformersbattle.R
import io.banana.transformersbattle.databinding.FragmentTransformerListBinding
import io.banana.transformersbattle.domain.models.Transformer
import io.banana.transformersbattle.framework.extensions.observe
import io.banana.transformersbattle.framework.extensions.showError
import io.banana.transformersbattle.framework.ui.battle.BattleActivity
import io.banana.transformersbattle.framework.ui.create_transformer.TransformersWorkshopFragment
import org.koin.android.ext.android.inject

private const val REQUEST_CREATE_TRANSFORMER = 1

class TransformerListFragment : Fragment() {

    private lateinit var binding: FragmentTransformerListBinding
    private lateinit var adapter: TransformerAdapter
    private val mainViewModel: TransformerListViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_transformer_list,
            null,
            false
        )
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        configureRecycler(binding.transformersList)
//        configureAddButton(binding.addTransformerButton)
        configureBottomAppBar(binding.bottomAppBar)
        configureBattleButton(binding.startBattleButton)
        configureSwipeRefresh(binding.refreshLayout)
        registerEvents()

        mainViewModel.start()

        return binding.root
    }

    private fun registerEvents() {
        observe(mainViewModel.onListLoadedLiveData) {
            adapter.listItem = it
        }
        observe(mainViewModel.onErrorLiveData) {
            showError(it)
        }
        observe(mainViewModel.onLoadingLiveData) {
            if (!it) binding.refreshLayout.isRefreshing = false
        }
    }

    private fun configureRecycler(recyclerView: RecyclerView) {
        adapter = TransformerAdapter(onItemAction)
        recyclerView.adapter = adapter
    }

    private fun configureBottomAppBar(bottomAppBar: BottomAppBar) {
        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.add_transformer_option) {
                TransformersWorkshopFragment.startForCreation(
                    requireActivity(),
                    this,
                    REQUEST_CREATE_TRANSFORMER
                )
                true
            } else false
        }
    }

    private fun configureBattleButton(button: View) {
        button.setOnClickListener {
            BattleActivity.startActivity(requireActivity(), mainViewModel.transformers)
        }
    }

    private fun configureSwipeRefresh(refreshLayout: SwipeRefreshLayout) {
        refreshLayout.setOnRefreshListener {
            mainViewModel.refresh()
        }
    }

    private val onItemAction = object : TransformerAdapter.OnItemAction {
        override fun onDelete(transformer: Transformer) {
            AlertDialog.Builder(requireContext())
                .setMessage("Are you sure that you want to delete ${transformer.name}?")
                .setPositiveButton("Delete it!") { _, _ ->
                    mainViewModel.remove(transformer.id)
                }.setNegativeButton("I changed my mind!") { _, _ ->

                }.create().show()
        }

        override fun onEdit(transformer: Transformer) {
            TransformersWorkshopFragment.startForEdition(
                requireActivity(),
                this@TransformerListFragment,
                REQUEST_CREATE_TRANSFORMER,
                transformer,
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CREATE_TRANSFORMER) {
            mainViewModel.refresh()
        }
    }
}