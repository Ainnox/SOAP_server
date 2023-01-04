package com.example.projet_ws



import localhost._8080.ws.GetTrainRequest
import localhost._8080.ws.GetTrainResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload


@Endpoint
class CountryEndpoint @Autowired constructor(val trainRepository: TrainRepository) {
    @PayloadRoot( namespace= "http://localhost:8080/ws/",localPart = "getTrainRequest")
    @ResponsePayload
    fun getTrain(@RequestPayload request: GetTrainRequest): GetTrainResponse {
        val response = GetTrainResponse()
        response.setTrain(trainRepository.findTrain(request.id))
        return response
    }
}