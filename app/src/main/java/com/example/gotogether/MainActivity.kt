package com.example.gotogether

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gotogether.presentation.navigation.BottomNavigationBar
import com.example.gotogether.presentation.navigation.NavBarItems
import com.example.gotogether.presentation.navigation.NavigationController
import com.example.gotogether.ui.theme.GoTogetherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoTogetherTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val bottomBarRoutes = NavBarItems.BarItems.map { it.route }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.statusBars),
                    bottomBar = {
                        if (currentRoute in bottomBarRoutes) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) {
                    Column {
                        NavigationController(
                            navController = navController,
                            modifier = Modifier.weight(1f)
                        )
//                        BottomNavigationBar(
//                            navController = navController
//                        )
                    }
                }
            }
        }
    }
}
