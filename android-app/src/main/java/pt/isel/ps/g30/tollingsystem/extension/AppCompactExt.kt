package pt.isel.ps.g30.tollingsystem.extension

import androidx.appcompat.app.AppCompatActivity
import pt.isel.ps.g30.tollingsystem.TollingSystemApp

val AppCompatActivity.app: TollingSystemApp
    get() = application as TollingSystemApp
