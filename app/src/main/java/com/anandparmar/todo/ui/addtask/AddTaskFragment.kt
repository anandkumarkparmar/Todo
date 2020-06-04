package com.anandparmar.todo.ui.addtask

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.anandparmar.todo.R

class AddTaskFragment : AppCompatActivity() {

    private lateinit var viewModel: AddTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.fragment_home)
        viewModel = ViewModelProviders.of(this).get(AddTaskViewModel::class.java)
    }
}
