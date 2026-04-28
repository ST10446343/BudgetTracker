package com.example.budgettracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.budgettracker.data.AppDatabase
import com.example.budgettracker.data.Expense
import kotlinx.coroutines.launch

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_expense)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "expense-db"
        ).build()

        val amount = findViewById<EditText>(R.id.etAmount)
        val desc = findViewById<EditText>(R.id.etDescription)
        val date = findViewById<EditText>(R.id.etDate)
        val startTime = findViewById<EditText>(R.id.etStartTime)
        val endTime = findViewById<EditText>(R.id.etEndTime)
        val category = findViewById<EditText>(R.id.etCategory)
        val saveBtn = findViewById<Button>(R.id.btnSave)

        val minGoal = findViewById<EditText>(R.id.etMinGoal)
        val maxGoal = findViewById<EditText>(R.id.etMaxGoal)
        val searchCategory = findViewById<EditText>(R.id.etSearchCategory)
        val totalBtn = findViewById<Button>(R.id.btnTotal)
        val txtTotal = findViewById<TextView>(R.id.txtTotal)

        saveBtn.setOnClickListener {
            val amt = amount.text.toString().trim()
            val descText = desc.text.toString().trim()
            val dt = date.text.toString().trim()
            val start = startTime.text.toString().trim()
            val end = endTime.text.toString().trim()
            val cat = category.text.toString().trim()

            if (amt.isEmpty() || descText.isEmpty() || dt.isEmpty() ||
                start.isEmpty() || end.isEmpty() || cat.isEmpty()) {

                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()

            } else {
                val expense = Expense(
                    amount = amt.toDouble(),
                    description = descText,
                    date = dt,
                    startTime = start,
                    endTime = end,
                    category = cat
                )

                lifecycleScope.launch {
                    db.expenseDao().insert(expense)

                    Toast.makeText(this@AddExpenseActivity, "Saved!", Toast.LENGTH_SHORT).show()

                    amount.text.clear()
                    desc.text.clear()
                    date.text.clear()
                    startTime.text.clear()
                    endTime.text.clear()
                    category.text.clear()
                }
            }
        }

        totalBtn.setOnClickListener {
            val cat = searchCategory.text.toString().trim()

            if (cat.isEmpty()) {
                Toast.makeText(this, "Enter a category", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val total = db.expenseDao().getTotalByCategory(cat) ?: 0.0

                    val min = minGoal.text.toString()
                    val max = maxGoal.text.toString()

                    txtTotal.text =
                        "Total spent on $cat: R$total\nMinimum goal: R$min\nMaximum goal: R$max"
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}