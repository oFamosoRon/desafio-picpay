package com.picpay.desafio.android.ui

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.PicPayApi
import com.picpay.desafio.android.data.PicPayService
import com.picpay.desafio.android.data.UserRepositoryImpl
import com.picpay.desafio.android.domain.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    private val api: PicPayApi by lazy {
        PicPayService.api
    }

    private val userRepository: UserRepository by lazy {
        UserRepositoryImpl(api)
    }

    override fun onResume() {
        super.onResume()

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        progressBar.visibility = View.VISIBLE

        this.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val users = userRepository.getUsers()
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    adapter.users = users
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    val message = getString(R.string.error)

                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE

                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
