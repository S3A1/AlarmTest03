package com.example.alarmtest03

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        cancelAlarm.setOnClickListener{
            cancelAlarmManager()
            val CHANNEL_ID = "channel_id"
            val channel_name = "channel_name"
            val channel_description = "channel_description "

            ///APIレベルに応じてチャネルを作成
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = channel_name
                val descriptionText = channel_description
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                /// チャネルを登録
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }




        setAlarm.setOnClickListener{
            var editSecond = findViewById<EditText>(R.id.edit_second)
            var editMinute = findViewById<EditText>(R.id.edit_minute)
            var editHour = findViewById<EditText>(R.id.edit_hour)

            /*
            edit_second.setText(0,TextView.BufferType.NORMAL)
            edit_minute.setText(0,TextView.BufferType.NORMAL)
            edit_hour.setText(0,TextView.BufferType.NORMAL)
            */

            //秒数の取得
            var TimeSecond=Integer.parseInt(editSecond.text.toString().trim())
            //分数の取得
            var TimeMinute=Integer.parseInt(editMinute.text.toString().trim())
            //〇時間の取得
            var TimeHour=Integer.parseInt(editHour.text.toString().trim())

            val calendar= Calendar.getInstance()
            calendar.timeInMillis=System.currentTimeMillis()
            calendar.add(Calendar.SECOND,TimeSecond)
            calendar.add(Calendar.MINUTE,TimeMinute)
            calendar.add(Calendar.HOUR,TimeHour)
            setAlarmManager(calendar)

            //calendar.add(Calendar.SECOND,TimeSecond)
            //setAlarmManager(calendar)
        }
    }

    private fun setAlarmManager(calendar: Calendar){
        val am=getSystemService(Context.ALARM_SERVICE)as AlarmManager
        val intent= Intent(this,AlarmBroadcastReceiver::class.java)
        val pending= PendingIntent.getBroadcast(this,0,intent,0)
        when{
            Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP->{
                val info= AlarmManager.AlarmClockInfo(
                    calendar.timeInMillis,null)
                am.setAlarmClock(info,pending)
            }
            Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT->{
                am.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pending)
            }
            else->{
                am.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,pending)
            }
        }
    }

    private fun cancelAlarmManager(){
        val am=getSystemService(Context.ALARM_SERVICE)as AlarmManager
        val intent= Intent(this,AlarmBroadcastReceiver::class.java)
        val pending= PendingIntent.getBroadcast(this,0,intent,0)
        am.cancel(pending)
    }



}
