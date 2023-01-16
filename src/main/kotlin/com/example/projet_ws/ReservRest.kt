package com.example.projet_ws

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import localhost._8080.ws.Reservations

@JsonIgnoreProperties(ignoreUnknown = true)
class ReservRest {

    @JsonProperty("id_reservation")
    val id:Int = 0
    @JsonProperty("id_train")
    val idTrain:Int = 0
    @JsonProperty("id_utilisateur")
    val idUser:Int = 0
    @JsonProperty("classe")
    val classe:String = ""
    @JsonProperty("quantite")
    val quantite:Int = 0
    @JsonProperty("prix")
    val prix:Double = 0.0
    @JsonProperty("heure_depart")
    val heureDepart:String = ""
    @JsonProperty("heure_arrivee")
    val heureArrivee:String = ""
    @JsonProperty("lieu_depart")
    val lieuDepart:String = ""
    @JsonProperty("lieu_arrivee")
    val lieuArrivee:String = ""

    fun toSoapReserv(): Reservations {
        val reserv = Reservations()
        reserv.idReservation = id
        reserv.idTrain = idTrain
        reserv.idUser = idUser
        reserv.classe = classe
        reserv.quantite = quantite
        reserv.prix = prix
        reserv.heureDepart = heureDepart
        reserv.heureArrivee = heureArrivee
        reserv.lieuDepart = lieuDepart
        reserv.lieuArrivee = lieuArrivee
        return reserv
    }
}