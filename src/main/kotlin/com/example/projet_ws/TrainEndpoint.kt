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
}