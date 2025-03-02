package com.raj.compose.playground.sideeffects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme


fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

class DerivedStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.derived_state)) {
                    DerivedStateScrollExample()
                }
            }
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, DerivedStateActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DerivedStateScrollExamplePreview() {
    JetpackComposePlaygroundTheme {
        TopBarScaffold(title = stringResource(R.string.derived_state)) {
            DerivedStateScrollExample()
        }
    }
}

@Composable
fun DerivedStateScrollExample() {

    val listState = rememberLazyListState()

    /**
     * listState.firstVisibleItemIndex and listState.firstVisibleItemScrollOffset both are observable
     * Note that these properties are observable and if you use them in the composable function
     * they will be recomposed on every scroll causing potential performance issues.
     *
     * even when isEnabled is false -> false -> false then also it cause recomposition.
     * Reason: LazyListState doesn't implement distinctUntilChanged
     */

    /*val isEnabled = listState.firstVisibleItemIndex > 20
    val  firstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset*/

    /* val firstVisibleItemScrollOffset by remember {
         */
    /**
     * Now developer got the control.
     * if we change listState.firstVisibleItemScrollOffset then only it will be observed
     *//*
        mutableStateOf(listState.firstVisibleItemScrollOffset)
    }*/
    val firstVisibleItemScrollOffset by remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset }
    }
    val isEnabled by remember {
        derivedStateOf {
            /**
             * derivedStateOf internally implements distinctUntilChanged,
             * so now isEnabled will not cause recomposition when false -> false -> false
             * or true -> true -> true
             *
             * isEnabled will triggered recomposition only when true -> false or false -> true
             * It's a drastic improvement.
             */
            listState.firstVisibleItemIndex > 19
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxHeight(.85f),
        ) {
            LazyColumn(state = listState) {
                items((1..100).toList()) {
                    Text(
                        "Item $it",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color.LightGray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { }, enabled = isEnabled, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Scroll To Top")
            }

            Text(text = "firstVisibleItemScrollOffset : $firstVisibleItemScrollOffset")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DerivedStateEmailExample() {

    var email by remember { mutableStateOf("") }

    val isValidEmail by remember {
        derivedStateOf {
            return@derivedStateOf isValidEmail(email)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        TextField(
            modifier = Modifier.padding(8.dp),
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.enter_email)) }
        )

        Button(
            onClick = { },
            enabled = isValidEmail
        ) {
            Text(text = stringResource(R.string.submit))
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DerivedStateFormExample() {

    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }

    val submitEnabled by remember {
        derivedStateOf {
            return@derivedStateOf firstname.isNotEmpty() && lastname.isNotEmpty()
        }
    }

//    val submitEnabled by remember {
//        mutableStateOf(firstname.isNotEmpty() && lastname.isNotEmpty())
//    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            modifier = Modifier.padding(8.dp),
            value = firstname,
            onValueChange = { firstname = it },
            label = { Text(stringResource(R.string.enter_firstname)) }
        )

        TextField(
            modifier = Modifier.padding(8.dp),
            value = lastname,
            onValueChange = { lastname = it },
            label = { Text(stringResource(R.string.enter_lastname)) }
        )

        Button(
            onClick = { },
            enabled = submitEnabled // firstname.isNotEmpty() && lastname.isNotEmpty()
        ) {
            Text(text = stringResource(R.string.submit))
        }
    }

}