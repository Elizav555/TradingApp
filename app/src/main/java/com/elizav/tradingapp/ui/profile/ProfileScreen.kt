package com.elizav.tradingapp.ui.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.elizav.tradingapp.R

@Composable
fun ProfileScreen(navController: NavController) {
    Text(text = stringResource(id = R.string.profile))
}