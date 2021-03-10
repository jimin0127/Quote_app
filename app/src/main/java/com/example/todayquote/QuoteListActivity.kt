package com.example.todayquote

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class QuoteListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quote_list_activity)

        val currentQuoteSize = intent.getIntExtra("quote_size", 0)

        // 토스트 이용해서 "현재 n개의 명언이 저장되어 있습니다." 출력
        Toast.makeText(this,
            "현재 ${currentQuoteSize}개의 명언이 저장되어 있습니다.",
            Toast.LENGTH_SHORT).show()

        val recyclerView = findViewById<RecyclerView>(R.id.quote_list)
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, LinearLayoutManager(this).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)


        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val pref = this.getSharedPreferences("quotes", Context.MODE_PRIVATE)
        val quotes = Quote.getQuotesFromPreference(pref)
        val adapter = QuoteAdapter(quotes)
        recyclerView.adapter = adapter


    }
}
















