package com.android.recipeapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.recipeapp.R
import com.android.recipeapp.databinding.InstructionsRowLayoutBinding
import com.android.recipeapp.models.Step

class RecipeInstructionsFragmentAdapter(var steps: List<Step>):RecyclerView.Adapter<RecipeInstructionsFragmentAdapter.AdapterViewHolder>() {

    class AdapterViewHolder(val binding: InstructionsRowLayoutBinding ):RecyclerView.ViewHolder(binding.root) {
        fun bind(step: Step){
            binding.apply{
                binding.step=step
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<InstructionsRowLayoutBinding>(inflater, R.layout.instructions_row_layout,parent,false)
        return AdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(steps[position])
    }

    override fun getItemCount(): Int {
        return steps.size
    }
}