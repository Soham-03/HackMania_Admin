package com.example.hackmania_admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun BottomNavigationGraph(
    navController: NavHostController
){
    NavHost(navController = navController, startDestination = BottomBarScreen.Listed.route){
        composable(route = BottomBarScreen.Listed.route){
            ListedScreen()
        }
        composable(route = BottomBarScreen.Analytics.route){
            AnalyticsScreen()
        }
    }
}