package com.filedownloader.viewmodelinternals.customimplementation

import kotlin.reflect.KClass

/**
 * If ViewModel doesn't have any dependencies then we create VM without any factory
 * using reflection
 */


class NoDependenciesViewModel {
    init {
        println("NoDependenciesViewModel - initialized")
    }
}

fun <T> getViewModel(modelClass: Class<T>): T {
    return modelClass.getConstructor().newInstance()
}

private fun main() {
    val vm = getViewModel(NoDependenciesViewModel::class.java)
    println("NoDependenciesViewModel = $vm")
}


