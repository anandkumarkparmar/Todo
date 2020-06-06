package com.anandparmar.todo.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anandparmar.todo.R
import com.anandparmar.todo.adapter.TaskAdapter
import com.anandparmar.todo.databinding.FragmentHomeBinding
import com.anandparmar.todo.viewmodel.TaskViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        val adapter = TaskAdapter() {
            Handler().postDelayed(Runnable {
                viewModel.completeATask(it.taskId)
            }, 500)
        }
        binding.taskRecyclerView.adapter = adapter
        viewModel.nonCompletedTasks.observe(viewLifecycleOwner, Observer {
            if (it == null || it.isEmpty()) {
                binding.taskRecyclerView.visibility = View.GONE
                binding.taskCompletedLayout.visibility = View.VISIBLE
            } else {
                binding.taskRecyclerView.visibility = View.VISIBLE
                binding.taskCompletedLayout.visibility = View.GONE
                adapter.taskList = it
            }
        })

        return binding.root
    }
}
