package com.gokcank.astroyorum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.gokcank.astroyorum.theme.AstroYorumTheme
import com.gokcank.astroyorum.data.UserPreferencesRepository

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    // In-App Update kontrolü
    checkForAppUpdate()

    setContent {
      val permissionLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
          contract = androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
      ) { isGranted: Boolean ->
          if (isGranted) {
              com.gokcank.astroyorum.scheduler.NotificationScheduler().scheduleDailyHoroscopeNotification(this)
          }
      }
      val repository = remember { UserPreferencesRepository(this) }
      val themePref by repository.themePreference.collectAsState(initial = 0)
      val appConfigRepository = remember { com.gokcank.astroyorum.data.AppConfigRepository(this) }

      androidx.compose.runtime.LaunchedEffect(Unit) {
          appConfigRepository.fetchRemoteConfig()
          
          if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
              if (androidx.core.content.ContextCompat.checkSelfPermission(
                      this@MainActivity,
                      android.Manifest.permission.POST_NOTIFICATIONS
                  ) != android.content.pm.PackageManager.PERMISSION_GRANTED
              ) {
                  permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
              } else {
                  com.gokcank.astroyorum.scheduler.NotificationScheduler().scheduleDailyHoroscopeNotification(this@MainActivity)
              }
          } else {
              com.gokcank.astroyorum.scheduler.NotificationScheduler().scheduleDailyHoroscopeNotification(this@MainActivity)
          }
      }

      AstroYorumTheme(themePref = themePref) { 
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) { 
          MainNavigation() 
        } 
      }
    }
  }

  private fun checkForAppUpdate() {
      val appUpdateManager = com.google.android.play.core.appupdate.AppUpdateManagerFactory.create(this)
      val appUpdateInfoTask = appUpdateManager.appUpdateInfo
      appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
          if (appUpdateInfo.updateAvailability() == com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
              && appUpdateInfo.isUpdateTypeAllowed(com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE)
          ) {
              try {
                  @Suppress("DEPRECATION")
                  appUpdateManager.startUpdateFlowForResult(
                      appUpdateInfo,
                      com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE,
                      this,
                      1001
                  )
              } catch (e: Exception) {
                  // Hata yoksay
              }
          }
      }
  }
}
