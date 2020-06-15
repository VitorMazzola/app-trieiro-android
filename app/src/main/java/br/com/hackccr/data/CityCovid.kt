package br.com.hackccr.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CityCovid(
    @SerializedName("codigo_ibge") val ibgeCode : Int,
    @SerializedName("nome") val name : String,
    @SerializedName("latitude") val lat : Double,
    @SerializedName("longitude") val lng : Double,
    @SerializedName("casos") val cases : Long,
    @SerializedName("incidencia") val incidence : String,
    @SerializedName("distance") val distance : Long
) : Serializable {

    fun getIncidence() : IncidenceEnum? {
        return when(incidence) {
            IncidenceEnum.LOW.value -> IncidenceEnum.LOW
            IncidenceEnum.MEDIUM.value -> IncidenceEnum.MEDIUM
            IncidenceEnum.HIGH.value -> IncidenceEnum.HIGH
            else ->  null
        }
    }
}