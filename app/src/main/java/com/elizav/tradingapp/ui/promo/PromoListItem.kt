package com.elizav.tradingapp.ui.promo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elizav.tradingapp.R
import com.elizav.tradingapp.domain.model.Promo

@Composable
fun PromoListItem(promo: Promo, onPromoClick: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text(
            text = promo.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(promo.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_baseline_money_48),
                contentDescription = stringResource(R.string.promo_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Text(text = promo.text)
        }
        Text(
            modifier = modifier.padding(horizontal = 8.dp),
            text = "Die Data: ${promo.die_date}. Is euro available: ${if (promo.euro_available) "Yes" else "No"}"
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            onClick = { onPromoClick(promo.link) },
            colors = ButtonDefaults.buttonColors(
                containerColor =
                promo.button_color?.let { androidx.compose.ui.graphics.Color(it) }
                    ?: MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = promo.button_text)
        }
    }
}