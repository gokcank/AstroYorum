# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep WorkManager and Room from being obfuscated/stripped by R8
-keep class androidx.work.** { *; }
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.work.**
-dontwarn androidx.room.**

# Keep models for Serialization/Firestore
-keep class com.example.astroyorum.data.** { *; }

# standard
-keepattributes SourceFile,LineNumberTable
