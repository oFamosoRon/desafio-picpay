package com.picpay.desafio.android.ui.users_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    private val viewModel: UsersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_users, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.user_list_progress_bar)

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        collectUiStateChanges()

        return view
    }

    private fun collectUiStateChanges() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
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
                setErrorState(state.message)
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

    private fun setErrorState(exceptionMessage: String?) {
        val message = getString(R.string.error)
        Toast.makeText(requireActivity(), exceptionMessage ?: message, Toast.LENGTH_SHORT).show()
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }
}