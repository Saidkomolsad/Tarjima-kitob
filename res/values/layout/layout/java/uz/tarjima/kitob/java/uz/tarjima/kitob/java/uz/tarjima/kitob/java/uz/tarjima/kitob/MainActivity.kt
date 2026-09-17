package uz.tarjima.kitob

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : Activity() {

    private lateinit var adapter: PhraseAdapter
    private var currentCategory = "Barchasi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.spinnerCategory)
        val editSearch = findViewById<EditText>(R.id.editSearch)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            PhrasesData.categories
        )
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentCategory = PhrasesData.categories[position]
                applyFilter(editSearch.text.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        adapter = PhraseAdapter { phrase ->
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("phrase", "${phrase.uz} — ${phrase.ru}")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Nusxalandi", Toast.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilter(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        applyFilter("")
    }

    private fun applyFilter(query: String) {
        val q = query.trim().lowercase()
        val filtered = PhrasesData.all.filter {
            (currentCategory == "Barchasi" || it.category == currentCategory) &&
                    (q.isEmpty() || it.uz.lowercase().contains(q) || it.ru.lowercase().contains(q))
        }
        adapter.update(filtered)
    }
}
