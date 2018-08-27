package pt.isel.ps.g30.tollingsystem.extension

import pt.isel.ps.g30.tollingsystem.data.database.model.CurrentTransaction
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction

fun CurrentTransaction.toFinishedTransaction(): TollingTransaction{
    if(vehicle == null || destination == null || destTimestamp == null || origin ==null || originTimestamp == null) throw Exception(" could not convert to tolling transaction, active transaction is not finished")

    return TollingTransaction(vehicle!!, origin!!, originTimestamp!!, destination!!, destTimestamp!!)

}