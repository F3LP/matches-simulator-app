package com.facd.matchessimulatorapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facd.matchessimulatorapp.databinding.MatchItemBinding
import com.facd.matchessimulatorapp.domain.Match
import com.facd.matchessimulatorapp.ui.DetailActivity

class MatchesAdapter(private val matches: List<Match>) : RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MatchItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = matches[position]

        holder.bind(match)
    }

    override fun getItemCount() = matches.size

    fun getMatches() = matches


    class ViewHolder(private val binding: MatchItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val context = itemView.context

        fun bind(match: Match) {
            binding.tvHomeTeam.text = match.homeTeam.name
            binding.tvAwayTeam.text = match.awayTeam.name

            if(match.homeTeam.score != null && match.awayTeam.score!= null) {
                binding.tvScoreHomeTeam.text = match.homeTeam.score.toString()
                binding.tvScoreAwayTeam.text = match.awayTeam.score.toString()
            }

            Glide.with(context).load(match.homeTeam.image).circleCrop().into(binding.ivHomeTeam)
            Glide.with(context).load(match.awayTeam.image).circleCrop().into(binding.ivAwayTeam)

            this.itemView.setOnClickListener { view ->
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.Extras.MATCH, match)
                context.startActivity(intent)
            }
        }

    }

}