package com.elizav.tradingapp.ui.promo

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoListItem(promo: String, onPromoClick: (String) -> Unit, modifier: Modifier = Modifier) {
    ListItem(
        modifier = modifier.clickable(
            onClick = { onPromoClick(promo) }
        ),
        headlineText = {
            Text(text = promo)
        }
    )
}