package com.example.atmos.ui.map.state

import com.example.atmos.domain.model.SearchResultItem
import com.mapbox.search.SearchEngine
import com.mapbox.search.SearchEngineSettings

data class MapSearchEngineState(
    val query: String = "",
    val suggestionsSearch: List<SearchResultItem> = emptyList(),
    val isSearching: Boolean = false,
    val showSuggestions: Boolean = false,
    val searchEngine: SearchEngine = SearchEngine.createSearchEngineWithBuiltInDataProviders(
        SearchEngineSettings()
    )
)
