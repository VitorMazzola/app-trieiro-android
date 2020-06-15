package br.com.hackccr.api

import br.com.hackccr.data.CityCovid
import br.com.hackccr.data.Point
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface Api {
    @GET("pontos")
    fun getPoints(@Query("latitude") lat: Double, @Query("longitude") lng: Double) : Observable<List<Point>>

    @GET("covid")
    fun getCovidCases(@Query("lat") lat: Double, @Query("long") lng: Double) : Observable<List<CityCovid>>
}