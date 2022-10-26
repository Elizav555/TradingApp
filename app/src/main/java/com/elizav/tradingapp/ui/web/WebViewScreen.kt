package com.elizav.tradingapp.ui.web

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.elizav.tradingapp.R
import com.elizav.tradingapp.ui.web.state.WebViewEvent
import com.elizav.tradingapp.ui.web.state.WebViewState
import com.elizav.tradingapp.ui.widgets.Loading
import com.elizav.tradingapp.ui.widgets.MyWebView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun WebViewScreen(navController: NavController) {
    val viewModel: WebViewViewModel = hiltViewModel()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val uiState by viewModel.uiState.collectAsState()
    val onBackClick = { viewModel.onEvent(WebViewEvent.GoBackEvent) }
    val handleLoading =
        { isLoading: Boolean -> viewModel.onEvent(WebViewEvent.HandleLoading(isLoading)) }
    val coroutineScope = rememberCoroutineScope()
    WebViewContent(snackbarHostState, uiState, onBackClick, handleLoading, coroutineScope)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WebViewContent(
    snackbarHostState: SnackbarHostState,
    uiState: WebViewState,
    onBackClick: () -> Unit,
    handleLoading: (Boolean) -> Unit,
    coroutineScope: CoroutineScope
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.promo_list)
                    )
                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) })
    { padding ->
        uiState.url?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = it,
                    withDismissAction = true
                )
            }
        }
        if (uiState.isLoading) {
            Loading()
        } else {
            MyWebView("https://elizav555.github.io/", Modifier.padding(padding), handleLoading)
        }
    }

//Links from api aren't correct so i mocked website
//    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
//    if (uiState.isLoading) {
//        Loading()
//    } else MyWebView(urlState?:"https://elizav555.github.io/", Modifier.padding(padding))
//    }
}