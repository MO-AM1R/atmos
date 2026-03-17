package com.example.atmos.data.datasource.local
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.atmos.data.enums.Language
import com.example.atmos.data.enums.LocationOption
import com.example.atmos.data.enums.TemperatureUnit
import com.example.atmos.data.enums.WindUnit
import com.example.atmos.domain.model.StoredPoint
import com.example.atmos.domain.model.UserPreferences
import com.example.atmos.utils.AppConstants.SharedPreferences.Keys
import com.example.atmos.utils.LocalizationHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserPreferencesDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @param:ApplicationContext private val context: Context,
) : UserPreferencesDataStore {

    private val userPreferences: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { prefs ->
            val lat = prefs[Keys.LATITUDE]
            val lon = prefs[Keys.LONGITUDE]
            UserPreferences(
                locationOption = LocationOption.valueOf(
                    prefs[Keys.LOCATION_OPTION] ?: LocationOption.GPS.name
                ),
                temperatureUnitOption = TemperatureUnit.valueOf(
                    prefs[Keys.TEMPERATURE_UNIT] ?: TemperatureUnit.CELSIUS.name
                ),
                windUnitOption = WindUnit.valueOf(
                    prefs[Keys.WIND_UNIT] ?: WindUnit.METERS_PER_SECOND.name
                ),
                languageOption = Language.valueOf(
                    prefs[Keys.LANGUAGE] ?: Language.ENGLISH.name
                ),
                storedPoint = if (lat != null && lon != null) {
                    StoredPoint(latitude = lat, longitude = lon)
                } else null
            )
        }

    override suspend fun hasSeenOnboarding(): Boolean =
        dataStore.data.first()[Keys.HAS_SEEN_ONBOARDING] ?: false

    override suspend fun seeOnboarding() {
        dataStore.edit { prefs ->
            prefs[Keys.HAS_SEEN_ONBOARDING] = true
        }
        val existing = dataStore.data.first()
        if (existing[Keys.TEMPERATURE_UNIT] == null) {
            saveUserPreferences(null)
        }
    }

    override suspend fun saveUserPreferences(userPreferences: UserPreferences?) {
        val prefs = userPreferences ?: UserPreferences(
            locationOption = LocationOption.GPS,
            temperatureUnitOption = TemperatureUnit.CELSIUS,
            windUnitOption = WindUnit.METERS_PER_SECOND,
            languageOption = Language.ENGLISH,
            storedPoint = null
        )

        dataStore.edit { store ->
            store[Keys.LOCATION_OPTION] = prefs.locationOption.name
            store[Keys.TEMPERATURE_UNIT] = prefs.temperatureUnitOption.name
            store[Keys.WIND_UNIT] = prefs.windUnitOption.name
            store[Keys.LANGUAGE] = prefs.languageOption.name
            prefs.storedPoint?.let { point ->
                store[Keys.LATITUDE] = point.latitude
                store[Keys.LONGITUDE] = point.longitude
            }
        }
    }

    override fun getUserPreferences(): Flow<UserPreferences> {
        return userPreferences
    }

    override suspend fun saveStoredPoint(point: StoredPoint) {
        dataStore.edit { prefs ->
            prefs[Keys.LATITUDE] = point.latitude
            prefs[Keys.LONGITUDE] = point.longitude
        }
    }

    override suspend fun saveStoredLocationName(name: String) {
        dataStore.edit { prefs ->
            prefs[Keys.STORED_LOCATION_NAME] = name
        }
    }

    override suspend fun getStoredLocationName(): String? =
        dataStore.data.first()[Keys.STORED_LOCATION_NAME]

    override suspend fun saveLocationOption(option: LocationOption) {
        dataStore.edit { prefs ->
            prefs[Keys.LOCATION_OPTION] = option.name
        }
    }

    override suspend fun saveTemperatureUnit(unit: TemperatureUnit) {
        dataStore.edit { prefs ->
            prefs[Keys.TEMPERATURE_UNIT] = unit.name
        }
    }

    override suspend fun saveWindUnit(unit: WindUnit) {
        dataStore.edit { prefs ->
            prefs[Keys.WIND_UNIT] = unit.name
        }
    }

    override suspend fun saveLanguage(language: Language) {
        dataStore.edit { prefs ->
            prefs[Keys.LANGUAGE] = language.name
        }

        LocalizationHelper.saveLanguageToPrefs(context, language)
    }
}