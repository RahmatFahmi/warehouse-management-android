package com.rahmat.project1

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.rahmat.project1.common.network.LocalNetworkStatus
import com.rahmat.project1.common.network.NetworkMonitor
import com.rahmat.project1.common.network.connectivityState
import com.rahmat.project1.common.utils.language.LanguageManager
import com.rahmat.project1.common.utils.theme.ThemeViewModel
import com.rahmat.project1.ui.MainViewModel
import com.rahmat.project1.ui.components.ConnectivityBanner
import com.rahmat.project1.ui.navigation.AppNavGraph
import com.rahmat.project1.ui.theme.Project1Theme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor
    
    private val mainViewModel: MainViewModel by viewModels()

    override fun attachBaseContext(newBase: Context) {
        val languageManager = LanguageManager(newBase)
        val languageCode = languageManager.getLanguage()
        val locale = Locale.forLanguageTag(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        config.setLocales(LocaleList(locale))

        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition {
            mainViewModel.isOnboardingCompleted.value == null
        }

        setContent {
            val isOnline by connectivityState(networkMonitor)
            val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsState()
            val themeViewModel: ThemeViewModel = viewModel()

            if (isOnboardingCompleted != null) {
                CompositionLocalProvider(
                    LocalNetworkStatus provides isOnline
                ) {
                    Project1Theme(themeMode = themeViewModel.themeMode) {
                        val navController = rememberNavController()
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                ConnectivityBanner(
                                    isVisible = !isOnline,
                                    modifier = Modifier.statusBarsPadding()
                                )
                                Box(modifier = Modifier.weight(1f)) {
                                    AppNavGraph(
                                        navController = navController,
                                        startDestination = if (isOnboardingCompleted == true) "auth" else "intro",
                                        onOnboardingFinish = {
                                            mainViewModel.completeOnboarding()
                                            navController.navigate("auth") {
                                                popUpTo("intro") { inclusive = true }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
