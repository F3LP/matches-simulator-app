package com.facd.matchessimulatorapp.data

import com.facd.matchessimulatorapp.domain.Match
import retrofit2.Call
import retrofit2.http.GET

interface MatchesApi {

    @GET("matches.json")
    fun getMatches(): Call<List<Match>>
}