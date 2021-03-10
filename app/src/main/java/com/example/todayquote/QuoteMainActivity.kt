package com.example.todayquote

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
// import java.util.Random
import kotlin.random.Random

class QuoteMainActivity : AppCompatActivity() {
    // 생성자에서 초기화를 해주지 못하지만, 널 허용하지 않는 타입으로 설정하고 싶다면? lateinit
    // 그런데 반드시 이 객체에 접근하기 전에 이 객체를 초기화하겠다는 일종의 선언
    private lateinit var pref: SharedPreferences
    // private SharedPreferences pref;
    // 이 경우 quotes 객체에 접근하기 전 반드시 초기화 해야 함!
    private lateinit var quotes: List<Quote>
    // private lateinit var str: String

    fun initializeQuotes() {
        val initialized = pref.getBoolean("initialized", false)
        if(!initialized) {
            // ...
            Quote.saveToPreference(pref, 0, "대충 명언", "유명한 사람")
            Quote.saveToPreference(pref, 1, "착하게 살자", "착한 사람")
            Quote.saveToPreference(pref, 2, "피곤하다")

            val editor = pref.edit()
            editor.putBoolean("initialized", true)
            editor.apply()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quote_main_activity)

        // null pointer exception 발생
        // pref.getString("key1", "hello")

        // 아이디 quote_text, quote_from 뷰 객체 받아오기
        var quoteText = findViewById<TextView>(R.id.quote_text)
        var quoteFrom = findViewById<TextView>(R.id.quote_from)

        // 프리퍼런스 객체 가져오기 (파일 이름은 quotes)
        pref = this.getSharedPreferences("quotes", Context.MODE_PRIVATE)

        // 필요한 기본 명언 초기화
        initializeQuotes()

        // 20개의 명언 가져오기
        quotes = Quote.getQuotesFromPreference(pref)

        // 명언이 있다면
        if(quotes.isNotEmpty()) {
            val randomIdx = Random.nextInt(quotes.size)
            val randomQuote = quotes[randomIdx]
            quoteText.text = randomQuote.text
            quoteFrom.text = randomQuote.from
        } else {
            // 없다면
            quoteText.text = "저장된 명언이 없습니다."
            quoteFrom.text = ""
        }

        val toQuoteListBtn = findViewById<Button>(R.id.quote_list_btn)
        /*
        toQuoteListBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@QuoteMainActivity, QuoteListActivity::class.java)
            }
        })
        */

        toQuoteListBtn.setOnClickListener {
            val intent = Intent(this, QuoteListActivity::class.java)
            intent.putExtra("quote_size", quotes.size)

            startActivity(intent)
        }

        val toQuoteEditBtn = findViewById<Button>(R.id.quote_edit_btn)
        toQuoteEditBtn.setOnClickListener {
            val intent = Intent(this, QuoteEditActivity::class.java)
            startActivity(intent)
        }

        val resetQuoteBtn = findViewById<ImageButton>(R.id.reset_btn)
        resetQuoteBtn.setOnClickListener{
            if(quotes.isNotEmpty()) {
                val randomIdx = Random.nextInt(quotes.size)
                val randomQuote = quotes[randomIdx]
                quoteText.text = randomQuote.text
                quoteFrom.text = randomQuote.from
            } else {
                // 없다면
                quoteText.text = "저장된 명언이 없습니다."
                quoteFrom.text = ""
            }
        }
    }
}

