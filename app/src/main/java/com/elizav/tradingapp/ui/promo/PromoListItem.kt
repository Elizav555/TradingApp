package com.elizav.tradingapp.ui.promo

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elizav.tradingapp.domain.model.Promo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoListItem(promo: Promo, onPromoClick: (String) -> Unit, modifier: Modifier = Modifier) {
    ListItem(
        modifier = modifier.clickable(
            onClick = { onPromoClick(promo.link) }
        ),
        headlineText = {
            Text(text = promo.name)
        },
        supportingText = {
            Text(text = promo.text)
        },
    )
}