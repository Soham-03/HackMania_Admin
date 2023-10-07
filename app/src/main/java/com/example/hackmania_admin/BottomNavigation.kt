package com.example.hackmania_admin

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hackmania_admin.ui.theme.Pink40
import com.example.hackmania_admin.ui.theme.lightpeach
import org.checkerframework.common.subtyping.qual.Bottom


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(){
    val navController = rememberNavController()
//    val state by viewModel.state.collectAsState()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButton = {
            val context = LocalContext.current
            FloatingActionButton(
                containerColor = lightpeach,
                onClick = {
                    val intent = Intent(context, AddProduct::class.java)
                    context.startActivity(intent)
                }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "", Modifier.size(30.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {it.calculateBottomPadding()
        BottomNavigationGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Listed,
        BottomBarScreen.Analytics
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        screens.forEach { screen->
            currentDestination?.let{
                AddItem(screen = screen, currentDestination =it, navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination,
    navController: NavHostController
){
    val selected = currentDestination.hierarchy.any{ it.route == screen.route }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
    )
    {
        val bgColor: Color by animateColorAsState(if (selected) Pink40 else Color.Black,
            animationSpec = tween(500, easing = LinearEasing), label = ""
        )

        Image(
            painter = painterResource(id = screen.icon),
            contentDescription = "bottom icon",
            colorFilter = ColorFilter.tint(bgColor),
            modifier = Modifier
                .padding(top = 8.dp)
                .size(20.dp)
        )
        Text(
            text = screen.title,
            color = bgColor,
            fontWeight = FontWeight.Light
        )
    }
}