package com.anandparmar.todo.ui

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anandparmar.todo.R
import com.anandparmar.todo.utils.cancelNotifications
import com.anandparmar.todo.utils.sendNotification
import com.anandparmar.todo.viewmodel.TaskViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModelAndListener()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        setupFab()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setupUserInDrawer(navView.getHeaderView(0))
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_logout -> {
                    AuthUI.getInstance().signOut(this)
                    true
                }
            }
            false
        }

        val notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelNotifications()
    }

    private fun setupViewModelAndListener() {
        viewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        viewModel.authenticationState.observe(this, Observer {
            if (it == null) {
                openLoginActivity()
            }
        })
    }

    private fun setupUserInDrawer(navHeaderView: View) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            user.photoUrl?.let {
                Picasso.get().load(it).placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher).into(navHeaderView.userImageView)
            }
            user.displayName?.let {
                navHeaderView.userName.text = it
            }
            user.email?.let {
                navHeaderView.userEmail.text = it
            }
        }
    }

    private fun setupFab() {
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            openAddTaskActivity()
        }
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun openAddTaskActivity() {
        startActivity(Intent(this, AddTaskActivity::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
