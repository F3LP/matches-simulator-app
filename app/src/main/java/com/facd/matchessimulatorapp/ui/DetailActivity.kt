package com.facd.matchessimulatorapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.facd.matchessimulatorapp.databinding.ActivityDetailBinding
import com.facd.matchessimulatorapp.domain.Match

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    object Extras {
        const val MATCH = "EXTRA_MATCH"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMatchFromExtra()

    }

    private fun loadMatchFromExtra() {
        intent?.extras?.getParcelable<Match>(Extras.MATCH).let {
            Glide.with(this).load(it?.place?.image).into((binding.ivPlace))
            supportActionBar?.title = it?.place?.name

            Glide.with(this).load(it?.homeTeam?.image).into((binding.ivHomeTeam))
            Glide.with(this).load(it?.awayTeam?.image).into((binding.ivAwayTeam))

            binding.tvDescription.text = it?.description
            binding.tvHomeTeamName.text = it?.homeTeam?.name
            binding.tvAwayTeamName.text = it?.awayTeam?.name
            binding.tvHomeTeamScore.text = it?.homeTeam?.score?.toString()
            binding.tvAwayTeamScore.text = it?.awayTeam?.score?.toString()
            binding.rbHomeTeamStars.rating = it?.homeTeam?.stars?.toFloat()!!
            binding.rbAwayTeamStars.rating = it.awayTeam.stars.toFloat()
        }
    }

}