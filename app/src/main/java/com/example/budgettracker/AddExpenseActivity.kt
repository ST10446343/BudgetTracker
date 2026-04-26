package com.example.budgettracker


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.budgettracker.data.AppDatabase

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_expense)

        val db = androidx.room.Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "expense-db"
        ).build()

        val amount = findViewById<EditText>(R.id.etAmount)
        val desc = findViewById<EditText>(R.id.etDescription)
        val date = findViewById<EditText>(R.id.etDate)
        val saveBtn = findViewById<Button>(R.id.btnSave)

        saveBtn.setOnClickListener {

            val amt = amount.text.toString()
            val description = desc.text.toString()
            val dt = date.text.toString()

            if (amt.isNotEmpty() && description.isNotEmpty() && dt.isNotEmpty()) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}