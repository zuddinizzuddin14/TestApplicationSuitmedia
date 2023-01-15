package com.suitmedia.testapplicationsuitmedia.presentation.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.suitmedia.testapplicationsuitmedia.databinding.ActivityLoginBinding
import com.suitmedia.testapplicationsuitmedia.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding : ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListener()
    }

    private fun setClickListener() {
        binding.apply {

            btnCheck.setOnClickListener {
                val palindrome = etPalindrome.text.toString().trim()
                if (validationPalindrome(palindrome)) {
                    if (palindromeCheck(palindrome)) dialogMessage("Is Palindrome")
                    else dialogMessage("Not Palindrome")
                }
            }

            btnNext.setOnClickListener {
                val name = etName.text.toString().trim()

                if (validationName(name)) {
                    startActivity(HomeActivity.userWelcome(this@LoginActivity, name))
                }
            }

        }
    }

    private fun validationName(
        name: String
    ): Boolean {
        var isInputValid = true

        binding.apply {
            if (name.isEmpty()) {
                isInputValid = false
                tilEtName.isErrorEnabled = true
                tilEtName.error = "Name is Empty"
            } else {
                tilEtName.isErrorEnabled = false
            }
        }

        return isInputValid
    }

    private fun validationPalindrome(
        palindrome: String
    ): Boolean {
        var isInputValid = true

        binding.apply {
            if (palindrome.isEmpty()) {
                isInputValid = false
                tilEtPalindrome.isErrorEnabled = true
                tilEtPalindrome.error = "Palindrome is Empty"
            } else {
                tilEtPalindrome.isErrorEnabled = false
            }
        }

        return isInputValid
    }

    private fun dialogMessage(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun palindromeCheck(text: String): Boolean {
        val cleaned = text.replace("[^a-zA-Z0-9]".toRegex(), "").lowercase()
        return cleaned == cleaned.reversed()
    }

}