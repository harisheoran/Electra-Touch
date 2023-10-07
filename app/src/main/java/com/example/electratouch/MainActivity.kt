package com.example.electratouch

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
import com.example.electratouch.components.BottomNavigationBar
import com.example.electratouch.components.MyAppBar
import com.example.electratouch.screens.AboutUI
import com.example.electratouch.screens.DatabaseScreen
import com.example.electratouch.screens.ElectionScreen
import com.example.electratouch.ui.theme.AdiTheme
import com.example.electratouch.utils.Admin
import com.example.electratouch.utils.Destinations
import com.example.electratouch.utils.Election
import com.example.electratouch.utils.MyDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    val items = listOf<Destinations>(MyDatabase, Election, Admin)

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
            startDestination = MyDatabase.route,
            modifier = modifier.padding(it)
        ) {
            // Database Screen
            composable(
                route = MyDatabase.route
            ) {
                DatabaseScreen()
            }

            composable(
                route = Election.route
            ) {
                ElectionScreen()
            }
            composable(
                route = Admin.route
            ) {
                AboutUI()
            }
        }
    }
}