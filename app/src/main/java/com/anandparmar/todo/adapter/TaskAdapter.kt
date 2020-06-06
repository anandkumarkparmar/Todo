package com.anandparmar.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anandparmar.todo.database.Task
import com.anandparmar.todo.databinding.ItemViewTaskBinding

class TaskAdapter(val onClick: (task: Task) -> Unit): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    var taskList =  listOf<Task>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(taskList.get(position), onClick)
    }

    class ViewHolder private constructor(val binding: ItemViewTaskBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Task, onClick: (task: Task) -> Unit) {
            binding.task = item
            binding.checkRB.setOnCheckedChangeListener { compoundButton, checked ->
                if (checked) {
                    onClick.invoke(item)
                }
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewTaskBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
