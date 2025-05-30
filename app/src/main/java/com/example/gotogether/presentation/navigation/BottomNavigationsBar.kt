package com.example.gotogether.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DirectionsCarFilled
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gotogether.ui.theme.MediumGray

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Surface(
        tonalElevation = 8.dp, // або shadowElevation, якщо потрібно
        shadowElevation = 8.dp,
        color = Color.White,
        modifier = modifier.height(80.dp)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            val backStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry.value?.destination?.route
            NavBarItems.BarItems.forEach { navItem ->
                NavigationBarItem(
                    selected = currentRoute == navItem.route,
                    icon = {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = navItem.title,
                            tint = MediumGray
                        )
                    },
                    label = {
                        Text(
                            text = navItem.title,
                            fontSize = 8.sp,
                            color = MediumGray
                        )
                    },
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

object NavBarItems {
    val BarItems = listOf<BarItem>(
        BarItem(
            title = "Пошук",
            icon = Icons.Default.Search,
            route = "search_trips"
        ),
        BarItem(
            title = "Пропозиція",
            icon = Icons.Default.AddCircle,
            route = "proposition"
        ),
        BarItem(
            title = "Мої пропозиції",
            icon = Icons.Default.DirectionsCarFilled,
            route = "my_propositions"
        ),
        BarItem(
            title = "Мої поїздки",
            icon = Icons.Default.DateRange,
            route = "my_trips"
        ),
        BarItem(
            title = "Профіль",
            icon = Icons.Default.AccountCircle,
            route = "profile"
        )
    )
}
