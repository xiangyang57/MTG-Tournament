package com.example.mtgtourney.ui.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity

import com.example.mtgtourney.R

@Composable
fun CurrentMatchScreen(
    playerOneName: String = "Player 1",
    playerTwoName: String = "Player 2",
    onConfirmVictor: () -> Unit = {}
) {
    val buttonEnabled = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Player One Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.player1)),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.8f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = playerOneName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Center Divider (Victory Indicators + VS)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.versus_icon)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(2) {
                    Image(
                        painter = painterResource(id = R.drawable.victory_indicator),
                        contentDescription = "Player 1 Victory",
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.victory_icon))
                            .padding(horizontal = dimensionResource(id = R.dimen.margin_small)),
                        contentScale = ContentScale.Fit
                    )
                }

                Text(
                    text = "VS",
                    color = Color.White,
                    fontSize = with(LocalDensity.current) {
                        dimensionResource(id = R.dimen.victory_icon).toSp()
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 48.dp)
                        .background(
                            color = Color.Gray, // Replace with a proper background if needed
                            shape = CircleShape
                        )
                        .size(dimensionResource(id = R.dimen.versus_icon))
                )

                repeat(2) {
                    Image(
                        painter = painterResource(id = R.drawable.victory_indicator),
                        contentDescription = "Player 2 Victory",
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.victory_icon))
                            .padding(horizontal = dimensionResource(id = R.dimen.margin_small)),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            // Player Two Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.player2)),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.8f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = playerTwoName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Confirm Victor Button
            Button(
                onClick = onConfirmVictor,
                enabled = buttonEnabled.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.button))
                    .padding(vertical = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.dialog_confirm))
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewCurrentMatchScreen() {
    CurrentMatchScreen()
}
