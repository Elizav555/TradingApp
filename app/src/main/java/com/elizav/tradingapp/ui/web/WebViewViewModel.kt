package com.elizav.tradingapp.ui.web

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.elizav.tradingapp.domain.model.EncodedLink
import com.elizav.tradingapp.ui.navigation.Route
import com.elizav.tradingapp.ui.navigation.navigator.AppNavigator
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
    private val _urlState: MutableStateFlow<String?> =
        MutableStateFlow(null)
    val urlState: StateFlow<String?> = _urlState

    init {
        val url: String? = savedStateHandle[Route.WebViewRoute.URL_KEY]
        _urlState.update { url?.let { EncodedLink.decodeLink(it) } }
    }
}
