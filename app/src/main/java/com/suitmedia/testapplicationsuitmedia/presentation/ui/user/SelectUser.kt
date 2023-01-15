package com.suitmedia.testapplicationsuitmedia.presentation.ui.user

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suitmedia.testapplicationsuitmedia.R
import com.suitmedia.testapplicationsuitmedia.data.storage.DataStore
import com.suitmedia.testapplicationsuitmedia.databinding.ActivitySelectUserBinding
import com.suitmedia.testapplicationsuitmedia.presentation.bussiness.UserViewModel
import com.suitmedia.testapplicationsuitmedia.presentation.ui.adapter.ListUsersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectUser : AppCompatActivity() {

    private val binding : ActivitySelectUserBinding by lazy {
        ActivitySelectUserBinding.inflate(layoutInflater)
    }

    private val userViewModel : UserViewModel by viewModels()

    private val listUsersAdapter : ListUsersAdapter by lazy {
        ListUsersAdapter()
    }

    private var currentPage = 1
    private var maxPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.apply {
            include.btnBack.setOnClickListener {
                onBackPressed()
            }
            listUsersAdapter.actionClick(object : ListUsersAdapter.OnUserItem {
                override fun userClicked(fullName: String) {
                    DataStore.fullName = fullName
                    finish()
                }
            })
        }
    }

    private fun observeData() {
        binding.apply {
            include.tvTitle.setText(R.string.text_title_third)
            userViewModel.getListUsers(currentPage)
            userViewModel.listUsers.observe(this@SelectUser) { listUsers ->
                val data = listUsers.payload?.data
                if (data.isNullOrEmpty().not()) data?.let { listUsersAdapter.setItem(it) }
                else maxPage = true
            }
            userViewModel.loadingState.observe(this@SelectUser) {
                if (it) {
                    pbLoading.visibility = View.VISIBLE
                    rvListUsers.visibility = View.GONE
                } else {
                    pbLoading.visibility = View.GONE
                    rvListUsers.visibility = View.VISIBLE
                }
            }
            userViewModel.errorState.observe(this@SelectUser) {
                if (it.first) {
                    tvError.visibility = View.VISIBLE
                    tvError.text = it.second?.message
                    rvListUsers.visibility = View.GONE
                } else {
                    tvError.visibility = View.GONE
                    rvListUsers.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initList() {
        binding.rvListUsers.apply {
            layoutManager = LinearLayoutManager(this@SelectUser)
            adapter = listUsersAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = listUsersAdapter.itemCount
                    val lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (lastVisibleItem + 4 >= totalItemCount && !maxPage) {
                        currentPage += 1
                        userViewModel.getListUsers(currentPage)
                    }
                }
            })
        }
    }

}