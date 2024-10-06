package com.example.farmbridge.ui.theme.Navigation

sealed class Screen(val route:String){
    object Splash: Screen("splash")
    object Register: Screen("register")
    object SelectLanguage: Screen("selectlanguage")
    object  Dashboard :Screen("dashboard")
    object Login:Screen("login")
    object ForgotPassword:Screen("forgotpassword")

}