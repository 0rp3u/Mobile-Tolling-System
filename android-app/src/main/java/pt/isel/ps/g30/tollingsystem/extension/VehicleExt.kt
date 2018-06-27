package pt.isel.ps.g30.tollingsystem.extension

import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.data.api.model.Tare
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle

fun Vehicle.getIconResource() = when(tare){
    Tare.Classe_1 -> R.drawable.ic_tare_1
    Tare.Classe_2 -> R.drawable.ic_tare_2
    Tare.Classe_3 -> R.drawable.ic_tare_3
    Tare.Classe_4 -> R.drawable.ic_tare_4
    Tare.Classe_5 -> R.drawable.ic_tare_5
}
