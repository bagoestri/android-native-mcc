package com.example.requestapi

import android.icu.text.Bidi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.BasicTextField



import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.requestapi.database.AppDatabase
import com.example.requestapi.ui.theme.Primary

@Composable
fun Login(navController: NavController, appDatabase: AppDatabase, isLoggedIn: MutableState<Boolean>,userName: MutableState<String?>, modifier: Modifier = Modifier){
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val inputError = remember { mutableStateOf("") }

    val navigateToHome = remember { mutableStateOf(false) }

    LaunchedEffect(navigateToHome.value) {
        if (navigateToHome.value) {
            val user = appDatabase.userDao().getUserByUsernameAndPassword(username.value, password.value)
            if (user != null) {
                isLoggedIn.value = true
                userName.value = user.name
                navController.navigate("home")
                navigateToHome.value = false
            } else {
                navigateToHome.value = false
            }
        }
    }

    Column (
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painterResource(
            id = R.drawable.inventory),
            contentDescription = "Login Image",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = username.value,
            onValueChange = {
                username.value = it
            },
            label = { Text("Username") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (inputError.value.isNotEmpty()) {
            Text(text = inputError.value, color = Color.Red)
        }
        Button(
            onClick = {
                if (username.value.isBlank() || password.value.isBlank()) {
                    inputError.value = "Input fields cannot be empty"
                    navigateToHome.value = false
                } else {
                    navigateToHome.value = true
                }
            },
            modifier = Modifier.size(150.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary
            )
        ) {
            Text(
                text = "LOGIN",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ClickableText(
            text = AnnotatedString("Doesn't have an account? Register"),
            onClick = { offset ->
                navController.navigate("register")
            },
            style = TextStyle(
                textDecoration = TextDecoration.Underline,
                fontSize = 16.sp,
                color = Color.Blue
            ),
            modifier = Modifier.padding(8.dp)
        )


    }
}



