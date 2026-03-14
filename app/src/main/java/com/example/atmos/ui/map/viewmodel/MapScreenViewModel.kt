package com.example.atmos.ui.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmos.domain.model.SearchResultItem
import com.example.atmos.ui.map.state.MapNavigationEvent
import com.example.atmos.ui.map.state.MapScreenEvent
import com.example.atmos.ui.map.state.MapScreenState
import com.example.atmos.ui.map.state.MapScreenUIState
import com.mapbox.search.ResponseInfo
import com.mapbox.search.SearchMultipleSelectionCallback
import com.mapbox.search.SearchOptions
import com.mapbox.search.SearchSuggestionsCallback
import com.mapbox.search.result.SearchResult
import com.mapbox.search.result.SearchSuggestion
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MapScreenViewModel @Inject constructor(

) : ViewModel() {
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

    fun updateQuery(newQuery: String) {
        updateMapSearchState(query = newQuery)

        if (newQuery.length > 2) {
            updateMapSearchState(isSearching = true, showSuggestions = true)
            viewModelScope.launch {
                _mapState.value.mapSearchEngineState.searchEngine.search(
                    query = newQuery,
                    options = SearchOptions(limit = 5),
                    callback = object : SearchSuggestionsCallback {
                        override fun onSuggestions(
                            suggestions: List<SearchSuggestion>,
                            responseInfo: ResponseInfo
                        ) {
                            _mapState.value.mapSearchEngineState.searchEngine.select(
                                suggestions = suggestions,
                                callback = object : SearchMultipleSelectionCallback {
                                    override fun onResult(
                                        suggestions: List<SearchSuggestion>,
                                        results: List<SearchResult>,
                                        responseInfo: ResponseInfo
                                    ) {
                                        updateMapSearchState(
                                            isSearching = false,
                                            suggestionsSearch = results.map { result ->
                                                SearchResultItem(
                                                    name = result.name,
                                                    address = result.address?.formattedAddress(),
                                                    point = result.coordinate
                                                )
                                            }
                                        )
                                    }

                                    override fun onError(e: Exception) {
                                        updateMapSearchState(isSearching = false)
                                    }
                                }
                            )
                        }

                        override fun onError(e: Exception) {
                            updateMapSearchState(isSearching = false)
                        }
                    }
                )
            }
        } else {
            updateMapSearchState(suggestionsSearch = emptyList(), showSuggestions = false)
        }
    }

    fun setScreenState(state: MapScreenState) {
        _mapState.update { it.copy(screenState = state) }
    }
}