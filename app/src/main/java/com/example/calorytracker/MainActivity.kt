package com.example.calorytracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calorytracker.navigation.Route
import com.example.calorytracker.ui.theme.CaloryTrackerTheme
import com.example.core.domain.preferences.Preferences
import com.example.onboarding_presentation.activity.ActivityScreen
import com.example.onboarding_presentation.age.AgeScreen
import com.example.onboarding_presentation.gender.GenderScreen
import com.example.onboarding_presentation.goal.GoalScreen
import com.example.onboarding_presentation.height.HeightScreen
import com.example.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.example.onboarding_presentation.weight.WeightScreen
import com.example.onboarding_presentation.welcome.WelcomeScreen
import com.example.tracker_presentation.search_food.SearchFoodScreen
import com.example.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences.OnboardingPreferences

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()

        setContent {
            CaloryTrackerTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if (shouldShowOnboarding) Route.WELCOME else Route.TRACKER_OVERVIEW
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(onNextClick = {
                                navController.navigate(route = Route.GENDER)
                            })
                        }
                        composable(Route.GENDER) {
                            GenderScreen(
                                onNextClick = {
                                    navController.navigate(route = Route.AGE)
                                }
                            )
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                snakbarState = snackbarHostState,
                                onNextClick = {
                                    navController.navigate(route = Route.HEIGHT)
                                }
                            )
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                snakbarState = snackbarHostState,
                                onNextClick = {
                                    navController.navigate(route = Route.WEIGHT)
                                }
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                snakbarState = snackbarHostState,
                                onNextClick = {
                                    navController.navigate(route = Route.ACTIVITY)
                                }
                            )
                        }
                        composable(Route.ACTIVITY) {
                            ActivityScreen(
                                onNextClick = {
                                    navController.navigate(route = Route.GOAL)
                                }
                            )
                        }
                        composable(Route.GOAL) {
                            GoalScreen(
                                onNextClick = {
                                    navController.navigate(route = Route.NUTRIENT_GOAL)
                                }
                            )
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                snakbarState = snackbarHostState,
                                onNextClick = {
                                    navController.navigate(route = Route.TRACKER_OVERVIEW)
                                }
                            )
                        }

                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                onNavigateToSearchScreen = { mealName, day, month, year ->
                                    navController.navigate(
                                        route = Route.SEARCH + "/$mealName" + "/$day" + "/$month" + "/$year"
                                    )
                                }
                            )
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            it.arguments?.apply {
                                val mealName = getString("mealName")!!
                                val dayOfMonth = getInt("dayOfMonth")
                                val month = getInt("month")
                                val year = getInt("year")

                                SearchFoodScreen(
                                    snakbarState = snackbarHostState,
                                    onNavigateUp = { navController.navigateUp() },
                                    mealName = mealName,
                                    dayOfMonth = dayOfMonth,
                                    month = month,
                                    year = year
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}