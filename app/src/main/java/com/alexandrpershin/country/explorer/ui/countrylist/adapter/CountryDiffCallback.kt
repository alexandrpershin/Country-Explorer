package com.alexandrpershin.country.explorer.ui.countrylist.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.alexandrpershin.country.explorer.model.Country

class CountryDiffCallback(
    private var newResults: List<Country>,
    private var oldResults: List<Country>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldResults.size
    }

    override fun getNewListSize(): Int {
        return newResults.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldResults[oldItemPosition].name == newResults[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldResults[oldItemPosition].name == newResults[newItemPosition].name
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}