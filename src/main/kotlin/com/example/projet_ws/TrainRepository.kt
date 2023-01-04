package com.example.projet_ws


import localhost._8080.ws.Train
import javax.annotation.PostConstruct
import java.util.HashMap
import org.springframework.stereotype.Component

@Component
class TrainRepository {
    companion object {
        private val trains = HashMap<Int, Train>()
    }

    @PostConstruct
    public fun initData(){
        val spain = Train();
        spain.id = 1
        spain.start= "Paris"
        spain.dest= "Madrid"

        trains[spain.id] = spain

        val poland = Train()
        poland.id = 2
        poland.start = "Paris"
        poland.dest = "Warsaw"

        trains[poland.id] = poland
    }

    public fun findTrain(id: Int):Train? {
        return trains[id] ?: throw IllegalArgumentException("The train does not exist")
    }
}