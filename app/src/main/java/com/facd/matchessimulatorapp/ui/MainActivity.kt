package com.facd.matchessimulatorapp.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.facd.matchessimulatorapp.R
import com.facd.matchessimulatorapp.data.MatchesApi
import com.facd.matchessimulatorapp.databinding.ActivityMainBinding
import com.facd.matchessimulatorapp.domain.Match
import com.facd.matchessimulatorapp.ui.adapter.MatchesAdapter
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var matchesApi: MatchesApi
    private var matchesAdapter: MatchesAdapter = MatchesAdapter(Collections.emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupHttpClient()
        setupMatchesList()
        setupMatchesRefresh()
        setupFloatingActionButton()
    }

    private fun setupHttpClient() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://f3lp.github.io/matches-simulator-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        matchesApi = retrofit.create(MatchesApi::class.java)
    }

    private fun setupMatchesList() {
        binding.rvMatches.setHasFixedSize(true)
        binding.rvMatches.layoutManager = LinearLayoutManager(this)
        binding.rvMatches.adapter = matchesAdapter

        findMatchesFromApi()
    }

    private fun setupMatchesRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(this::findMatchesFromApi)
    }

    private fun findMatchesFromApi() {
        binding.swipeRefreshLayout.isRefreshing = true
        matchesApi.getMatches().enqueue(object : Callback<List<Match>> {

            override fun onResponse(call: Call<List<Match>>, response: Response<List<Match>>) {
                if (response.isSuccessful) {
                    val matches = response.body()
                    if (matches != null) {
                        matchesAdapter = MatchesAdapter(matches)
                        binding.rvMatches.adapter = matchesAdapter
                    }
                } else {
                    showErrorMessage()
                }
                binding.swipeRefreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Match>>, t: Throwable) {
                showErrorMessage()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun setupFloatingActionButton() {
        binding.floatingActionButton.setOnClickListener { v ->
            v.animate()
                .rotation(360f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        for (i in 0 until matchesAdapter.itemCount) {
                            val match = matchesAdapter.getMatches()[i]
                            match.homeTeam.score = Random.nextInt(match.homeTeam.stars + 1)
                            match.awayTeam.score = Random.nextInt(match.awayTeam.stars + 1)
                            matchesAdapter.notifyDataSetChanged()
                        }
                    }
                })
        }
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.floatingActionButton, R.string.error_api, Snackbar.LENGTH_LONG).show()
    }
}