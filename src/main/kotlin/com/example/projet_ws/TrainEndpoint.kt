package com.example.projet_ws

import localhost._8080.ws.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload
import java.sql.DriverManager

@Endpoint
class TrainEndpoint @Autowired constructor() {
//    @PayloadRoot(namespace = "http://localhost:8080/ws/", localPart = "getTrainRequest")
//    @ResponsePayload
//    fun getTrain(@RequestPayload request: GetTrainRequest): GetTrainResponse {
//        val response = GetTrainResponse()
//        response.train = trainRepository.findTrain(request.id)
//        return response
//    }

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
                    "and pwd = '" + request.password + "'"
        )
        val user = User()
        resultSet.next()
        user.id = resultSet.getInt("id")
        user.name = resultSet.getString("name")
        user.lastName = resultSet.getString("last_name")
        response.user = user
        return response
    }
}