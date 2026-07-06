package com.gokcank.astroyorum

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

// ─── Navigasyon Anahtarları ────────────────────────────────────────────────────
@Serializable data object Main : NavKey
@Serializable data object HomeTab : NavKey
@Serializable data object ZodiacTab : NavKey
@Serializable data object TarotTab : NavKey
@Serializable data object MoonTab : NavKey
@Serializable data object ProfileTab : NavKey

// Alt ekranlar
@Serializable data class ZodiacDetail(val signId: Int) : NavKey
@Serializable data object BirthChartScreen : NavKey
@Serializable data object CompatibilityScreen : NavKey
@Serializable data object OnboardingScreen : NavKey
