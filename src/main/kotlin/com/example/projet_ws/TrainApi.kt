package com.example.projet_ws

import localhost._8080.ws.Reservations
import localhost._8080.ws.Train
import org.springframework.web.client.RestTemplate
import java.util.*

class TrainApi {
    companion object TrainsUrl {
        const val trainsUrl = "http://localhost:5000/"
        val restTemplate = RestTemplate()
    }

    fun getTrainStartDate(start: String, dest: String, dateStart: String): MutableList<Train> {
        val url = trainsUrl + "consulter/train/dateDepart/$start/$dest/$dateStart"
        val response = restTemplate.getForObject(url, Array<TrainRest>::class.java)
        var trains: MutableList<Train> = MutableList(0) { Train() }
        if (response != null)
            trains = response.map {it.toSoapTrain() }.toMutableList()

        return trains
    }

    fun getReservations(userId: Int): MutableList<Reservations> {
        val url = trainsUrl + "consulter/reservation/$userId"
        val response = restTemplate.getForObject(url, Array<ReservRest>::class.java)
        var trains: MutableList<Reservations> = MutableList(0) { Reservations() }
        if (response != null)
            trains = response.map {it.toSoapReserv() }.toMutableList()

        return trains
    }

    fun getReservation(userId: Int, trainId: Int, classe: String, quantite: Int): String? {
        val url = trainsUrl + "reserver/train/$userId/$trainId/$quantite/$classe"
        return restTemplate.getForObject(url, String::class.java)
    }

    fun getAnnulation(idTrain: Int, idUser: Int): String {
        val url = trainsUrl + "annuler/reservation/$idUser/$idTrain"
        return restTemplate.getForObject(url, String::class.java)!!
    }
}