package com.anandparmar.todo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.anandparmar.todo.R
import com.anandparmar.todo.database.Task
import com.anandparmar.todo.databinding.ActivityAddTaskBinding
import com.anandparmar.todo.viewmodel.TaskViewModel

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        viewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        setupViewAndClickListeners()
    }

    @SuppressLint("ShowToast")
    private fun setupViewAndClickListeners() {
        binding.saveButton.setOnClickListener {
            if (binding.taskDetailsET.text.toString().isEmpty()) {
                Toast.makeText(this, "Give a name to the task...", Toast.LENGTH_LONG).show()
            } else {
                val task = Task(0, binding.taskDetailsET.text.toString())
                viewModel.saveTask(task)
                finish()
            }
        }

        binding.dateAndTimeLayout.visibility = View.GONE
        binding.reminderCheckbox.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) {
                binding.dateAndTimeLayout.visibility = View.VISIBLE
            } else {
                binding.dateAndTimeLayout.visibility = View.GONE
            }
        }
    }
}
