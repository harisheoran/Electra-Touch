package com.example.adi.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.adi.R
import com.example.adi.components.HomeCard
import com.example.adi.components.UiStatus
import com.example.adi.models.Hinduism
import com.example.adi.models.King
import com.example.adi.network.ApiResponse
import com.example.adi.viewmodel.HinduismViewModel

@Composable
fun HomeScreen(onClick: (args: String, img: String) -> Unit) {
    val viewModel: HinduismViewModel = hiltViewModel()
    // val names = viewModel.warriorsNameListState.observeAsState()

    val hinduismState = viewModel.hinduismStateFlow.collectAsState()
    val apiRes = hinduismState.value

    var selected by rememberSaveable {
        mutableStateOf(false)
    }

    when (apiRes.status) {

        // Successfull response
        is ApiResponse.Status.Success -> {

            HomeUI(
                onClick = onClick,
                onClickFavourite = {
                    viewModel.saveFavouriteKing(it)
                },
                hinduismList = apiRes
            )
        }

        // Failure Response
        is ApiResponse.Status.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(R.raw.server_error)
            }
        }

        // Loading
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UiStatus(R.raw.loading)
            }
        }
    }

}

@Composable
fun HomeUI(
    onClick: (args: String, img: String) -> Unit,
    onClickFavourite: (favouriteKing: King) -> Unit,
    hinduismList: ApiResponse<List<Hinduism>>
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
        val warriorsData = hinduismList.dataBody
        items(warriorsData) {
            HomeCard(
                hinduism = it,
                onClick = onClick,
                onClickFavourite = onClickFavourite
            )
        }
    }
}
