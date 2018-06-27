package pt.isel.ps.g30.tollingsystem.extension


import java.text.SimpleDateFormat
import java.util.*

val myFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK)
fun Date.dateTimeParsed(): String = myFormat.format(this)
