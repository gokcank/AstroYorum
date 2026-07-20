package com.gokcank.astroyorum.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.gokcank.astroyorum.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class AppConfigRow(
    val key: String,
    val value: JsonElement,
    val description: String? = null
)

class AppConfigRepository(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_config_prefs", Context.MODE_PRIVATE)

    // Analytics & Remote Config için kullanılacak Supabase istemcisi
    private val supabase = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
    }

    suspend fun fetchRemoteConfig(): Map<String, Any> {
        val lastFetchTime = prefs.getLong("last_fetch_time", 0L)
        val currentTime = System.currentTimeMillis()
        val twentyFourHoursInMillis = 24 * 60 * 60 * 1000L

        // Cache geçerliyse (24 saat dolmamışsa) lokalden döndür
        if (currentTime - lastFetchTime < twentyFourHoursInMillis) {
            if (BuildConfig.DEBUG) {
                Log.d("AppConfigRepository", "Cache geçerli, lokalden okunuyor.")
            }
            val maintenanceMode = prefs.getBoolean("maintenance_mode", false)
            val welcomeMessage = prefs.getString("welcome_message", "") ?: ""
            return mapOf(
                "maintenance_mode" to maintenanceMode,
                "welcome_message" to welcomeMessage
            )
        }

        // Cache yoksa veya süresi dolmuşsa Supabase'den çek
        return try {
            val rows = supabase.from("app_config")
                .select()
                .decodeList<AppConfigRow>()
            
            val configMap = mutableMapOf<String, Any>()
            rows.forEach { row ->
                val primitive = row.value.jsonPrimitive
                if (primitive.isString) {
                    configMap[row.key] = primitive.content
                } else if (primitive.booleanOrNull != null) {
                    configMap[row.key] = primitive.booleanOrNull!!
                }
            }
            
            // Cache'e yaz
            prefs.edit().apply {
                putLong("last_fetch_time", currentTime)
                putBoolean("maintenance_mode", configMap["maintenance_mode"] as? Boolean ?: false)
                putString("welcome_message", configMap["welcome_message"] as? String ?: "")
                apply()
            }

            if (BuildConfig.DEBUG) {
                Log.d("AppConfigRepository", "Remote Config Supabase'den başarıyla çekildi ve önbelleğe alındı: $configMap")
            }
            configMap
        } catch (e: Exception) {
            Log.e("AppConfigRepository", "Remote Config çekilirken hata oluştu", e)
            
            // Hata olursa yine de elimizdeki eski cache'i dönelim (Fallback)
            val maintenanceMode = prefs.getBoolean("maintenance_mode", false)
            val welcomeMessage = prefs.getString("welcome_message", "") ?: ""
            mapOf(
                "maintenance_mode" to maintenanceMode,
                "welcome_message" to welcomeMessage
            )
        }
    }
}
