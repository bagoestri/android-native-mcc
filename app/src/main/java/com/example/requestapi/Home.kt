package com.example.requestapi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.requestapi.API.RetrofitInstance
import com.example.requestapi.model.Movie
import com.example.requestapi.model.MovieResponse
import com.example.requestapi.ui.theme.Primary
import com.example.requestapi.ui.theme.RequestAPITheme
import com.google.accompanist.coil.rememberCoilPainter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MovieItem(movie: Movie, navController: NavController, modifier: Modifier = Modifier) {
    val painter = rememberCoilPainter(
        request = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
        fadeIn = true
    )
    Image(
        painter = painter,
        contentDescription = "Movie Image",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clickable { navController.navigate("detail/${movie.id}") }
            .padding(all = 8.dp)
            .fillMaxSize()
    )
}

@Composable
fun PopularMoviesListItem(movie: Movie, navController: NavController) {
    val painter = rememberCoilPainter(
        request = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
        fadeIn = true
    )

    Column(
        modifier = Modifier
            .clickable { navController.navigate("detail/${movie.id}") }
            .padding(all = 16.dp)
            .width(200.dp)
            .height(250.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painter,
                contentDescription = "Movie Image",
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
        }
        Text(
            text = movie.title ?: "No title available",
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}


@Composable
fun HomePage(navController: NavController, modifier: Modifier = Modifier) {
    val nowPlayingMovies = remember { mutableStateOf(listOf<Movie>()) }
    val AllMovies = remember { mutableStateOf(listOf<Movie>()) }
    val isLoadingNowPlaying = remember { mutableStateOf(true) }
    val isLoadingAll = remember { mutableStateOf(true) }
    val isErrorNowPlaying = remember { mutableStateOf(false) }
    val isErrorAll = remember { mutableStateOf(false) }

    // Fetch Now Playing movies
    LaunchedEffect(key1 = "now_playing") {
        RetrofitInstance.apiService.getNowPlaying("en-US", 1)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        nowPlayingMovies.value = response.body()?.results ?: listOf()
                        isLoadingNowPlaying.value = false
                        isErrorNowPlaying.value = false
                    } else {
                        isLoadingNowPlaying.value = false
                        isErrorNowPlaying.value = true
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    isLoadingNowPlaying.value = false
                    isErrorNowPlaying.value = true
                }
            })
    }

    // Fetch All movies
    LaunchedEffect(key1 = "all_movies") {
        RetrofitInstance.apiService.getAllMovies()
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        AllMovies.value = response.body()?.results ?: listOf()
                        isLoadingAll.value = false
                        isErrorAll.value = false
                    } else {
                        isLoadingAll.value = false
                        isErrorAll.value = true
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    isLoadingAll.value = false
                    isErrorAll.value = true
                }
            })
    }

    RequestAPITheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    backgroundColor = Primary
                ) {
                    BottomNavigationItem(
                        icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
                        selected = true,
                        onClick = { navController.navigate("home") }
                    )
                    BottomNavigationItem(
                        icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites") },
                        selected = false,
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
            if (isLoadingNowPlaying.value || isLoadingAll.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            } else if (isErrorNowPlaying.value || isErrorAll.value) {
                Text(
                    text = "An error occurred while fetching data.",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .wrapContentSize(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Now Playing",
                        style = MaterialTheme.typography.h6,
                        color = Color.Black
                    )
                    LazyRow(
                        modifier = Modifier.height(200.dp)
                    ) {
                        items(nowPlayingMovies.value) { movie ->
                            MovieItem(movie = movie, navController = navController)
                        }
                    }

                    Text(
                        text = "All Movies",
                        style = MaterialTheme.typography.h6,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    LazyColumn {
                        items(AllMovies.value.chunked(2)) { moviesChunk ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                for (movie in moviesChunk) {
                                    PopularMoviesListItem(movie = movie, navController = navController)
                                }
                            }
                        }
                    }


                }
            }
        }
    }
}
