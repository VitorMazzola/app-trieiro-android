package br.com.hackccr.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Point(
    @SerializedName("id") val id : String,
    @SerializedName("nome_fantasia") val fantasyName : String,
    @SerializedName("categoria") val category : String,
    @SerializedName("responsavel") val responsible : String,
    @SerializedName("telefone") val telephone : String,
    @SerializedName("sg_uf") val sg_uf : String,
    @SerializedName("no_uf") val no_uf : String,
    @SerializedName("municipio") val city : String,
    @SerializedName("br") val br : String,
    @SerializedName("km") val km : String,
    @SerializedName("periodo") val period : String,
    @SerializedName("sempre_aberto") val alwaysOpen : String,
    @SerializedName("horario_abertura") val openingTime : String,
    @SerializedName("horario_fechamento") val closingTime : String,
    @SerializedName("observacoes") val observations : String,
    @SerializedName("status") val status : String,
    @SerializedName("longitude") val lng : String,
    @SerializedName("latitude") val lat : String
) : Serializable {

    fun getCategory() : CategoryEnum {
        return when(category) {
            CategoryEnum.PRF_STATION.value -> CategoryEnum.PRF_STATION
            CategoryEnum.BUTTERFLY.value -> CategoryEnum.BUTTERFLY
            CategoryEnum.ACCOMMODATION.value -> CategoryEnum.ACCOMMODATION
            CategoryEnum.GAS_STATION.value -> CategoryEnum.GAS_STATION
            CategoryEnum.RESTAURANT.value -> CategoryEnum.RESTAURANT
            CategoryEnum.MECHANICAL_WORKSHOP.value -> CategoryEnum.MECHANICAL_WORKSHOP
            CategoryEnum.STOP_POINT.value -> CategoryEnum.STOP_POINT
            CategoryEnum.CONVENIENCE_STORE.value -> CategoryEnum.CONVENIENCE_STORE
            else -> CategoryEnum.DEFAULT
        }
    }
}