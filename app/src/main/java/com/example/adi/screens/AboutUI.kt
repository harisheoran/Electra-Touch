package com.example.adi.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity

@Composable
fun AboutUI(modifier: Modifier = Modifier) {
    Column(modifier) {
        GithubCard()
    }
}

@Composable
fun GithubCard() {
    val paddingModifier = Modifier.padding(10.dp)
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/harisheoran/Adi")) }

    Card(
        modifier = paddingModifier
            .fillMaxWidth()
            .clickable {
                startActivity(context, intent, null)
            }
    ) {
        Column() {
            Text(
                text = "Github Repository",
                modifier = paddingModifier,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "harisheoran/Adi",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            )

        }
    }
}