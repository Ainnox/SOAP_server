package com.example.projet_ws

import com.fasterxml.jackson.annotation.JsonProperty
import localhost._8080.ws.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.DriverManager

@Endpoint
class TrainEndpoint @Autowired constructor() {

    data class TrainData(
        @JsonProperty("id") val id: Int,
        @JsonProperty("lieu_depart") val lieu_depart: String,
        @JsonProperty("lieu_arrivee") val lieu_arrivee: String,
        @JsonProperty("heure_depart") val heure_depart: String,
        @JsonProperty("heure_arrivee") val heure_arrivee: String,
        @JsonProperty("business_class_remaining") val businesse_places_remaining: Int,
        @JsonProperty("first_class_remaining") val first_places_remaining: Int,
        @JsonProperty("standard_class_remaining") val second_places_remaining: Int,
    )

    @PayloadRoot(namespace = "http://localhost:8080/ws/", localPart = "getUserRequest")
    @ResponsePayload
    fun getUser(@RequestPayload request: GetUserRequest): GetUserResponse {
        val response = GetUserResponse()
        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/webservice", "webservice", "webservice"
        )
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(
            "SELECT id,name,last_name FROM users WHERE username = '" + request.username + "' " +
                    "and pwd = '" + request.pwd + "'"
        )
        val user = User()
        resultSet.next()
        user.id = resultSet.getInt("id")
        user.name = resultSet.getString("name")
        user.lastName = resultSet.getString("last_name")
        response.user = user
        return response
    }

    @PayloadRoot(namespace = "http://localhost:8080/ws/", localPart = "subscribeRequest")
    @ResponsePayload
    fun subscribe(@RequestPayload request: SubscribeRequest): SubscribeResponse {
        val response = SubscribeResponse()
        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/webservice", "webservice", "webservice"
        )
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(
            "SELECT id FROM users WHERE username = '${request.username}'"
        )
        if (resultSet.next()) {
            response.status = "Username already exists"
        } else {
            statement.executeUpdate(
                "INSERT INTO users (username, pwd, name, last_name) " +
                        "VALUES ('${request.username}', '${request.pwd}', '${request.name}', '${request.lastName}')"
            )
            response.status = "OK"
        }
        return response
    }

    @PayloadRoot(namespace = "http://localhost:8080/ws/", localPart = "getReservationsRequest")
    @ResponsePayload
    fun getReservation(@RequestPayload request: GetReservationsRequest): GetReservationsResponse {
        val response = GetReservationsResponse()
        val trainApi = TrainApi()
        val apiRep = trainApi.getReservations(request.usrId)
        for (reservation in apiRep) {
            response.reservations.add(reservation)
        }
        return response
    }

    @PayloadRoot(namespace = "http://localhost:8080/ws/", localPart = "getTrainStartDateRequest")
    @ResponsePayload
    fun getTrainStartDate(@RequestPayload request: GetTrainStartDateRequest): GetTrainStartDateResponse {
        val response = GetTrainStartDateResponse()
        val trainApi = TrainApi()
        val apiRep = trainApi.getTrainStartDate(request.start, request.dest,request.dateStart)
        for (train in apiRep) {
            response.trains.add(train)
        }
        return response
    }
}