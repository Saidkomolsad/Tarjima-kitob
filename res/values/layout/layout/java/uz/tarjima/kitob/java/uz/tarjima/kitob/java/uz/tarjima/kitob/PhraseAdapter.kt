package uz.tarjima.kitob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhraseAdapter(
    private val onClick: (Phrase) -> Unit
) : RecyclerView.Adapter<PhraseAdapter.VH>() {

    var items: List<Phrase> = emptyList()

    fun update(newItems: List<Phrase>) {
        items = newItems
        notifyDataSetChanged()
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val textUz: TextView = view.findViewById(R.id.textUz)
        val textRu: TextView = view.findViewById(R.id.textRu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_phrase, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val phrase = items[position]
        holder.textUz.text = phrase.uz
        holder.textRu.text = phrase.ru
        holder.itemView.setOnClickListener { onClick(phrase) }
    }
}
