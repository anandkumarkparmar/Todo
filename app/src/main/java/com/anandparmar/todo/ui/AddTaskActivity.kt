package com.anandparmar.todo.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.anandparmar.todo.R
import com.anandparmar.todo.database.Task
import com.anandparmar.todo.databinding.ActivityAddTaskBinding
import com.anandparmar.todo.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var viewModel: TaskViewModel
    val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task)
        viewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        setupViewAndClickListeners()
    }

    @SuppressLint("ShowToast")
    private fun setupViewAndClickListeners() {
        binding.dateAndTimeLayout.visibility = View.GONE
        binding.reminderCheckbox.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) {
                binding.dateAndTimeLayout.visibility = View.VISIBLE
                updateDateAndTimeETText()
            } else {
                binding.dateAndTimeLayout.visibility = View.GONE
            }
        }

        binding.dateAndTimeET.setOnClickListener {
            showDatePicker()
        }

        binding.saveButton.setOnClickListener {
            if (binding.taskDetailsET.text.toString().isEmpty()) {
                Toast.makeText(this, "Give a name to the task...", Toast.LENGTH_LONG).show()
            } else {
                val task = Task(0, binding.taskDetailsET.text.toString(), if (binding.reminderCheckbox.isChecked) calendar.timeInMillis else null)
                viewModel.saveTask(task)
                finish()
            }
        }
    }

    private fun showDatePicker() {
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, date)
            showTimePicker()
            updateDateAndTimeETText()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showTimePicker() {
        TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            updateDateAndTimeETText()
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateDateAndTimeETText() {
        val myFormat = "dd/MM/yyyy hh:mm"
        val sdf = SimpleDateFormat(myFormat)
        binding.dateAndTimeET.setText(sdf.format(calendar.time))
    }
}
