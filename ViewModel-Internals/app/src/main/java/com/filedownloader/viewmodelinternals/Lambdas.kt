package com.filedownloader.viewmodelinternals

// Lambdas Expressions are essentially anonymous functions that we can treat as values
// we can, for example, pass them as arguments to functions, return them, or do any
// other thing we could do with a normal object.

val square: (Int) -> Int = { value -> value * value }

val nine = square(3)

val doNothing: (Int) -> Int = { value -> value }

val add: (Int, Int) -> Int = { a, b -> a + b }

val result = add(2, 3)

// A higher-order function is a function that takes functions as parameters or returns a function.

fun passMeFunction(abc: () -> Unit) {
    // I can take function
    // do something here
    // execute the function
    abc()
}

fun add(a: Int, b: Int): Int {
    return a + b
}

fun returnMeAddFunction(): ((Int, Int) -> Int) {
    // can do something and return function as well
    // returning function
    return ::add
}

class Lambdas {

    fun lambdasWay() {
        makeNetworkCallLambdas(onSuccess = {
            // show in UI
        }, onError = {
            // handle error
        })
    }

    private fun makeNetworkCallLambdas(
        onSuccess: (value: String) -> Unit,
        onError: (value: String) -> Unit
    ) {
        // some code to make network call and fetch data
        val data = "some data from network"

        onSuccess(data)

        onError("some error message")
    }

    fun listenerWay() {
        makeNetworkCall(object : Listener {

            override fun onSuccess(value: String) {
                // show in UI
            }

            override fun onError(value: String) {
                // handle error
            }

        })
    }

    private fun makeNetworkCall(listener: Listener) {
        // some code to make network call and fetch data
        val data = "some data from network"

        listener.onSuccess(data)

        listener.onError("some error message")
    }

}

interface Listener {

    fun onSuccess(value: String)

    fun onError(value: String)

}