package com.example.astroyorum

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.astroyorum.data.UserPreferencesRepository
import com.example.astroyorum.data.UserProfile
import com.example.astroyorum.theme.*
import com.example.astroyorum.ui.birthchart.BirthChartScreen
import com.example.astroyorum.ui.home.HomeScreen
import com.example.astroyorum.ui.moon.MoonScreen
import com.example.astroyorum.ui.onboarding.OnboardingScreen
import com.example.astroyorum.ui.profile.ProfileScreen
import com.example.astroyorum.ui.splash.SplashScreen
import com.example.astroyorum.ui.tarot.TarotScreen
import com.example.astroyorum.ui.zodiac.ZodiacScreen
import kotlinx.coroutines.launch

@Composable
fun MainNavigation() {
    val context = LocalContext.current
    val repo = remember { UserPreferencesRepository(context) }
    val scope = kotlinx.coroutines.MainScope()

    val isOnboardingDone by repo.isOnboardingDone.collectAsStateWithLifecycle(false)
    val userProfile by repo.userProfile.collectAsStateWithLifecycle(UserProfile())
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
        SplashScreen(
            onSplashFinished = { showSplash = false }
        )
    } else if (!isOnboardingDone) {
        OnboardingScreen(
            onComplete = { profile ->
                scope.launch { repo.saveProfile(profile) }
            }
        )
    } else {
        AstroMainApp(
            userProfile = userProfile,
            onSaveProfile = { profile ->
                scope.launch { repo.saveProfile(profile) }
            }
        )
    }
}

@Composable
private fun AstroMainApp(
    userProfile: UserProfile,
    onSaveProfile: (UserProfile) -> Unit,
    horoscopeViewModel: com.example.astroyorum.ui.main.HoroscopeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var currentTab by remember { mutableIntStateOf(0) }
    val backStack = rememberNavBackStack(HomeTab)

    val navItems = listOf(
        Triple("Ana Sayfa", "🏠", 0),
        Triple("Burçlar", "⭐", 1),
        Triple("Tarot", "🃏", 2),
        Triple("Ay", "🌙", 3),
        Triple("Profil", "👤", 4)
    )

    val horoscopeUiState by horoscopeViewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        // Ana içerik
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp)
        ) {
            when (currentTab) {
                0 -> HomeScreen(
                    userProfile = userProfile,
                    horoscopeUiState = horoscopeUiState,
                    onNavigateToZodiac = { currentTab = 1 },
                    onNavigateToTarot = { currentTab = 2 },
                    onNavigateToMoon = { currentTab = 3 },
                    onNavigateToBirthChart = { backStack.add(BirthChartScreen) }
                )
                1 -> ZodiacScreen(
                    initialSignId = userProfile.zodiacSignId,
                    horoscopeUiState = horoscopeUiState
                )
                2 -> TarotScreen()
                3 -> MoonScreen(horoscopeUiState = horoscopeUiState)
                4 -> ProfileScreen(
                    userProfile = userProfile,
                    onSaveProfile = onSaveProfile
                )
            }
        }

        // Alt navigasyon çubuğu
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(CosmicDeepPurple.copy(0f), CosmicMidnight)
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CosmicMidnight)
                    .border(
                        topStart = 0.dp, topEnd = 0.dp,
                        bottomStart = 0.dp, bottomEnd = 0.dp
                    )
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                navItems.forEach { (label, emoji, tabIndex) ->
                    val isSelected = currentTab == tabIndex
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { currentTab = tabIndex }
                            .padding(vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(GoldenStardust.copy(0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = emoji, fontSize = 18.sp)
                            }
                        } else {
                            Text(text = emoji, fontSize = 18.sp)
                        }
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            color = if (isSelected) GoldenStardust else CometGray,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Doğum haritası ekranı (backstack)
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<HomeTab> { /* Ana tab navigasyonu yukarıda */ }
                entry<BirthChartScreen> {
                    BirthChartScreen(userProfile = userProfile)
                }
            }
        )
    }
}

// Border extension for selective sides
private fun Modifier.border(
    topStart: androidx.compose.ui.unit.Dp,
    topEnd: androidx.compose.ui.unit.Dp,
    bottomStart: androidx.compose.ui.unit.Dp,
    bottomEnd: androidx.compose.ui.unit.Dp
) = this
