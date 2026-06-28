package com.example.astroyorum.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore extension
val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_profile")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        val KEY_NAME = stringPreferencesKey("name")
        val KEY_BIRTH_DAY = intPreferencesKey("birth_day")
        val KEY_BIRTH_MONTH = intPreferencesKey("birth_month")
        val KEY_BIRTH_YEAR = intPreferencesKey("birth_year")
        val KEY_BIRTH_HOUR = intPreferencesKey("birth_hour")
        val KEY_BIRTH_MINUTE = intPreferencesKey("birth_minute")
        val KEY_BIRTH_CITY = stringPreferencesKey("birth_city")
        val KEY_ZODIAC_ID = intPreferencesKey("zodiac_id")
        val KEY_ONBOARDING_DONE = intPreferencesKey("onboarding_done")
    }

    val userProfile: Flow<UserProfile> = context.userDataStore.data.map { prefs ->
        UserProfile(
            name = prefs[KEY_NAME] ?: "",
            birthDay = prefs[KEY_BIRTH_DAY] ?: 1,
            birthMonth = prefs[KEY_BIRTH_MONTH] ?: 1,
            birthYear = prefs[KEY_BIRTH_YEAR] ?: 1990,
            birthHour = prefs[KEY_BIRTH_HOUR] ?: 12,
            birthMinute = prefs[KEY_BIRTH_MINUTE] ?: 0,
            birthCity = prefs[KEY_BIRTH_CITY] ?: "",
            zodiacSignId = prefs[KEY_ZODIAC_ID] ?: 0
        )
    }

    val isOnboardingDone: Flow<Boolean> = context.userDataStore.data.map { prefs ->
        (prefs[KEY_ONBOARDING_DONE] ?: 0) == 1
    }

    suspend fun saveProfile(profile: UserProfile) {
        context.userDataStore.edit { prefs ->
            prefs[KEY_NAME] = profile.name
            prefs[KEY_BIRTH_DAY] = profile.birthDay
            prefs[KEY_BIRTH_MONTH] = profile.birthMonth
            prefs[KEY_BIRTH_YEAR] = profile.birthYear
            prefs[KEY_BIRTH_HOUR] = profile.birthHour
            prefs[KEY_BIRTH_MINUTE] = profile.birthMinute
            prefs[KEY_BIRTH_CITY] = profile.birthCity
            prefs[KEY_ZODIAC_ID] = calculateZodiacId(profile.birthDay, profile.birthMonth)
            prefs[KEY_ONBOARDING_DONE] = 1
        }
    }

    suspend fun resetOnboarding() {
        context.userDataStore.edit { prefs ->
            prefs[KEY_ONBOARDING_DONE] = 0
        }
    }
}
