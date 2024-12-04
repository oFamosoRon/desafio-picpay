package com.picpay.desafio.android.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.di.MainViewModelFactory
import com.picpay.desafio.android.di.UserRepositoryProvider.userRepository
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = MainViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        collectUiStateChanges()
    }

    private fun collectUiStateChanges() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                updateScreen(state)
            }
        }
    }

    private fun updateScreen(state: UiState) {
        when (state) {
            is UiState.Success -> {
                setSuccessState(state.users)
            }

            is UiState.Error -> {
                setErrorState()
            }

            UiState.Loading -> {
                setLoadingState()
            }
        }
    }

    private fun setLoadingState() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun setSuccessState(users: List<User>) {
        adapter.users = users
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun setErrorState() {
        val message = getString(R.string.error)
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
            .show()
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }
}
