package com.codelabs.lemonadecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.lemonadecompose.ui.theme.LemonadeComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeApp()
        }
    }
}

@Composable
fun LemonImageAndLegend(modifier: Modifier = Modifier) {

    var lemonSqueezCount = 0
    var lemonSqueezNeeded = 0
    var legend = "";
    var imageResource = R.drawable.lemon_tree
    var imageDescription = ""

    var currentState: Int by remember { mutableStateOf(1) }

    //evalua estado cuando se le da click
    when {
        currentState == 2 -> {
            imageResource = R.drawable.lemon_squeeze
            imageDescription = stringResource(R.string.lemon_to_squeeze)
            legend = stringResource(R.string.state_2_description)
        }
        currentState == 3 -> {
            imageResource = R.drawable.lemon_drink
            imageDescription = stringResource(R.string.lemonade_to_drink)
            legend = stringResource(R.string.state_3_description)
        }
        currentState == 4 -> {
            imageResource = R.drawable.lemon_restart
            imageDescription = stringResource(R.string.empty_glass)
            legend = stringResource(R.string.state_4_description)
        }
        else -> {
            imageResource = R.drawable.lemon_tree
            imageDescription = stringResource(R.string.lemon_tree)
            legend = stringResource(R.string.state_1_description)
        }
    }

    Column (modifier = modifier) {
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonColors(
                containerColor = Color(android.graphics.Color.parseColor("#99c2a4")),
                contentColor = Color.Unspecified,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.Gray
            ),
            shape = RoundedCornerShape(16.dp),
            onClick = {
            if(currentState == 4){
                currentState = 1
            }else if (currentState == 2){
                //calcula el valor random de la cantidaa de veces que se debe exprimir el limon
                if(lemonSqueezCount == 0){
                    lemonSqueezNeeded = (2..4).random()
                }
                //como lemonSqueezCount no esta delegado, su modificacion no implica
                //una recomposición por lo que los valores de las variable se mantienen
                lemonSqueezCount++
                if(lemonSqueezCount == lemonSqueezNeeded) {
                    //aqui si sucede una recomposicion porque se modifica currentState
                    currentState = 3
                    lemonSqueezCount = 0
                }
            }else{
                currentState++
            }
        }) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = imageDescription
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = legend,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier){
    Column (modifier = modifier, verticalArrangement = Arrangement.Center) {
        Text(
            modifier = Modifier.padding(top = 30.dp),
            text = "Lemonade App",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    TopBar(
        Modifier
            .background(Color.Yellow)
            .fillMaxWidth()
            .height(80.dp))
    LemonImageAndLegend(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}

