package com.alexandrpershin.country.explorer.ui.countrylist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexandrpershin.country.explorer.R
import com.alexandrpershin.country.explorer.databinding.ItemCountryBinding
import com.alexandrpershin.country.explorer.extensions.loadSvgFromUrl
import com.alexandrpershin.country.explorer.model.Country
import com.alexandrpershin.country.explorer.utils.getLocalizedCountryName


class CountriesAdapter(
    private val context: Context,
    private val countries: ArrayList<Country> = arrayListOf()
) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    var onItemClick: ((countryName: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemCountryBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = countries.size

    fun updateData(newCountries: List<Country>) {

        newCountries.let {

            DiffUtil.calculateDiff(CountryDiffCallback(it, countries)).dispatchUpdatesTo(this)

            this.countries.clear()
            this.countries.addAll(it)
        }
    }

    inner class ViewHolder(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val country = countries[position]
            with(binding) {

                tvCountryName.text = getLocalizedCountryName(context, country)

                ivCountryFlag.loadSvgFromUrl(
                    country.flag,
                    R.drawable.ic_country_flag_placeholder,
                    50,
                    50
                )

                itemView.setOnClickListener {
                    onItemClick?.invoke(country.name)
                }
            }
        }

    }

}



