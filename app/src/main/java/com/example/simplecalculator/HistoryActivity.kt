
package com.example.pasteldreamcalculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pasteldreamcalculator.viewmodel.CalculatorViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val recyclerView =
            findViewById<RecyclerView>(R.id.recyclerViewHistory)

        val btnClearAll =
            findViewById<Button>(R.id.btnClearAll)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = HistoryAdapter(
            onItemClick = { history ->
                val intent = Intent().apply {
                    putExtra("REUSED_RESULT", history.result)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            },
            onDeleteClick = { id ->
                viewModel.deleteHistory(id)
            }
        )

        recyclerView.adapter = adapter

        btnClearAll.setOnClickListener {
            viewModel.clearAllHistory()
        }

        lifecycleScope.launch {
            viewModel.historyList.collectLatest { list ->
                adapter.submitList(list)
            }
        }
    }
}
