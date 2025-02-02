package com.filedownloader.viewmodelinternals.customimplementation

import androidx.lifecycle.SavedStateHandle


class ViewModelWithDependencies(
    val savedStateHandle: SavedStateHandle
) {
    init {
        println("ViewModelWithDependencies - initialize")
    }
}
//SAM
fun interface Factory<T> {
    fun create(): T
}

fun <T> getViewModel(factory: Factory<T>): T {
    return factory.create()
}

private fun main() {
    val vm1 = getViewModel {
        ViewModelWithDependencies(SavedStateHandle())
    }

    val vm2 = getViewModel(
        object : Factory<ViewModelWithDependencies> {
            override fun create(): ViewModelWithDependencies {
                return ViewModelWithDependencies(SavedStateHandle())
            }
        }
    )

    println("ViewModel 1= $vm1")
    println("ViewModel 1= $vm2")
}

