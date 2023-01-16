package com.example.projet_ws

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import localhost._8080.ws.Train

@JsonIgnoreProperties(ignoreUnknown = true)
class TrainRest {

    @JsonProperty("id_train")
    val id:Int = 0
    @JsonProperty("lieu_depart")
    val lieuDepart:String = ""
    @JsonProperty("lieu_arrivee")
    val lieuArrivee:String = ""
    @JsonProperty("heure_depart")
    val heureDepart:String = ""
    @JsonProperty("heure_arrivee")
    val heureArrivee:String = ""
    @JsonProperty("business_class_remaining")
    val businessClassRemaining:Int = 0
    @JsonProperty("first_class_remaining")
    val firstClassRemaining:Int = 0
    @JsonProperty("standard_class_remaining")
    val standardClassRemaining:Int = 0

    fun toSoapTrain(): Train {
        val train = Train()
        train.idTrain = id
        train.start = lieuDepart
        train.dest = lieuArrivee
        train.dateStart = heureDepart
        train.dateDest = heureArrivee
        train.businessClassRemaining = businessClassRemaining
        train.firstClassRemaining = firstClassRemaining
        train.standardClassRemaining = standardClassRemaining
        return train
    }
}
