package pt.isel.ps.g30.tollingsystem.extension


import java.text.SimpleDateFormat
import java.util.*

val myFormat = SimpleDateFormat("HH:mm:ss \n dd/MM/yyyy", Locale.UK)
fun Date.dateTimeParsed(): String = myFormat.format(this)
