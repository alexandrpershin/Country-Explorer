package com.alexandrpershin.country.explorer.ui.cutomview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.alexandrpershin.country.explorer.databinding.SearchViewBinding
import com.alexandrpershin.country.explorer.extensions.setVisible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Custom search view for search countries
* */

class CountrySearchView : ConstraintLayout {

    private lateinit var binding: SearchViewBinding

    constructor(context: Context) : super(context) {
        initComponent()
        setAllListeners()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initComponent()
        setAllListeners()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initComponent()
        setAllListeners()
    }

    private fun initComponent() {
        binding = SearchViewBinding.inflate(
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            this,
            true
        )

        binding.search.requestFocus()

    }

    fun onQueryChanged(timeMillis: Long = 500, clientTextChangedListener: (String) -> Unit) {
        var searchFor = ""
        binding.search.addTextChangedListener { query ->
            val searchText = query.toString().trim()
            if (searchText == searchFor) {
                return@addTextChangedListener
            }

            searchFor = searchText

            GlobalScope.launch(Dispatchers.Main) {
                delay(timeMillis)

                if (searchText != searchFor) {
                    return@launch
                }

                clientTextChangedListener.invoke(searchText)
            }
        }
    }

    private fun setAllListeners() {
        binding.search.addTextChangedListener {
            binding.grBtClose.setVisible(it?.isNotEmpty() ?: false)
        }

        binding.ivCloseClickable.setOnClickListener {
            binding.search.setText("")
        }

    }


}