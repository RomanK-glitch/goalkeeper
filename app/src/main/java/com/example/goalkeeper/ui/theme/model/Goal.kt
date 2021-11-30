package com.example.goalkeeper.ui.theme.model

data class Goal(
    var goalDescription: String = "New Goal!",
    var achieved : Boolean = false,
    var subGoals: List<SubGoal> = emptyList(),
    var reminders: List<Reminder> = emptyList())