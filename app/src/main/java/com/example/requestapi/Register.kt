package com.example.requestapi

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
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.requestapi.database.AppDatabase
import com.example.requestapi.model.User
import com.example.requestapi.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun Register(navController: NavController, appDatabase: AppDatabase, modifier: Modifier = Modifier){
    val name = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val passwordError = remember { mutableStateOf("") }
    val confirmPasswordError = remember { mutableStateOf("") }
    val inputError = remember { mutableStateOf("") }
    val registrationSuccessful = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painterResource(
            id = R.drawable.inventory),
            contentDescription = "Register Image",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            label = { Text("Name") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
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
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = confirmPassword.value,
            onValueChange = {
                confirmPassword.value = it
            },
            label = { Text("Confirm Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (inputError.value.isNotEmpty()) {
            Text(text = inputError.value, color = Color.Red)
        }
        if (registrationSuccessful.value) {
            Text(
                text = "Account has been created",
                color = Color.Green,
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
        Button(
            onClick = {
                if (name.value.isBlank() || username.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank()) {
                    inputError.value = "Input fields cannot be empty"
                } else if (password.value != confirmPassword.value) {
                    inputError.value = "Password must be the same"
                } else {
                    coroutineScope.launch {
                        appDatabase.userDao().insertUser(User(name = name.value, username = username.value, password = password.value))
                        registrationSuccessful.value = true
                        name.value = ""
                        username.value = ""
                        password.value = ""
                        confirmPassword.value = ""
                    }
                }
            },
            modifier = Modifier.size(150.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary
            )
        ) {
            Text(
                text = "REGISTER",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ClickableText(
            text = AnnotatedString("Already Have Account! Login"),
            onClick = { offset ->
                navController.navigate("login")
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

//@Preview(showBackground = true)
//@Composable
//fun RegisterPagePreview() {
//    RequestAPITheme {
//        Register()
//    }
//}