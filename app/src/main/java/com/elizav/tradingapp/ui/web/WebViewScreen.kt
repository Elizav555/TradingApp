package com.elizav.tradingapp.ui.web

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(navController: NavController) {
    val viewModel: WebViewViewModel = hiltViewModel()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.urlState.collect { url ->
            url?.let {
                snackbarHostState.showSnackbar(
                    message = it,
                    withDismissAction = true
                )
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        MyWebView("https://elizav555.github.io/", Modifier.padding(padding))
    }

//    val urlState by viewModel.urlState.collectAsState()
//    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
//        MyWebView(urlState?:"https://elizav555.github.io/", Modifier.padding(padding))
//    }
}