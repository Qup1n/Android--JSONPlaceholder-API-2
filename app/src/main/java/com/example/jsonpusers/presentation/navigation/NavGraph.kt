package com.example.jsonpusers.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jsonpusers.presentation.ui.detail.UserDetailScreen
import com.example.jsonpusers.presentation.ui.list.UserListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "user_list"
    ) {
        composable("user_list") {
            UserListScreen(navController = navController)
        }

        composable(
            route = "user_detail/{userId}",
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            UserDetailScreen(navController = navController)
        }
    }
}