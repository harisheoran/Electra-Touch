package com.example.adi.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AboutUI(modifier: Modifier = Modifier){
    Column(modifier) {
        GithubCard()
    }
}

@Composable
fun GithubCard(){
    val paddingModifier = Modifier.padding(10.dp)
    Card(
        modifier = paddingModifier.fillMaxWidth(),

    ) {
        Column() {
            Text(text = "Text with card content color (Blue)",
                modifier = paddingModifier)
            Text(text = "Text with card custom color",
                color = Color.Black,
                modifier = paddingModifier)

        }
    }
}