package com.juanpablo0612.iefjt.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juanpablo0612.iefjt.ui.add_brand.AddBrandScreen
import com.juanpablo0612.iefjt.ui.add_element.AddElementScreen
import com.juanpablo0612.iefjt.ui.add_status.AddStatusScreen
import com.juanpablo0612.iefjt.ui.add_type.AddTypeScreen
import com.juanpablo0612.iefjt.ui.element_detail.ElementDetailScreen
import com.juanpablo0612.iefjt.ui.element_list.ElementListScreen
import com.juanpablo0612.iefjt.ui.home.HomeScreen
import com.juanpablo0612.iefjt.ui.login.LoginScreen
import com.juanpablo0612.iefjt.ui.register.RegisterScreen
import com.juanpablo0612.iefjt.ui.update_element_status.UpdateElementStatusScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        addLoginScreen(navController)
        addRegisterScreen(navController)
        addHomeScreen(navController)
        addAddElementScreen(navController)
        addAddBrandScreen(navController)
        addAddTypeScreen(navController)
        addAddStatusScreen(navController)
        addElementDetailScreen(navController)
        addUpdateElementStatusScreen(navController)
        addElementListScreen(navController)
    }
}

inline fun <reified T : Any> NavGraphBuilder.animatedComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = { slideInHorizontally { height -> height } },
        exitTransition = {
            slideOutHorizontally { height -> -height }
        },
        popEnterTransition = {
            slideInHorizontally { height -> -height }
        },
        popExitTransition = {
            slideOutHorizontally { height -> height }
        },
        content = content
    )
}

fun NavGraphBuilder.addLoginScreen(navController: NavController) {
    animatedComposable<Login> {
        LoginScreen(
            navigateToHome = {
                navController.navigate(Home) {
                    popUpTo(route = Login) {
                        inclusive = true
                    }
                }
            },
            navigateToRegister = {
                navController.navigate(Register) {
                    launchSingleTop = true
                }
            },
            navigateToForgotPassword = {}
        )
    }
}

fun NavGraphBuilder.addRegisterScreen(navController: NavController) {
    animatedComposable<Register> {
        RegisterScreen(
            navigateToHome = {
                navController.navigate(Home) {
                    popUpTo(route = Register) {
                        inclusive = true
                    }
                }
            },
            navigateToLogin = {
                navController.navigate(Login) {
                    popUpTo(route = Register) {
                        saveState = true
                        inclusive = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
        )
    }
}

fun NavGraphBuilder.addHomeScreen(navController: NavController) {
    animatedComposable<Home> {
        HomeScreen(
            navigateToLogin = {
                navController.navigate(Login) {
                    popUpTo(route = Home) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
            navigateToAddBrand = { navController.navigate(AddBrand) },
            navigateToAddType = { navController.navigate(AddType) },
            navigateToAddStatus = { navController.navigate(AddStatus) },
            navigateToElementDetail = {
                val elementDetail = ElementDetail(it)
                navController.navigate(elementDetail)
            },
            navigateToElementList = { brandId, typeId, statusId ->
                val elementList = ElementList(brandId, typeId, statusId)

                navController.navigate(elementList)
            }
        )
    }
}


fun NavGraphBuilder.addAddElementScreen(navController: NavController) {
    animatedComposable<AddElement> {
        AddElementScreen(
            navigateBack = { navController.navigateUp() },
            navigateToAddBrand = { navController.navigate(AddBrand) },
            navigateToAddType = { navController.navigate(AddType) },
            navigateToAddStatus = { navController.navigate(AddStatus) }
        )
    }
}

fun NavGraphBuilder.addAddBrandScreen(navController: NavController) {
    animatedComposable<AddBrand> {
        AddBrandScreen(
            navigateBack = { navController.navigateUp() }
        )
    }
}

fun NavGraphBuilder.addAddTypeScreen(navController: NavController) {
    animatedComposable<AddType> {
        AddTypeScreen(
            navigateBack = { navController.navigateUp() }
        )
    }
}

fun NavGraphBuilder.addAddStatusScreen(navController: NavController) {
    animatedComposable<AddStatus> {
        AddStatusScreen(
            navigateBack = { navController.navigateUp() }
        )
    }
}

fun NavGraphBuilder.addElementDetailScreen(navController: NavController) {
    animatedComposable<ElementDetail> {
        ElementDetailScreen(
            navigateBack = { navController.navigateUp() },
            navigateToUpdateElementStatus = { elementId, statusId ->
                val updateElementStatus = UpdateElementStatus(elementId, statusId)
                navController.navigate(updateElementStatus)
            }
        )
    }
}

fun NavGraphBuilder.addUpdateElementStatusScreen(navController: NavController) {
    animatedComposable<UpdateElementStatus> {
        UpdateElementStatusScreen(
            navigateBack = { navController.navigateUp() }
        )
    }
}

fun NavGraphBuilder.addElementListScreen(navController: NavController) {
    animatedComposable<ElementList> {
        ElementListScreen(
            navigateBack = { navController.navigateUp() },
            navigateToElementDetail = { elementId ->
                val elementDetail = ElementDetail(elementId)
                navController.navigate(elementDetail)
            },
            navigateToAddElement = { navController.navigate(AddElement) }
        )
    }
}