package com.juanpablo0612.iefjt.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.juanpablo0612.iefjt.R

@Composable
fun BottomNavigationBar(
    currentDestination: NavDestination?,
    navigateToRoute: (Any) -> Unit
) {
    NavigationBar {
        bottomBarScreens.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(
                        screen.route::class
                    )
                } == true,
                onClick = { navigateToRoute(screen.route) },
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(text = stringResource(screen.titleId)) }
            )
        }
    }
}

val bottomBarScreens = listOf(
    BottomBarScreen(
        titleId = R.string.home_title,
        route = Home,
        icon = Icons.Default.Home
    ),
    BottomBarScreen(
        titleId = R.string.add_element_title,
        route = AddElement,
        icon = Icons.Default.Add
    )
)

data class BottomBarScreen<T : Any>(val titleId: Int, val route: T, val icon: ImageVector)