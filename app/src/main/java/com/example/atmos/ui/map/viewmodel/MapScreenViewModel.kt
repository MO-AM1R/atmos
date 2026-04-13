package com.example.atmos.ui.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.domain.model.SearchResultItem
import com.example.atmos.ui.map.state.MapNavigationEvent
import com.example.atmos.ui.map.state.MapScreenEvent
import com.example.atmos.ui.map.state.MapScreenState
import com.example.atmos.ui.map.state.MapScreenUIState
import com.mapbox.search.ResponseInfo
import com.mapbox.search.SearchOptions
import com.mapbox.search.SearchSuggestionsCallback
import com.mapbox.search.common.RestrictedMapboxSearchAPI
import com.mapbox.search.result.SearchSuggestion
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@HiltViewModel
class MapScreenViewModel @Inject constructor(

) : ViewModel() {
    private val searchExecutor = Executors.newSingleThreadExecutor()
    private val _mapState = MutableStateFlow(MapScreenUIState())
    val mapState = _mapState.asStateFlow()
    private val _mapNavigationEvent = Channel<MapNavigationEvent>(Channel.BUFFERED)
    val mapNavigationEvent = _mapNavigationEvent.receiveAsFlow()


    fun onEvent(event: MapScreenEvent) {
        when (event) {
            MapScreenEvent.OnLoadingCurrentLocation -> {
                setScreenState(MapScreenState.Loading)
            }

            is MapScreenEvent.OnSelectPoint -> _mapState.update {
                it.copy(selectedPoint = event.point)
            }

            is MapScreenEvent.OnLoadedCurrentLocation -> _mapState.update {
                it.copy(
                    myLocation = event.point,
                    screenState =
                        if (event.point == null)
                            MapScreenState.Error("Please try again later")
                        else
                            MapScreenState.Success
                )
            }

            is MapScreenEvent.OnSave -> {
                viewModelScope.launch {
                    _mapNavigationEvent.send(MapNavigationEvent.SaveAndGoBack(event.point))
                }
            }

            MapScreenEvent.ClearSearch -> {
                updateMapSearchState(
                    query = "",
                    suggestionsSearch = emptyList(),
                    showSuggestions = false
                )
            }

            is MapScreenEvent.OnSelectSearchItem -> {
                updateMapSearchState(
                    query = event.name,
                    suggestionsSearch = emptyList(),
                    showSuggestions = false
                )
            }

            is MapScreenEvent.OnQueryChanged -> updateQuery(event.newQuery)

            is MapScreenEvent.OnBack -> {
                viewModelScope.launch {
                    _mapNavigationEvent.send(MapNavigationEvent.GoBack)
                }
            }
        }
    }

    fun updateMapSearchState(
        query: String? = null,
        suggestionsSearch: List<SearchResultItem>? = null,
        isSearching: Boolean? = null,
        showSuggestions: Boolean? = null,
    ) {
        _mapState.update {
            it.copy(
                mapSearchEngineState = it.mapSearchEngineState.copy(
                    query = query ?: it.mapSearchEngineState.query,
                    suggestionsSearch = suggestionsSearch
                        ?: it.mapSearchEngineState.suggestionsSearch,
                    isSearching = isSearching ?: it.mapSearchEngineState.isSearching,
                    showSuggestions = showSuggestions ?: it.mapSearchEngineState.showSuggestions,
                )
            )
        }
    }

    @OptIn(RestrictedMapboxSearchAPI::class)
    fun updateQuery(newQuery: String) {
        updateMapSearchState(query = newQuery)

        if (newQuery.length > 2) {

            updateMapSearchState(
                isSearching = true,
                showSuggestions = true
            )

            viewModelScope.launch {
                val searchEngine = _mapState.value.mapSearchEngineState.searchEngine

                searchEngine.search(
                    query = newQuery,
                    options = SearchOptions(limit = 5),
                    callback = object : SearchSuggestionsCallback {

                        override fun onSuggestions(
                            suggestions: List<SearchSuggestion>,
                            responseInfo: ResponseInfo
                        ) {

                            val mappedResults = suggestions.map { suggestion ->
                                SearchResultItem(
                                    name = suggestion.name,
                                    address = suggestion.address?.formattedAddress(),
                                    point = suggestion.coordinate
                                )
                            }

                            updateMapSearchState(
                                isSearching = false,
                                suggestionsSearch = mappedResults
                            )
                        }

                        override fun onError(e: Exception) {
                            updateMapSearchState(isSearching = false)
                        }
                    }
                )
            }

        } else {
            updateMapSearchState(
                suggestionsSearch = emptyList(),
                showSuggestions = false
            )
        }
    }

    fun setScreenState(state: MapScreenState) {
        _mapState.update { it.copy(screenState = state) }
    }

    override fun onCleared() {
        super.onCleared()

        if (!searchExecutor.isShutdown || !searchExecutor.isTerminated)
            searchExecutor.shutdown()
    }
}