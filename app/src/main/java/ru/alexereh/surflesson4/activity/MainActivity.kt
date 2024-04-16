package ru.alexereh.surflesson4.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import ru.alexereh.surflesson4.R
import ru.alexereh.surflesson4.databinding.ActivityMainBinding
import ru.alexereh.surflesson4.prefs.ExtrasConstants

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.registerButton.setOnClickListener {
            val newIntent = Intent(this, EnterFirstNameActivity::class.java)
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(newIntent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            val extras = it.extras!!
            val firstName = extras.getString(ExtrasConstants.FIRST_NAME_KEY)!!
            val lastName = extras.getString(ExtrasConstants.LAST_NAME_KEY)!!
            val age = extras.getInt(ExtrasConstants.AGE_KEY)
            Toast.makeText(
                this,
                "Добро пожаловать, $firstName $lastName",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}