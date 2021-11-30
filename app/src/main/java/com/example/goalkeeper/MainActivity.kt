package com.example.goalkeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goalkeeper.ui.theme.GoalKeeperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoalKeeperTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    GoalList()
                }
            }
        }
    }
}

@Composable
fun GoalList() {
    Box (modifier = Modifier.fillMaxHeight()) {
        LazyColumn (modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
            item {
                GoalCard()
            }
            item {
                GoalCard()
            }
            item {
                GoalCard()
            }
        }
        FloatingActionButton(onClick = { /* TODO: Add new goal */ }, modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(24.dp)
            .size(72.dp)) {
            Icon(Icons.Default.Add, contentDescription = "Add new goal")
        }
    }
}

@Composable
fun GoalCard() {
    var isGoalAchieved by remember { mutableStateOf(false) }
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) -90f else 0f)

    Card(elevation = 8.dp,modifier = Modifier
        .padding(top = 12.dp)
        .animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        )) {

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)) {

                Checkbox(checked = isGoalAchieved,
                    onCheckedChange = { isGoalAchieved = it },
                    modifier = Modifier.padding(8.dp))

                Text(text = "New goal!", textAlign = TextAlign.Start,
                    fontSize = 18.sp, fontWeight = FontWeight.Bold,
                    maxLines = if (expandedState) Int.MAX_VALUE else 1, overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(8.dp))

                IconButton(onClick = { expandedState = !expandedState },
                    modifier = Modifier
                        .padding(8.dp)
                        .border(width = 1.dp, color = Color.Cyan, shape = CircleShape)
                        .rotate(rotationState)) {
                    Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Expand card", tint = Color.Cyan)
                }
            }

            if(expandedState) {

                SubGoalRow()
                SubGoalRow()
                SubGoalRow()

                OutlinedButton(onClick = { /*TODO:add new subGoal */ },
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, Color.Cyan),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Cyan)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add new subGoal")
                    Text(text = "New goal")
                }

                CardDivider()

                ReminderRow()
                ReminderRow()
                ReminderRow()

                OutlinedButton(onClick = { /*TODO:add new reminder */ },
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, hexColor("#0085FF")),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = hexColor("#0085FF"))
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add new reminder")
                    Text(text = "New reminder")
                }

                CardDivider()

                OutlinedButton(onClick = { /*TODO: Remove goal*/ },
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, hexColor("#E92C2C")),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = hexColor("#E92C2C"))) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete goal")
                    Text(text = "Delete goal")
                }
            }
        }
    }
}

@Composable
fun SubGoalRow() {
    var subGoalState by remember { mutableStateOf(false) }

    Row(modifier = Modifier.padding(start = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

        Checkbox(checked = subGoalState,
            onCheckedChange = { subGoalState = it },
            modifier = Modifier.padding(8.dp))

        Text (text = "New subGoal!",
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))
    }
}

@Composable
fun ReminderRow() {
    val isReminderEnabled = remember { mutableStateOf(true) }

    Row (modifier = Modifier.padding(start = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

        IconButton(onClick = { /* TODO: Enable and disable reminder */
            isReminderEnabled.value = !isReminderEnabled.value}) {
            Icon(Icons.Default.DateRange, contentDescription = "Enable(disable) reminder", tint = if (isReminderEnabled.value) hexColor("#0085FF") else hexColor("#808080"))
        }

        Text( text = "17 November 2022",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth().padding(8.dp))

    }
}

@Composable
fun CardDivider() {
    Divider (modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp))
}

fun hexColor(colorString: String) : Color = Color(android.graphics.Color.parseColor(colorString))

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoalKeeperTheme {
        GoalList()
    }
}