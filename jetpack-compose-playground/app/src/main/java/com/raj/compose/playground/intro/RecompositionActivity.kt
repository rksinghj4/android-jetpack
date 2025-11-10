package com.raj.compose.playground.intro

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.raj.compose.playground.R
import com.raj.compose.playground.ui.common.TopBarScaffold
import com.raj.compose.playground.ui.theme.JetpackComposePlaygroundTheme

class RecompositionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        intent.getStringExtra("key1")?.let {
//            Log.d(TAG, "onCreate: Got from intent - $it")
//        }
//        intent.getBundleExtra("key1")?.let {
//            val value1 = it.getString("key1")
//            val value2 = it.getInt("key2")
//            Log.d(TAG, "onCreate: Got from intent bundle - $value1 and $value2")
//        }
        setContent {
            JetpackComposePlaygroundTheme {
                TopBarScaffold(title = stringResource(R.string.recomposition)) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //Recomposition()
                        EqualityCheckForDataClass()
                    }
                }
            }
        }
    }

    /**
     * In older days state container function was considered user/reader of that state.
     * For any updates(must be distinct from just previous one) in the state causes
     * recomposition of all the readers/users including container of that state
     *
     * But in latest compose versions container function is not considered the user/reader of the
     * compose state so not recomposed on change.
     */
    @Composable
    fun Recomposition() {
        Log.d(TAG, "Recomposition - Start")
        var count by remember {
            Log.d(TAG, "Recomposition - remember")
            mutableIntStateOf(0)
        }
        Button(
            onClick = { count++ },
            content = {
                Text("Count: $count")
                Log.d(TAG, "Recomposition - Content End")
            }
        )
    }

    //val or var doesn't matter for equality policy in mutableStateOf()
    data class User(var name: String, var age: Int)

    //Compose uses structural
    @Composable
    fun EqualityCheckForDataClass() {

        var userState by remember {
            mutableStateOf(User(name = "Raj", age = 20))
        }
        val newUserObject = User(name = "Raj", age = 21)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(.7f)
            ) {
                Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = userState.name)
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = userState.age.toString()
                )
                Log.d(TAG, "EqualityCheckForDataClass - Card")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                userState = newUserObject
                //userState = User(name = "Raj", age = 20)
            }) {
                Text(text = "Update")
            }
        }
    }

    companion object {
        const val TAG = "RecompositionDemo"
        fun show(fromActivity: Activity) {
            Intent(fromActivity, RecompositionActivity::class.java).also {
                //val bundle = Bundle()
                //bundle.putString("key1", "value1")
                //bundle.putInt("key2", 123)
                //You can put any serializable or parcelable object also
                //bundle.putParcelable("key3", ParcelableObject())
                //bundle.putSerializable("key4", SerializableObject())
                //it.putExtras(bundle)
                fromActivity.startActivity(it)
            }
        }
    }
}

