package pt.isel.ps.g30.tollingsystem.extension


import java.text.SimpleDateFormat
import java.util.*

val myFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.UK)
fun Date.dateTimeParsed(): String = myFormat.format(this)

fun String.toDate(): Date = myFormat.parse(this)
