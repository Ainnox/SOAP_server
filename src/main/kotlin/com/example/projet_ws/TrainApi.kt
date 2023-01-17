package com.example.projet_ws

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONPObject
import localhost._8080.ws.Reservations
import localhost._8080.ws.Train
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
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

    fun postReservation(userId: Int, trainId: Int, classe: String, quantite: Int): String? {
        val url = trainsUrl + "reserver/train/"
//        val mapper = ObjectMapper()
        val body = """{"id_train":$trainId,"id_utilisateur":$userId,"classe":"$classe","quantite":$quantite}"""
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity(body, headers)
        return restTemplate.postForObject(url, request, String::class.java)
    }


    fun getAnnulation(idTrain: Int, idUser: Int): String {
        val url = trainsUrl + "annuler/reservation/$idUser/$idTrain"
        return restTemplate.getForObject(url, String::class.java)!!
    }

    fun postAnnulation(idResrvation: Int, idUser: Int): String {
        val url = trainsUrl + "annuler/reservation/"
        val body = """{"id_reservation":$idResrvation,"id_utilisateur":$idUser}"""
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity(body, headers)
        return restTemplate.postForObject(url, request, String::class.java)!!
    }
}