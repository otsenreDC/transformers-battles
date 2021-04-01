package io.banana.transformersbattle.framework.ui.transformer_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.banana.transformersbattle.R
import io.banana.transformersbattle.databinding.ItemTransformerBinding
import io.banana.transformersbattle.domain.models.Transformer

class TransformerAdapter(private val onItemAction: TransformerAdapter.OnItemAction) :
    RecyclerView.Adapter<TransformerAdapter.ViewHolder>() {

    var listItem: List<Transformer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemTransformerBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.item_transformer,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(
            item,
            onDelete = {
                onItemAction.onDelete(item)
            },
            onEdit = {
                onItemAction.onEdit(item)
            }
        )
    }

    override fun getItemCount(): Int = listItem.size

    class ViewHolder(
        private val binding: ItemTransformerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transformer: Transformer, onDelete: () -> Unit, onEdit: () -> Unit) {

            binding.nameText.text = transformer.name
            binding.strengthTechSpec.value = "${transformer.strength}"
            binding.intelligenceTechSpec.value = "${transformer.intelligence}"
            binding.speedTechSpec.value = "${transformer.speed}"
            binding.enduranceTechSpec.value = "${transformer.endurance}"
            binding.rankTechSpec.value = "${transformer.rank}"
            binding.courageTechSpec.value = "${transformer.courage}"
            binding.firepowerTechSpec.value = "${transformer.firepower}"
            binding.skillTechSpec.value = "${transformer.skill}"
            binding.overallTechSpec.value = "${transformer.overallRating}"

            Glide.with(binding.teamIcon)
                .load(transformer.teamIcon)
                .placeholder(R.drawable.placeholder)
                .into(binding.teamIcon)

            binding.deleteButton.setOnClickListener {
                onDelete()
            }
            binding.editButton.setOnClickListener { onEdit() }
        }
    }

    interface OnItemAction {
        fun onDelete(transformer: Transformer)
        fun onEdit(transformer: Transformer)
    }
}