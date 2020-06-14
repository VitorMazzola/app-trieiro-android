package br.com.hackccr.api

import br.com.hackccr.data.Point
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface Api {
    @GET("pontos")
    fun getPoints(@Query("uf") uf: String, @Query("municipio") city: String) : Observable<List<Point>>
}