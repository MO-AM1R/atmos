package com.example.atmos.utils

import android.content.Context
import android.content.res.Configuration
import androidx.core.content.edit
import com.example.atmos.data.enums.Language
import jakarta.inject.Singleton
import java.util.Locale

@Singleton
class LocalizationHelper {
    companion object {
        private const val PREFS_NAME   = "localization_prefs"
        private const val KEY_LANGUAGE = "language"
        private const val KEY_SPLASH_SEEN = "splash_seen"


        fun hasSplashBeenSeen(context: Context): Boolean {
            return context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_SPLASH_SEEN, false)
        }

        fun markSplashAsSeen(context: Context) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit {
                    putBoolean(KEY_SPLASH_SEEN, true)
                }
        }

        fun applyWithoutDI(context: Context): Context {
            val languageCode = context
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_LANGUAGE, null)

            languageCode ?: return context

            val language = try {
                Language.valueOf(languageCode)
            } catch (_: Exception) {
                return context
            }

            return applyLanguage(context, language)
        }

        fun saveLanguageToPrefs(context: Context, language: Language) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit {
                    putString(KEY_LANGUAGE, language.name)
                }
        }

        fun applyLanguage(context: Context, language: Language): Context {
            val locale = Locale.forLanguageTag(language.apiValue)
            Locale.setDefault(locale)

            val config = Configuration(context.resources.configuration)
            config.setLocale(locale)
            config.setLayoutDirection(locale)

            return context.createConfigurationContext(config)
        }
    }
}