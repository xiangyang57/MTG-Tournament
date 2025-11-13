package com.example.mtgtourney.ui.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mtgtourney.R
import com.example.mtgtourney.data.Match

@Composable
fun CurrentMatchScreen(
    viewModel: CurrentMatchViewModel
) {
    // Collect StateFlow as State - UI automatically updates when StateFlow emits
    val match by viewModel.match.collectAsState()
    val playerOneWins by viewModel.player1VictoryCount.collectAsState()
    val playerTwoWins by viewModel.player2VictoryCount.collectAsState()
    val selectedPlayer by viewModel.selectedPlayer.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Show match screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Player One Card
            match?.playerA?.let { player ->
                PlayerCard(
                    playerName = player.commander,
                    isSelected = selectedPlayer == player,
                    backgroundColor = colorResource(id = R.color.player1),
                    onCardClick = { viewModel.selectPlayer(player) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 8.dp)
                )
            }

            // Center Divider (Victory Indicators + VS)
            VictoryIndicators(
                playerOneWins = playerOneWins,
                playerTwoWins = playerTwoWins
            )

            // Player Two Card
            match?.playerB?.let { player ->
                PlayerCard(
                    playerName = player.commander,
                    isSelected = selectedPlayer == player,
                    backgroundColor = colorResource(id = R.color.player2),
                    onCardClick = { viewModel.selectPlayer(player) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 8.dp)
                )
            }

            // Confirm Victor Button
            Button(
                onClick = { viewModel.confirmVictory() },
                enabled = selectedPlayer != null,
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = dimensionResource(id = R.dimen.button))
                    .padding(vertical = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.dialog_confirm).uppercase(),
                style = MaterialTheme.typography.labelLarge)
            }
        }

        match?.winner?.let {
            ConfirmVictoryDialog(match!!, viewModel)
        }
    }
}

@Composable
fun PlayerCard(
    playerName: String,
    isSelected: Boolean,
    backgroundColor: Color,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onCardClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                backgroundColor
            else
                backgroundColor.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(
            if (isSelected) 8.dp else 4.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = playerName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun VictoryIndicators(
    playerOneWins: Int,
    playerTwoWins: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.versus_icon)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Player 1 Victory Indicators
        repeat(2) { index ->
            Image(
                painter = painterResource(
                    id = if (2 - index > playerOneWins)
                        R.drawable.victory_indicator
                    else
                        R.drawable.player_one_victory_indicator),
                contentDescription = "Player 1 Victory $index",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.victory_icon))
                    .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
                    .alpha(if (2 - index <= playerOneWins) 1f else 0.3f),
                contentScale = ContentScale.Fit
            )
        }

        Box(
            modifier = Modifier
                .wrapContentSize()
        ) {
            Image(
                painter = painterResource(R.drawable.victory_indicator),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "VS",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }

        // Player 2 Victory Indicators
        repeat(2) { index ->
            Image(
                painter = painterResource(
                    id = if (index >= playerTwoWins)
                        R.drawable.victory_indicator
                    else
                        R.drawable.player_two_victory_indicator),
                contentDescription = "Player 2 Victory $index",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.victory_icon))
                    .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
                    .alpha(if (index < playerTwoWins) 1f else 0.3f),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun ConfirmVictoryDialog(match: Match, viewModel: CurrentMatchViewModel) {
    val context = LocalContext.current
    val winner = match.winner!!
    val loser = if (winner == match.playerA) match.playerB!! else match.playerA
    val message = "${winner.commander} has railed ${loser.commander}"
    AlertDialog(
        onDismissRequest = {
            viewModel.updateTournament(context, match)
            viewModel.loadNextMatch(context)
        },
        title = {
            Text(
                text = "🏆 Match Complete!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                viewModel.updateTournament(context, match)
                viewModel.loadNextMatch(context)
            }) {
                Text("Continue")
            }
        }
    )
}