package pt.isel.ps.g30.tollingsystem.data.api.model

data class Vehicle(
        val id : Int,
        val licensePlate: String,
        val owner: String,
        val tare: Tare
       )