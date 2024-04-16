package ru.alexereh.surflesson4.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import ru.alexereh.surflesson4.R
import ru.alexereh.surflesson4.databinding.ActivityEnterFirstNameBinding
import ru.alexereh.surflesson4.prefs.ExtrasConstants

class EnterFirstNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnterFirstNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_first_name)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val startForResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val resultIntent = Intent(this, MainActivity::class.java).apply {
                    val age = result.data!!.extras!!.getInt(ExtrasConstants.AGE_KEY)
                    val lastName = result.data!!.extras!!.getString(ExtrasConstants.LAST_NAME_KEY)
                    putExtra(ExtrasConstants.AGE_KEY, age)
                    putExtra(ExtrasConstants.LAST_NAME_KEY, lastName)
                    putExtra(ExtrasConstants.FIRST_NAME_KEY, binding.editText.text.toString())
                }
                startActivity(resultIntent)
                finishAffinity()
            }
        }
        binding.nextBtn.setOnClickListener {
            val isBlank = binding.editText.text.toString().isBlank()
            if (isBlank) {
                Toast.makeText(this, "Вы не ввели имя", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, EnterLastNameActivity::class.java)
            startForResultLauncher.launch(intent)
        }
        binding.prevBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finishAffinity()
        }
        binding.cancelBtn.setOnClickListener {
            finishAffinity()
        }
    }
}