 package com.yusufyildiz.basicscomposecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yusufyildiz.basicscomposecodelab.ui.theme.BasicsComposeCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsComposeCodelabTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    // TODO: This state should be hoisted
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if(shouldShowOnboarding)
    {
        OnboardingScreen(onContinueClicked = {shouldShowOnboarding = false})
    }
    else
    {
        Greetings()
    }
}

@Composable
fun Greetings(names: List<String> = List(1000){"$it"})
{
    Surface(
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            LazyColumn{
                item { Text("Head") }
                items(names){ name->
                    Greeting(name)
                }
            }
        }

    }
}


@Composable
fun Greeting(name: String) {


    var expanded by remember {mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        targetValue =  if (expanded) 48.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 2000
        )
    )
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = name, style = MaterialTheme.typography.h2.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
                if(expanded){
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4)
                    )
                }
            }

            OutlinedButton(onClick = {expanded = !expanded}) {
                Text(if(expanded) stringResource(R.string.show_less) else stringResource(R.string.show_more))
            }
        }

    }
}

 @Composable
 fun OnboardingScreen(
     onContinueClicked: () -> Unit,
     modifier: Modifier = Modifier
 ) {

     Column(
         modifier = modifier.fillMaxSize(),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally
     ) {
         Text("Welcome to the Basics Codelab!")
         Button(
             modifier = Modifier.padding(vertical = 24.dp),
             onClick = onContinueClicked
         ) {
             Text("Continue")
         }
     }
 }

 @Preview(showBackground = true, widthDp = 320, heightDp = 320)
 @Composable
 fun OnboardingPreview() {
     BasicsComposeCodelabTheme {
         OnboardingScreen(onContinueClicked = {})
     }
 }



@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsComposeCodelabTheme {
        MyApp()
    }
}