package com.example.mycomposecookbook.screen

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home")
    object LoginScreen : Screens("login")
    object ForgotScreen : Screens("forgot")
    object RegistrationScreen : Screens("register") {
        val routeWithArgs: String = "${RegistrationScreen}?email={email}"
    }


}

@RequiresApi(Build.VERSION_CODES.O)
operator fun LocalDate.component1(): Int {
    return year
}

@RequiresApi(Build.VERSION_CODES.O)
operator fun LocalDate.component2(): Int {
    return year
}

@RequiresApi(Build.VERSION_CODES.O)
operator fun LocalDate.component3(): Int {
    return year
}