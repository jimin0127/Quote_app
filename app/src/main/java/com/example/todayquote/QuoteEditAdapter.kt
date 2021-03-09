package com.example.todayquote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class QuoteEditAdapter(private val dataList: List<Quote>) :
    RecyclerView.Adapter<QuoteEditAdapter.QuoteItemViewHolder>()
{
    class QuoteItemViewHolder(val view: View)
        : RecyclerView.ViewHolder(view) {
        lateinit var quote : Quote
        val quoteTextEdit = view.findViewById<EditText>(R.id.quote_text_edit)
        val quoteFromEdit = view.findViewById<EditText>(R.id.quote_from_edit)
        val quoteDeleteBtn = view.findViewById<Button>(R.id.quote_delete_btn)
        val quoteModifyBtn = view.findViewById<Button>(R.id.quote_modify_btn)

        // 주 생성자 호출 이후 바로 실행되는 코드 블록
        init {
            val pref = view.context.getSharedPreferences("quotes", Context.MODE_PRIVATE)

            quoteDeleteBtn.setOnClickListener {
                quoteTextEdit.setText("")
                quoteFromEdit.setText("")
                quote.text = ""
                quote.from = ""
                
                // adapterPosition을 사용해야 함
                val pos = this.adapterPosition
                Quote.removeQuoteFromPreference(pref, pos)
                Toast.makeText(view.context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }

            /*
            quoteModifyBtn.setOnClickListener {
               // Q) 구현
            }
            */

            quoteModifyBtn.setOnClickListener {
                quote.text = quoteTextEdit.text.toString()
                quote.from = quoteFromEdit.text.toString()
                val pos = this.adapterPosition
                Quote.saveToPreference(pref, pos, quote.text, quote.from)
                Toast.makeText(view.context, "수정되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        fun bind(q: Quote) {
            quote = q
            quoteTextEdit.setText(quote.text)
            quoteFromEdit.setText(quote.from)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(viewType, parent, false)

        return QuoteItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteItemViewHolder, position: Int) {
        val data = dataList[position]

        /*
        holder.view.findViewById<Button>(R.id.quote_delete_btn)
            .setOnClickListener {

            // 전달받은 position은 과거의 위치일 수 있으므로 onClickListener 내부에서 사용은 위험!
            Quote.removeQuoteFromPreference(pref, position)

            // 대신 뷰 홀더 객체의 getAdapterPosition 메소드 호출하여 현재 목록에서의 위치를 다시 받아오도록 함
            val pos = holder.adapterPosition
            Quote.removeQuoteFromPreference(pref, pos)
        }
        */
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.quote_edit_list_item
    }
}