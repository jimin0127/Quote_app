package com.example.todayquote

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuoteAdapter(val dataList: List<Quote>) :
    RecyclerView.Adapter<QuoteAdapter.QuoteItemViewHolder>()
{
    class QuoteItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        lateinit var quote: Quote
        val quoteText = view.findViewById<TextView>(R.id.quote_text)
        val quoteFrom = view.findViewById<TextView>(R.id.quote_from)

        // Q) 작동하도록 레이아웃 수정
        val shareBtn = view.findViewById<Button>(R.id.quote_share_btn)
        val fromSearchBtn = view.findViewById<Button>(R.id.quote_from_search_btn)

        init {
            shareBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)

                intent.putExtra(Intent.EXTRA_TITLE, "힘이 되는 명언")
                intent.putExtra(Intent.EXTRA_SUBJECT, "힘이 되는 명언")
                intent.putExtra(Intent.EXTRA_TEXT, "${quote.text}\n 출처 : ${quote.from}")
                intent.type = "text/plain"

                val chooser = Intent.createChooser(intent, "명언 공유")

                it.context.startActivity(chooser)
            }

            fromSearchBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/search?q=${quote.from}"))

                // val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-1234-5678"))
                // val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5666,126.9784"))
                // val intent = Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"))

                it.context.startActivity(intent)
            }
        }

        fun bind(q: Quote) {
            quote = q
            quoteText.text = quote.text
            quoteFrom.text = quote.from

            if(quote.from.isBlank()) {
                fromSearchBtn.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(viewType, parent, false)

        return QuoteItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteItemViewHolder, position: Int) {
        // 뷰, 교체할 데이터
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.quote_list_item
    }
}







