package pt.isel.ps.g30.tollingsystem.data.database.model

enum class TransactionState{
    INCOMPLETE,
    AWAITING_CONFIRMATION,
    CONFIRMED,
    CLOSED,
    CANCELED
}