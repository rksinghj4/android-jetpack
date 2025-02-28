package com.raj.compose.playground.compositionlocal

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalFontLoader
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.intl.Locale

/**
 * All are global/Top level immutable variable Part of androidx.compose.ui.platform package
 */
@Composable
fun AndroidCompositionLocalsAndroidKt() {
    val localContext = LocalContext.current
    val localConfiguration = LocalConfiguration.current
    val localLifecycleOwner = LocalLifecycleOwner.current
    val localSavedStateRegistryOwner = LocalSavedStateRegistryOwner.current
    val localView = LocalView.current
}

/**
 * All are global/Top level immutable variable Part of androidx.compose.ui.platform package
 */
@Composable
fun CompositionLocalsKt() {
    val localAccessibilityManager = LocalAccessibilityManager.current

    @OptIn(ExperimentalComposeUiApi::class)
    val localAutofill = LocalAutofill.current

    @OptIn(ExperimentalComposeUiApi::class)
    val localAutofillTree = LocalAutofillTree.current

    val localClipboardManager = LocalClipboardManager.current
    val localDensity = LocalDensity.current
    val localFocusManager = LocalFocusManager.current
    val localFontLoader = LocalFontLoader.current
    val localFontFamilyResolver = LocalFontFamilyResolver.current
    val localHapticFeedback = LocalHapticFeedback.current
    val localInputModeManager = LocalInputModeManager.current
    val localLayoutDirection = LocalLayoutDirection.current
    val localTextInputService = LocalTextInputService.current
    val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
    val localTextToolbar = LocalTextToolbar.current
    val localUriHandler = LocalUriHandler.current
    val localViewConfiguration = LocalViewConfiguration.current
    val localWindowInfo = LocalWindowInfo.current
}

/**
 * All are part of androidx.compose.ui.text.intl import
 */
@Composable
fun LocalKt() {
    val localObject = Locale.current//Returns a Locale object which represents current locale
    val language = Locale.current.language
    val region = Locale.current.region
    val script = Locale.current.script
}


