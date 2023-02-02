package com.ssafy.smile.data.remote.model

data class ClusterDto (
        val clusterId : Long,
        val numOfCluster : Int,
        val centroidLat : Double,
        val centroidLng : Double
)
