package com.example.mapd721_a3_ahmadabbas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("animation app") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding -> // Use the padding parameter here
        NavHost(navController, startDestination = "home", modifier = Modifier.padding(padding)) {
            composable("home") { HomeScreen(navController) }
            composable("transition") { TransitionScreen(navController) }
            composable("scale") { ScaleScreen(navController) }
            composable("infinite") { InfiniteScreen(navController) }
            composable("exit") { ExitScreen(navController) }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("transition") },
        ) {
            Text("Transition Animation")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("scale") },
        ) {
            Text("Scale Animation")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("infinite") },
        ) {
            Text("Infinite Animation")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("exit") },
        ) {
            Text("Exit Animation")
        }
    }
}

@Composable
fun TransitionScreen(navController: NavController) {
    var isRocketVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { isRocketVisible = !isRocketVisible }) {
            Text(if (isRocketVisible) "Land Rocket" else "Launch Rocket")
        }

        AnimatedVisibility(
            visible = isRocketVisible,
            enter = slideInVertically(
                initialOffsetY = { -it }, // Start from below the screen
                animationSpec = tween(durationMillis = 1000)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it }, // End at the top of the screen
                animationSpec = tween(durationMillis = 1000)
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.rocket),
                contentDescription = "Rocket",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}

@Composable
fun ScaleScreen(navController: NavController) {
    var scale by remember { mutableStateOf(1f) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { scale = if (scale == 1f) 1.5f else 1f },
            modifier = Modifier.scale(scale).animateContentSize()
        ) {
            Text("Press Me")
        }
    }
}

@Composable
fun InfiniteScreen(navController: NavController) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.butterfly), // Use butterfly.png
            contentDescription = "Butterfly",
            modifier = Modifier
                .size(100.dp)
                .scale(scale)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExitScreen(navController: NavController) {
    var isImageVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = isImageVisible,
            exit = fadeOut(animationSpec = tween(durationMillis = 1000))
        ) {
            Image(
                painter = painterResource(id = R.drawable.rocket), // Make sure to use the correct resource ID
                contentDescription = "Rocket",
                modifier = Modifier.size(200.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom
            Button(
                onClick = { isImageVisible = false }
            ) {
                Text("Exit Animation")
            }
        }
    }
}
