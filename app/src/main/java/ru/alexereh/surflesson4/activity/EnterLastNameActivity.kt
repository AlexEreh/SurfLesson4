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
import ru.alexereh.surflesson4.databinding.ActivityEnterLastNameBinding
import ru.alexereh.surflesson4.prefs.ExtrasConstants

class EnterLastNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnterLastNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_last_name)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val startForResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val resultIntent = Intent().apply {
                    putExtra(
                        ExtrasConstants.AGE_KEY,
                        result.data!!.extras!!.getInt(ExtrasConstants.AGE_KEY)
                    )
                    putExtra(
                        ExtrasConstants.LAST_NAME_KEY,
                        binding.editText.text.toString()
                    )
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
        binding.nextBtn.setOnClickListener {
            val isBlank = binding.editText.text.toString().isBlank()
            if (isBlank) {
                Toast.makeText(this, "Вы не ввели фамилию", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, EnterAgeActivity::class.java)
            startForResultLauncher.launch(intent)
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