package com.example.atmos.utils

import com.example.atmos.domain.model.StoredPoint
import com.mapbox.geojson.Point
import com.mapbox.search.ApiType
import com.mapbox.search.ResponseInfo
import com.mapbox.search.ReverseGeoOptions
import com.mapbox.search.SearchCallback
import com.mapbox.search.SearchEngine
import com.mapbox.search.SearchEngineSettings
import com.mapbox.search.result.SearchResult
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class ReverseGeocodingHelper @Inject constructor() {

    private val searchEngine = SearchEngine.createSearchEngineWithBuiltInDataProviders(
        settings = SearchEngineSettings(),
        apiType = ApiType.GEOCODING
    )

    suspend fun getLocationName(point: StoredPoint): String? =
        suspendCancellableCoroutine { continuation ->
            searchEngine.search(
                options = ReverseGeoOptions(
                    center = Point.fromLngLat(point.longitude, point.latitude),
                    limit = 1
                ),
                callback = object : SearchCallback {
                    override fun onResults(
                        results: List<SearchResult>,
                        responseInfo: ResponseInfo
                    ) {
                        val name = results.firstOrNull()?.name
                        continuation.resume(name)
                    }

                    override fun onError(e: Exception) {
                        continuation.resume(null)
                    }
                }
            )
        }
}