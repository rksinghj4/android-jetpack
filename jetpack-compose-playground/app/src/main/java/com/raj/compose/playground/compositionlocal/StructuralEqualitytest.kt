package com.raj.compose.playground.compositionlocal

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

private const val TAG = "LOCAL_COLORS"

data class MyColors(
    var primary: Color = Color.Blue,//Any change we will observe in primary only
    val secondary: Color = Color.Gray,
    val secondaryLight: Color = Color.LightGray
)

val myColor = MyColors()
val myColor2 = myColor.copy()
val myColor3 = myColor.copy(primary = Color.Yellow)

/**
 * Create a CompositionLocal key that can be provided using CompositionLocalProvider.
 * Changing the value provided during recomposition will invalidate the content of
 * CompositionLocalProvider that read the value using CompositionLocal.current.
 */
val LocalColors1 =
    compositionLocalOf {//default policy SnapshotMutationPolicy<T> = structuralEqualityPolicy()
        myColor
    }

/*
What is the role of CompositionLocal in Jetpack Compose?

CompositionLocal in Jetpack Compose is used to provide
values that can be accessed by the Composable functions
in the composition tree. It allows you to pass down values,
such as themes, fonts, or other contextual information,
without explicitly passing them through function parameters.
 */
val LocalColors2 = LocalColors1
/*compositionLocalOf {//default policy SnapshotMutationPolicy<T> = structuralEqualityPolicy()
    MyColors()
}*/

val LocalColors3 =
    compositionLocalOf {//default policy SnapshotMutationPolicy<T> = structuralEqualityPolicy()
        myColor.copy(primary = Color.Yellow)//New color
    }

val LocalColors4 =
    compositionLocalOf {//default policy SnapshotMutationPolicy<T> = structuralEqualityPolicy()
        myColor.copy(primary = Color.Blue)//Re assign to old color
    }


/*val LocalColors4 =
    compositionLocalOf {//default policy SnapshotMutationPolicy<T> = structuralEqualityPolicy()
        myColor2
    }

val LocalColors5 =
    compositionLocalOf {//default policy SnapshotMutationPolicy<T> = structuralEqualityPolicy()
        myColor2.primary = Color.Blue
    }*/

@Composable
internal fun StructuralEqualityPolicyTest() {
    Log.d(
        TAG,
        "myColor.hashCode() = ${myColor.hashCode()}"
    )
    Log.d(
        TAG,
        "myColor2.hashCode() = ${myColor2.hashCode()}"
    )
    Log.d(
        TAG,
        "myColor3.hashCode() = ${myColor3.hashCode()}"
    )
    Log.d(TAG, "myColor ***********  myColor2  ********** myColor3")
    Log.d(
        TAG,
        "LocalColors1.current.hashCode() = ${LocalColors1.current.hashCode()}"
    )
    Log.d(
        TAG,
        "LocalColors3.current.hashCode() = ${LocalColors3.current.hashCode()}"
    )
    Log.d(
        TAG,
        "LocalColors1.current = ${LocalColors1.current}"
    )
    Log.d(
        TAG,
        "LocalColors3.current = ${LocalColors3.current}"
    )
    //primary = Color.Blue , primary = Color.Yellow
    Log.d(
        TAG,
        "LocalColors1.current == LocalColors3.current = ${LocalColors1.current == LocalColors3.current}"
    )
    Log.d(TAG, "1. primary = Color.Blue *********** 1 End 3 **********3. primary = Color.Yellow")

    Log.d(
        TAG,
        "LocalColors4.current.hashCode() = ${LocalColors4.current.hashCode()}"
    )

    Log.d(
        TAG,
        "LocalColors3.current = ${LocalColors3.current}"
    )
    Log.d(
        TAG,
        "LocalColors4.current = ${LocalColors4.current}"
    )
    //primary = Color.Yellow , primary = Color.Blue

    Log.d(
        TAG,
        "LocalColors3.current == LocalColors4.current = ${LocalColors3.current == LocalColors4.current}"
    )
    Log.d(TAG, "3. primary = Color.Yellow ********** 3 End 4 ***********4. primary = Color.Blue")

    Log.d(
        TAG,
        "LocalColors1.current === LocalColors2.current = ${LocalColors1.current === LocalColors2.current}"
    )

    Log.d(
        TAG,
        "LocalColors1.current == LocalColors2.current = ${LocalColors1.current == LocalColors2.current}"
    )

    //primary = Color.Blue , primary = Color.Blue
    Log.d(
        TAG,
        "LocalColors1.current == LocalColors4.current = ${LocalColors1.current == LocalColors4.current}"
    )
    Log.d(TAG, "1. primary = Color.Blue ********* 1 End 4 ************ 4. primary = Color.Blue")

}
