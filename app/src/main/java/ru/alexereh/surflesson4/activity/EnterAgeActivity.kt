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
import ru.alexereh.surflesson4.databinding.ActivityEnterAgeBinding
import ru.alexereh.surflesson4.prefs.ExtrasConstants

class EnterAgeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnterAgeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_age)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.nextBtn.setOnClickListener {
            val editTextValue = binding.editText.text.toString().toIntOrNull()
            if (editTextValue == null) {
                Toast.makeText(this, "Введите возраст!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val resultIntent = Intent().apply {
                putExtra(ExtrasConstants.AGE_KEY, editTextValue)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        binding.prevBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        binding.cancelBtn.setOnClickListener {
            finishAffinity()
        }
    }
}