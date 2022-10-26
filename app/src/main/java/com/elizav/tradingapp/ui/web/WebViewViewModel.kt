package com.elizav.tradingapp.ui.web

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.elizav.tradingapp.domain.utils.EncodedLink
import com.elizav.tradingapp.ui.navigation.Route
import com.elizav.tradingapp.ui.navigation.navigator.AppNavigator
import com.elizav.tradingapp.ui.web.state.WebViewEvent
import com.elizav.tradingapp.ui.web.state.WebViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState: MutableStateFlow<WebViewState> =
        MutableStateFlow(WebViewState())
    val uiState: StateFlow<WebViewState> = _uiState

    init {
        val url: String? = savedStateHandle[Route.WebViewRoute.URL_KEY]
        _uiState.update { it.copy(url = url?.let { url -> EncodedLink.decodeLink(url) }) }
    }

    fun onEvent(event: WebViewEvent) {
        when (event) {
            WebViewEvent.GoBackEvent -> {
                appNavigator.tryNavigateBack()
            }
            is WebViewEvent.HandleLoading -> {
                _uiState.update { it.copy(isLoading = event.isLoading) }
            }
        }
    }
}
