package com.example.requestapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.requestapi.database.AppDatabase
import com.example.requestapi.ui.theme.RequestAPITheme


class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: AppDatabase
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()

        viewModel = MainViewModel(dbHelper)

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    val isLoggedIn = remember { mutableStateOf(false) }
                    val userName = remember { mutableStateOf<String?>(null) }

                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            Login(navController, dbHelper, isLoggedIn, userName)
                        }
                        composable("register") {
                            Register(navController, dbHelper)
                        }
                        composable("home") {
                            HomePage(navController)
                        }
                        composable("detail/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
                            MovieDetailPage(navController, movieId, viewModel)
                        }
                        composable("account") {
                            AccountPage(navController, isLoggedIn.value, userName.value) {
                                isLoggedIn.value = false
                                userName.value = null
                                navController.navigate("login") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        }
                        composable("favourite") {
                            FavouritePage(navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}


//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            val navController = rememberNavController()
//
//            NavHost(navController = navController, startDestination = "home") {
//                composable("home") { HomePage(navController) }
//                composable("detail/{movieId}") { backStackEntry ->
//                    val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
//                    MovieDetailPage(navController, movieId)
//                }
//            }
//        }
//    }
//}





