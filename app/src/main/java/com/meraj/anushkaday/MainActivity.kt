package com.meraj.anushkaday

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.meraj.anushkaday.models.DayData
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvGreeting: TextView
    private lateinit var tvTasks: TextView
    private lateinit var tvWater: TextView
    private lateinit var progressWater: ProgressBar
    private lateinit var tvStudy: TextView
    private lateinit var tvSpending: TextView
    private lateinit var tvMood: TextView
    private lateinit var tvPartnerMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        // TODO: replace this with real data loaded from Room DB / SharedPreferences
        val today = DayData()

        renderDashboard(today)
    }

    private fun bindViews() {
        tvGreeting = findViewById(R.id.tvGreeting)
        tvTasks = findViewById(R.id.tvTasks)
        tvWater = findViewById(R.id.tvWater)
        progressWater = findViewById(R.id.progressWater)
        tvStudy = findViewById(R.id.tvStudy)
        tvSpending = findViewById(R.id.tvSpending)
        tvMood = findViewById(R.id.tvMood)
        tvPartnerMessage = findViewById(R.id.tvPartnerMessage)
    }

    private fun renderDashboard(data: DayData) {
        tvGreeting.text = "${greetingEmoji()} Good ${timeOfDayLabel()}, ${data.userName} ❤️"
        tvTasks.text = "📅 Today's plan: ${data.totalTasks} tasks"
        tvWater.text = "💧 Water: ${data.waterGlassesDrunk}/${data.waterGlassesGoal}"
        progressWater.max = data.waterGlassesGoal
        progressWater.progress = data.waterGlassesDrunk
        tvStudy.text = "📚 Study: ${formatHours(data.studyHoursToday)}"
        tvSpending.text = "💰 Today: ₹${data.todaySpendingRupees}"
        tvMood.text = "${data.currentMood.emoji} Mood: ${data.currentMood.label}"
        tvPartnerMessage.text = data.partnerMessage
    }

    private fun timeOfDayLabel(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> "Morning"
            in 12..16 -> "Afternoon"
            in 17..20 -> "Evening"
            else -> "Night"
        }
    }

    private fun greetingEmoji(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> "🌤️"
            in 12..16 -> "☀️"
            in 17..20 -> "🌆"
            else -> "🌙"
        }
    }

    private fun formatHours(hours: Double): String {
        return if (hours == hours.toInt().toDouble()) {
            "${hours.toInt()} hours"
        } else {
            "$hours hours"
        }
    }
}
