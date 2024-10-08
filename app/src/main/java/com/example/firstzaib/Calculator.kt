package com.example.firstzaib

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Calculator(modifier: Modifier = Modifier, viewModel: CalculatorViewModel) {
    val equationText =
        viewModel.equationText.observeAsState()
    val resultText =
        viewModel.resultText.observeAsState()
    val buttonList = listOf(
        "C", "(", ")", "/",
        "7", "8", "9", "*",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "AC", "0", ".", "=",
    )
    Box(modifier = modifier) {
        // Box -> Column -> (2 Texts) & 1 (Lazy vertical Grid)
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Developed by Aurangzaib Baloch", fontWeight = FontWeight.ExtraLight, fontSize = 15.sp)
        }
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(130.dp))

            Text(
                text = equationText.value ?: "", style = TextStyle(
                    fontSize = 30.sp,
                    textAlign = TextAlign.End
                ),
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = resultText.value ?: "", style = TextStyle(
                    fontSize = 60.sp,
                    textAlign = TextAlign.End
                ),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                items(buttonList) { it ->
                    CalculatorButton(btn = it, onCLick = {
                        viewModel.onButtonClick(it)
                    })
                }
            }

        }
    }
}

@Composable
fun CalculatorButton(btn: String, onCLick: () -> Unit) {
    Box(modifier = Modifier.padding(5.dp)) {
        FloatingActionButton(
            onClick = {
                onCLick()
            },
            shape = CircleShape,
            containerColor = getColor(btn),
            contentColor = Color.White,

            modifier = Modifier.size(80.dp)
        ) {
            Text(text = btn, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

fun getColor(btn: String): Color {
    if (btn == "C" || btn == "AC")
        return Color(0xFFF44336)
    if (btn == "(" || btn == ")")
        return Color.Gray

    if (btn == "/" || btn == "*" || btn == "+" || btn == "-" || btn == "=")
        return Color(0xFFFF9800)

    return Color(0xFF00C8C9)
}