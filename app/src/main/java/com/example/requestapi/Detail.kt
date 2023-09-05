package com.example.requestapi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.requestapi.API.RetrofitInstance
import com.example.requestapi.model.Movie
import com.example.requestapi.ui.theme.Primary
import com.example.requestapi.ui.theme.RequestAPITheme
import com.google.accompanist.coil.rememberCoilPainter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MovieDetailPage(navController: NavController, movieId: Int?, viewModel: MainViewModel) {
    val movie = remember { mutableStateOf<Movie?>(null) }

    LaunchedEffect(movieId) {
        RetrofitInstance.apiService.getMovieDetails(movieId ?: 0).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    movie.value = response.body()
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
            }
        })
    }

    RequestAPITheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                TopAppBar(
                    title = { Text(text = movie.value?.title ?: "") },
                    backgroundColor = Primary,
                    contentColor = Color.White,
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                movie.value?.let {
                                    viewModel.addMovieToFavourite(it)
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Add to Favorites")
                        }
                    }
                )
                movie.value?.let {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Image(
                            painter = rememberCoilPainter(
                                request = "https://image.tmdb.org/t/p/w500${it.poster_path}",
                                fadeIn = true
                            ),
                            contentDescription = "Movie Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp),
                            contentScale = ContentScale.FillWidth
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp, end = 10.dp)
                        ){
                            Text(
                                text = it.original_title,
                                modifier = Modifier.padding(top = 10.dp),
                                color = MaterialTheme.colors.onSurface,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.W700,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Justify
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp, top = 10.dp)
                            ){
                                Column(Modifier.weight(1f)){
                                    Text(
                                        text = it.original_language,
                                        style = MaterialTheme.typography.subtitle1
                                    )
                                    Text(
                                        text = "Language",
                                        style = MaterialTheme.typography.subtitle2
                                    )
                                }
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        text = it.vote_average.toString(),
                                        style = MaterialTheme.typography.subtitle1
                                    )
                                    Text(
                                        text = "Rating",
                                        style = MaterialTheme.typography.subtitle2
                                    )
                                }
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        text = it.popularity.toString(),
                                        style = MaterialTheme.typography.subtitle1
                                    )
                                    Text(
                                        text = "Popularity",
                                        style = MaterialTheme.typography.subtitle2
                                    )
                                }
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        text = it.release_date,
                                        style = MaterialTheme.typography.subtitle1
                                    )
                                    Text(
                                        text = "Release Date",
                                        style = MaterialTheme.typography.subtitle2
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Description",
                                style = MaterialTheme.typography.h6,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it.overview,
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                    }
                }
            }

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MovieDetailPagePreview() {
//    MovieDetailPage(rememberNavController(), null)
//}

