package com.dapm.gotour.home

import com.google.gson.annotations.SerializedName

data class RouteResponse(@SerializedName("features") val features: List<Feature>)

data class Feature(@SerializedName("geometry") val geometry: Geometry)

data class Geometry(@SerializedName("coordinates") val coordinates: List<List<Double>>)


data class SumaryResponse(@SerializedName("features") val features2: List<Feature2>)
data class Feature2(@SerializedName("properties") val properties: Properties)
data class Properties(
    @SerializedName("summary") val summary: Summary
)

data class Summary(
    @SerializedName("distance") val distance: Double,
    @SerializedName("duration") val duration: Double
)
