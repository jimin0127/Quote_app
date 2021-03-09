package com.example.todayquote

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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

        /*
        // 수정 가능한 Quote를 저장할 리스트 객체 생성하고 명언 객체 3개 넣어보기
        // 명언 내용은 맘대로
        var quotes = mutableListOf<Quote>()
        quotes.add(Quote(1, "명언 내용 1", "출처 1"))
        quotes.add(Quote(2, "명언 내용 2", "출처 2"))
        quotes.add(Quote(3, "명언 내용 3", "출처 3"))

        var randomIndex = Random.nextInt(0, 3)
        var randomQuote = quotes[randomIndex]
        // var randomQuote = quotes.get(randomIndex)

        // 뷰 객체를 이용해서 출력
        quoteText.text = randomQuote.text
        // quoteText.setText(randomQuote.text)
        quoteFrom.text = randomQuote.from
        // Log.d("mytag", quoteText.getText().toString())
        Log.d("mytag", quoteText.text.toString())
        */



        // resources
        // getResources()

        /*
        val sp = getSharedPreferences("filename", Context.MODE_PRIVATE)

        // 데이터를 저장
        val editor = sp.edit()

        // 키, 값 쌍이 필요 (자바의 맵, 파이썬의 딕셔너리)
        editor.putInt("key1", 1)
        editor.putString("key2", "Hello")
        editor.putBoolean("make_alarm", true)
        editor.putInt("volume", 100)

        // 실제 데이터 저장을 위해서는 apply 메소드 호출
        editor.apply()
        // editor.commit()

        // 지우기
        // editor.remove("key1")
        // editor.clear()

        // 덮어쓰기(overwrite, = 수정)
        editor.putString("key2", "World")
        editor.remove("key2")
        editor.apply()

        var value1 = sp.getInt("key1", -1)
        var value2 = sp.getString("key2", "default")
        // sp.getString("volume", null)
        */
    }
}

