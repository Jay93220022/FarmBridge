package com.example.farmbridge.ui.theme.Navigation

sealed class Screen(val route:String){
    object Splash: Screen("splash")
    object Register: Screen("register")
    object SelectLanguage: Screen("selectlanguage")
    object  Dashboard :Screen("dashboard")
    object Login:Screen("login")
    object ForgotPassword:Screen("forgotpassword")
    object MarketPrice :Screen("marketprice")
    object  WeatherScreen:Screen("weatherscreen")
    object  MainScreen:Screen("mainscreen")
object AddCropnScreen:Screen("addcropscreen")
    object LatestUpdates : Screen("latest_updates")
    object  ProfileScreen:Screen("profileScreen")
object PlantDiseasesRecognitionScreen :Screen("cropselectionscreen")
    object CustomerLogin :Screen("CustomerLogin")
    object CustomerDashboard:Screen("CusDashboard")
    object RoleSelectionScreen:Screen("RoleSelectionScreen")
}