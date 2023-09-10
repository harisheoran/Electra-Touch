package com.example.adi

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.adi.components.BottomNavigationBar
import com.example.adi.components.MyAppBar
import com.example.adi.screens.AboutUI
import com.example.adi.screens.DetailsMainScreen
import com.example.adi.screens.FavouriteScreen
import com.example.adi.screens.HomeScreen
import com.example.adi.ui.theme.AdiTheme
import com.example.adi.utils.About
import com.example.adi.utils.Destinations
import com.example.adi.utils.Details
import com.example.adi.utils.Favourites
import com.example.adi.utils.Home
import com.example.adi.utils.NotificationUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notification = NotificationUtil(this)
        notification.showNotification()

        setContent {
            AdiTheme {
                Surface(
                    tonalElevation = 5.dp,
                ) {
                    MyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    //  val currentScreen = backStackEntry?.arguments?.getString("kingName") ?: Home.label
    val currentRoute = backStackEntry?.destination?.route
    val items = listOf<Destinations>(Home, About)

    Scaffold(
        topBar = {
            MyAppBar(
                //  currentScreen = currentScreen,
                canNavigateUp = navController.previousBackStackEntry != null
            ) { navController.navigateUp() }
        },
        bottomBar = {
            if (currentRoute != null) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onClickNavigate = { arg ->
                        navController.navigate(arg) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    items = items
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Home.route,
            modifier = modifier.padding(it)
        ) {
            // Home Screen
            composable(route = Home.route) {
                HomeScreen() { arg, img ->
                    navController.navigate(
                        "${Details.route}?kingName=${arg},img=${img}"
                    ) {
                        launchSingleTop = true
                    }
                }
            }

            // Details Screen
            composable(
                route = Details.argWithRoute,
                arguments = Details.argument
            ) {
                val arguments = requireNotNull(it.arguments)
                val img = arguments.getString(Details.imgUrl)
                    ?: "https://i.pinimg.com/originals/fa/0a/29/fa0a2998e72b207925e63f3152cbda2a.jpg"
                DetailsMainScreen(imgUrl = img)
            }

            composable(
                route = About.route
            ) {
                AboutUI()
            }

            composable(route = Favourites.route) {
                FavouriteScreen(
                    onClick = { arg ->
                        navController.navigate("${Details.route}/$arg") {

                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}