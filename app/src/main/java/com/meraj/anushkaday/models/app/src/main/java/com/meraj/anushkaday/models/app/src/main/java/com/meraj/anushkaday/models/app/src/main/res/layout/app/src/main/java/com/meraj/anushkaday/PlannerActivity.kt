package com.meraj.anushkaday

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.meraj.anushkaday.models.Task

class PlannerActivity : AppCompatActivity() {

    private lateinit var taskContainer: LinearLayout
    private lateinit var etNewTask: EditText
    private val tasks = mutableListOf<Task>()
    private val prefsName = "AnushkaPrefs"
    private val tasksKey = "tasks"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner)

        taskContainer = findViewById(R.id.taskContainer)
        etNewTask = findViewById(R.id.etNewTask)
        val btnAddTask = findViewById<Button>(R.id.btnAddTask)

        loadTasks()
        renderTasks()

        btnAddTask.setOnClickListener {
            val title = etNewTask.text.toString().trim()
            if (title.isNotEmpty()) {
                tasks.add(Task(title))
                etNewTask.text.clear()
                saveTasks()
                renderTasks()
            }
        }
    }

    private fun renderTasks() {
        taskContainer.removeAllViews()

        for ((index, task) in tasks.withIndex()) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(8, 16, 8, 16)
            }

            val checkBox = CheckBox(this).apply {
                text = task.title
                isChecked = task.isDone
                textSize = 16f
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                setOnCheckedChangeListener { _, isChecked ->
                    task.isDone = isChecked
                    saveTasks()
                }
            }

            val deleteBtn = TextView(this).apply {
                text = "✕"
                textSize = 18f
                setPadding(16, 0, 16, 0)
                setOnClickListener {
                    tasks.removeAt(index)
                    saveTasks()
                    renderTasks()
                }
            }

            row.addView(checkBox)
            row.addView(deleteBtn)
            taskContainer.addView(row)
        }
    }

    private fun saveTasks() {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val serialized = tasks.joinToString("\n") { task ->
            "${if (task.isDone) 1 else 0}\u0001${task.title}"
        }
        prefs.edit().putString(tasksKey, serialized).apply()
    }

    private fun loadTasks() {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val serialized = prefs.getString(tasksKey, "") ?: ""
        tasks.clear()
        if (serialized.isNotEmpty()) {
            serialized.split("\n").forEach { line ->
                val parts = line.split("\u0001")
                if (parts.size == 2) {
                    tasks.add(Task(title = parts[1], isDone = parts[0] == "1"))
                }
            }
        }
    }
}
