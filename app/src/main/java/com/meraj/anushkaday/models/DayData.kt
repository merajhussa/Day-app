package com.meraj.anushkaday.models

data class DayData(
    val userName: String = "Anushka",
    val totalTasks: Int = 4,
    val completedTasks: Int = 0,
    val waterGlassesDrunk: Int = 3,
    val waterGlassesGoal: Int = 6,
    val studyHoursToday: Double = 2.0,
    val todaySpendingRupees: Int = 180,
    val currentMood: Mood = Mood.HAPPY,
    val partnerMessage: String = "Whatever happens today, I'm always cheering for you. ❤️",
    val partnerName: String = "Meraj"
)

enum class Mood(val emoji: String, val label: String) {
    HAPPY("😊", "Happy"),
    SAD("😢", "Sad"),
    MOTIVATED("💪", "Motivated"),
    TIRED("😴", "Tired"),
    NEUTRAL("😐", "Okay")
}
