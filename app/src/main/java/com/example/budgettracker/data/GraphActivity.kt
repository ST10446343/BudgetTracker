package com.example.budgettracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.budgettracker.data.AppDatabase
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.launch

class GraphActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        barChart = findViewById(R.id.barChart)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "expense-db"
        ).build()

        lifecycleScope.launch {

            val expenses = db.expenseDao().getAll()

            val categoryTotals = mutableMapOf<String, Float>()

            for (expense in expenses) {

                val currentTotal =
                    categoryTotals[expense.category] ?: 0f

                categoryTotals[expense.category] =
                    currentTotal + expense.amount.toFloat()
            }

            val entries = ArrayList<BarEntry>()

            var index = 1f

            for ((category, total) in categoryTotals) {

                entries.add(
                    BarEntry(
                        index,
                        total
                    )
                )

                index++
            }

            val dataSet = BarDataSet(
                entries,
                "Amount Spent Per Category"
            )

            val data = BarData(dataSet)

            barChart.data = data
            barChart.description.text =
                "Budget Tracker Spending Graph"

            barChart.animateY(1000)

            barChart.invalidate()
        }
    }
}