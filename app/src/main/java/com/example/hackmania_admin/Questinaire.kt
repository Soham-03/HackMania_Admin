package com.example.hackmania_admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackmania_admin.ui.theme.lightpeach
import com.example.hackmania_admin.ui.theme.offWhite

@Composable
fun QuestionnaireScreen(questions: List<String>, title: String) {
    var answers = remember { mutableStateListOf<Boolean>() }
    for(i in 0..10){
        answers.add(false)
    }
    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
        Text(
            text = "$title Assessment",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 10.dp)
        )
        for ((index, question) in questions.withIndex()) {
            Spacer(modifier = Modifier.height(16.dp))

            // Call QuestionRow and pass the question, answer, and onAnswerChange callback
            QuestionRow(
                question = question,
                answer = answers[index],
                onAnswerChange = { newAnswer ->
                    answers[index] = newAnswer
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        var score by remember {
            mutableIntStateOf(0)
        }
        Text(text = "$title Level: " +score.toString())
        Button(onClick = {
            for(ans in answers){
                if(ans){
                    score +=1
                }
                else{

                }
            }
            if(title == "Sustainability"){
                Global.sustainLvl = score.toString()
            }
            if(title == "Packaging And Waste Reduction"){
                Global.packgAndWasteRed = score.toString()
            }
            if(title == "Impact on Environment"){
                Global.evnImpact = score.toString()
            }
        }) {
            Text(text = "Calculate Score")
        }
    }
}

@Composable
fun QuestionRow(
    question: String,
    answer: Boolean,
    onAnswerChange: (Boolean) -> Unit
) {
    val selected = rememberUpdatedState(answer)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Text(text = question, fontSize = 16.sp)

        Spacer(modifier = Modifier.width(16.dp))
        Row(modifier = Modifier.fillMaxWidth()){
            RadioButton(
                selected = selected.value,
                onClick = { onAnswerChange(!selected.value) },
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = "Yes",
                modifier = Modifier
                    .clickable { onAnswerChange(true) } // Pass true when "Yes" is selected
                    .padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.width(32.dp))

            RadioButton(
                selected = !selected.value,
                onClick = { onAnswerChange(!selected.value) },
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = "No",
                modifier = Modifier
                    .clickable { onAnswerChange(false) } // Pass false when "No" is selected
                    .padding(start = 4.dp)
            )
        }
    }
}

