package com.example.projet_ws

import localhost._8080.ws.Train
import org.springframework.web.client.RestTemplate
import java.util.*
import java.util.stream.Collectors

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
}