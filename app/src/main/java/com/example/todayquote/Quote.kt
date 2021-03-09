package com.example.todayquote

import android.content.SharedPreferences

// 데이터 클래스 정의
// data class Person(var name:String, var age:Int, val gender:String)
// 데이터 클래스 이름 : Quote
// 받아야 할 속성 이름 (idx, text, from)
// idx는 저장된 위치를 저장할 정수, text는 명언 내용, from은 출처
data class Quote(var idx : Int, var text : String, var from : String) {
    companion object {
        fun saveToPreference(
            pref: SharedPreferences,
            idx: Int, text: String,
            from: String="") : Quote {
            // SharedPreference 객체에 명언 데이터 저장
            val editor = pref.edit()

            // 키, 값 저장
            // "0.text"에다가 0번 명언 내용 저장
            editor.putString("$idx.text", text)
            // "0.from"에다가 0번 명언 출처 저장
            editor.putString("$idx.from", from)
            editor.apply()

            // 이후 Quote 객체를 생성하여 반환
            return Quote(idx, text, from)
        }

        fun removeQuoteFromPreference(pref: SharedPreferences, idx: Int) {
            // 해당 위치의 데이터 제거
            val editor = pref.edit()

            editor.remove("$idx.text")
            editor.remove("$idx.from")

            editor.apply()
        }

        fun getQuotesFromPreference(pref: SharedPreferences) : List<Quote> {
            val quotes = mutableListOf<Quote>()

            // val range1 = 0 until 20
            // val range2 = 0 .. 20

            for(i in 0 until 20) {
                val quoteText = pref.getString("$i.text", "")!!
                val quoteFrom = pref.getString("$i.from", "")!!
                if(quoteText.isNotBlank()) {
                    quotes.add(Quote(i, quoteText, quoteFrom))
                }
            }

            return quotes
        }
    }
}










