package com.example.mtgtourney.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.mtgtourney.data.Color
import com.example.mtgtourney.data.deck.Deck
import com.example.mtgtourney.R


@Composable
fun DeckColors(
    deck: Deck
) {

    Row {
        if (deck.colors.contains(Color.WHITE)) {
            ColorIcon(R.drawable.white)
        }
        if (deck.colors.contains(Color.BLUE)) {
            ColorIcon(R.drawable.blue)
        }
        if (deck.colors.contains(Color.BLACK)) {
            ColorIcon(R.drawable.black)
        }
        if (deck.colors.contains(Color.RED)) {
            ColorIcon(R.drawable.red)
        }
        if (deck.colors.contains(Color.GREEN)) {
            ColorIcon(R.drawable.green)
        }
        if (deck.colors.contains(Color.COLORLESS)) {
            ColorIcon(R.drawable.colorless)
        }
    }
}

@Composable
private fun ColorIcon(
    @DrawableRes icon: Int
) {
    Image(
        painter = painterResource(icon),
        contentDescription = "White",
        modifier = Modifier.size(dimensionResource(R.dimen.color_icon))
            .padding(end = dimensionResource(R.dimen.margin_tiny))
        )
}