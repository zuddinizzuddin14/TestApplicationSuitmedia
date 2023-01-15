package com.suitmedia.testapplicationsuitmedia.presentation.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.suitmedia.testapplicationsuitmedia.R
import com.suitmedia.testapplicationsuitmedia.data.storage.DataStore
import com.suitmedia.testapplicationsuitmedia.databinding.ActivityHomeBinding
import com.suitmedia.testapplicationsuitmedia.presentation.ui.user.SelectUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding : ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.apply {
            btnChooseUser.setOnClickListener {
                startActivity(Intent(this@HomeActivity, SelectUser::class.java))
            }
            include.btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun observeData() {
        binding.apply {
            include.tvTitle.setText(R.string.text_title_second)
            tvName.text = intent.getStringExtra(NAME)
        }
    }

    override fun onResume() {
        super.onResume()
        val fullName = DataStore.fullName
        if (fullName.isNullOrEmpty().not()) {
            binding.tvSelectedUserName.text = fullName
        }
    }

    override fun onDestroy() {
        DataStore.fullName = null
        super.onDestroy()
    }

    companion object {
        private const val NAME = "NAME"

        @JvmStatic
        fun userWelcome(context: Context, name: String) : Intent =
            Intent(context, HomeActivity::class.java).apply {
                putExtra(NAME, name)
            }
    }
}