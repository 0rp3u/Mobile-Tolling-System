package pt.isel.ps.g30.tollingsystem.interactor.notification
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.work.*
import kotlinx.coroutines.experimental.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.UnvalidatedTransactionInfo
import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.data.database.model.NotificationType
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.extension.getIconResource
import pt.isel.ps.g30.tollingsystem.services.work.ConfirmTransactionWork
import pt.isel.ps.g30.tollingsystem.services.work.VerifyTollingPassageWork
import pt.isel.ps.g30.tollingsystem.view.main.MainActivity

class NotificationInteractorImpl(private val tollingSystemDatabase: TollingSystemDatabase) : NotificationInteractor {


    override suspend fun getNotificationList() : Deferred<List<Notification>>{
        return async { tollingSystemDatabase.NotificationDao().findAll()}
    }

    override suspend fun getNotificationListLiveData() : Deferred<LiveData<List<Notification>>>{
        return async {
            tollingSystemDatabase.NotificationDao().findAllLiveData()}
    }

    override suspend fun confirmTransaction(notification: Notification) = launch {

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val request = OneTimeWorkRequestBuilder<ConfirmTransactionWork>()
                .setConstraints(constraints)
                .addTag(ConfirmTransactionWork.TAG)
                .setInputData(Data.Builder().putInt(ConfirmTransactionWork.KEY_ID, notification.transaction?.id ?: -1).build())
                .build()

        WorkManager.getInstance().enqueue(request)

        if (tollingSystemDatabase.NotificationDao().delete(notification) <1) throw Exception("Could not delete notification")
    }


    override suspend fun cancelTransaction(notification: Notification) = launch {
        if (tollingSystemDatabase.NotificationDao().delete(notification) <1) throw Exception("Could not delete notification")
    }

    override suspend fun dismissNotification(notification: Notification)= launch {
        if (tollingSystemDatabase.NotificationDao().delete(notification) <1) throw Exception("Could not delete notification")
    }


    override fun sendStartTransactionNotification(transaction: UnvalidatedTransactionInfo){
        val builder = NotificationCompat.Builder(TollingSystemApp.instance)

        val carIcon = transaction.vehicle?.getIconResource() ?: R.drawable.ic_notifications_black_24dp

        builder.setSmallIcon(carIcon)
                .setLargeIcon(BitmapFactory.decodeResource(TollingSystemApp.instance.resources, carIcon))
                .setColor(Color.RED)
                .setContentTitle("detected Passage")
                .setContentText("detected Passage on ${transaction.origin?.plaza?.name}")

        val extras = Bundle().also { it.putInt(MainActivity.SELECTED_ITEM_KEY, 2) }

        val notificationIntent = Intent(TollingSystemApp.instance, MainActivity::class.java).also { it.putExtras(extras) }

        val pendingIntent= PendingIntent.getActivity(TollingSystemApp.instance, 2, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        builder.setContentIntent(pendingIntent)

        sendNotification(builder)
    }

    override fun sendFinishTransactionNotification(transaction: TollingTransaction){
        val builder = NotificationCompat.Builder(TollingSystemApp.instance)

        val carIcon = transaction.vehicle.getIconResource()

        builder.setSmallIcon(carIcon)
                .setLargeIcon(BitmapFactory.decodeResource(TollingSystemApp.instance.resources, carIcon))
                .setColor(Color.RED)
                .setContentTitle("finished transaction")
                .setContentText("finished transaction started on ${transaction.origin.name} that ended on ${transaction.destination.name}")

        val extras = Bundle().also { it.putInt(MainActivity.SELECTED_ITEM_KEY, 2) }

        val notificationIntent = Intent(TollingSystemApp.instance, MainActivity::class.java).also { it.putExtras(extras) }

        val pendingIntent= PendingIntent.getActivity(TollingSystemApp.instance, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        builder.setContentIntent(pendingIntent)

        sendNotification(builder)
    }

private fun sendNotification(builder: NotificationCompat.Builder) {


    // Get an instance of the Notification manager
    val mNotificationManager = TollingSystemApp.instance.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Android O requires a Notification Channel.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val CHANNEL_ID = "TollingNotification"

        val name = TollingSystemApp.instance.getString(R.string.app_name)
        // Create the channel for the notification
        val mChannel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)

        // Set the Notification Channel for the Notification Manager.
        mNotificationManager.createNotificationChannel(mChannel)

        // Set the Channel ID for Android O.
        builder.setChannelId(CHANNEL_ID) // Channel ID

    }


    // Dismiss notification once the user touches it.
    builder.setAutoCancel(true)

    // Issue the notification
    mNotificationManager.notify(1, builder.build())
}

}
