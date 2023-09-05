package com.example.requestapi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.requestapi.ui.theme.Primary
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun FavouritePage(navController: NavController, viewModel: MainViewModel) {
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Primary
            ) {
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
                    selected = false,
                    onClick = { navController.navigate("home") }
                )
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites") },
                    selected = true,
                    onClick = { navController.navigate("favourite") }
                )
                BottomNavigationItem(
                    icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Account") },
                    selected = false,
                    onClick = { navController.navigate("account") }
                )
            }
        }
    ) {
        val favouriteMovies = viewModel.favouriteMovies.observeAsState(initial = emptyList())

        LazyColumn {
            items(favouriteMovies.value) { movie ->
                Card(
                    modifier = Modifier
                        .clickable { navController.navigate("detail/${movie.id}") }
                        .padding(all = 16.dp)
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    Column {
                        // Movie Image
                        Image(
                            painter = rememberCoilPainter(
                                request = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                                fadeIn = true
                            ),
                            contentDescription = "Movie Image",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(),
                            alignment = Alignment.TopCenter
                        )

                        // Movie Title
                        Text(
                            text = movie.title ?: "No title available",
                            modifier = Modifier.padding(all = 8.dp),
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
        }
    }
}

