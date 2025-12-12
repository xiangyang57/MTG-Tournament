package com.example.mtgtourney.ui.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mtgtourney.R
import com.example.mtgtourney.data.deck.Deck
import com.example.mtgtourney.data.tournament.Match

@Composable
fun MatchItem(
    match: Match,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.margin_small))
    ) {
        // Player A Card
        PlayerCard(
            playerName = match.playerA.commander,
            isWinner = match.winner?.commander == match.playerA.commander,
            hasWinner = match.winner != null,
            strokeColor = colorResource(R.color.player1),
            modifier = Modifier.fillMaxWidth()
        )

        // VS Text
        Text(
            text = "vs",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.margin_small)),
            textAlign = TextAlign.Center,
            fontSize = dimensionResource(R.dimen.match_name).value.sp,
            fontWeight = FontWeight.Bold
        )

        // Player B Card
        PlayerCard(
            playerName = match.playerB.commander,
            isWinner = match.winner?.commander == match.playerB.commander,
            hasWinner = match.winner != null,
            strokeColor = colorResource(R.color.player2),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PlayerCard(
    playerName: String,
    isWinner: Boolean,
    hasWinner: Boolean,
    strokeColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.margin_med),
                vertical = dimensionResource(R.dimen.margin_small)
            )
            .height(dimensionResource(R.dimen.match_loss_icon)),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = dimensionResource(R.dimen.margin_small),
            color = strokeColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(dimensionResource(R.dimen.margin_large)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = playerName,
                textAlign = TextAlign.Center,
                fontSize = dimensionResource(R.dimen.match_name).value.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            // Show cross out icon if this player lost
            if (hasWinner && !isWinner) {
                Image(
                    painter = painterResource(id = R.drawable.cross_out),
                    contentDescription = "Lost",
                    modifier = Modifier.size(dimensionResource(R.dimen.match_loss_icon))
                )
            }
        }
    }
}

// LazyColumn implementation for the list
@Composable
fun MatchList(
    matches: List<Match>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.margin_small))
    ) {
        items(
            count = matches.size,
        ) { index ->
            Column {
                MatchItem(match = matches[index])
                HorizontalDivider(
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_med)),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}


// Preview
@Preview(showBackground = true)
@Composable
private fun MatchItemPreview() {
    MaterialTheme {
        MatchItem(
            match = Match(
                playerA = Deck(commander = "Team A", colors = listOf(com.example.mtgtourney.data.Color.BLUE)),
                playerB = Deck(commander = "Team B", colors = listOf(com.example.mtgtourney.data.Color.RED)),
                winner = Deck(commander = "Team A", colors = listOf(com.example.mtgtourney.data.Color.BLUE))
            )
        )
    }
}