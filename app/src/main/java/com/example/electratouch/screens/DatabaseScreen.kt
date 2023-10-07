package com.example.electratouch.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.electratouch.components.DatabaseCard
import com.example.electratouch.viewmodel.FirestoreViewModel
import com.example.electratouch.viewmodel.NetworkViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electratouch.R
import com.example.electratouch.components.UiStatus
import com.example.electratouch.models.User

@Composable
fun DatabaseScreen() {
    val viewModel: FirestoreViewModel = hiltViewModel()

    val networkViewModel: NetworkViewModel = viewModel()

    val networkState by networkViewModel.getNetworkState().observeAsState(initial = true)

    if (networkState) {
        viewModel.loadUser()
        val usersState = viewModel.chapterList.observeAsState()
        if(usersState.value == null){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(R.raw.loading)
            }
        }else if (usersState != null) {
            val usersList = usersState.value
            if (usersList != null) {
                DatabaseUI(usersList = usersList)
            }
        }
    } else {
        // No network connection, show a "No Internet" message
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            UiStatus(R.raw.server_error)
        }
    }
}


@Composable
fun DatabaseUI(
    usersList: List<User>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(usersList) {
            DatabaseCard(
                user = it
            )
        }
    }
}
