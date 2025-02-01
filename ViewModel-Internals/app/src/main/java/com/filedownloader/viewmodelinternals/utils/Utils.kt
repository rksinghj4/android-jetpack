package com.filedownloader.viewmodelinternals.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings

object Utils {
    /**
     * import android.provider.Settings
     *
     */
    fun isDoNotKeepActivityOptionON(context: Context): Int {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.System.getInt(
                context.contentResolver,
                Settings.System.ALWAYS_FINISH_ACTIVITIES,
                0
            )
        } else {
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.ALWAYS_FINISH_ACTIVITIES,
                0
            )
        }
    }

    fun showDeveloperOptionScreen(context: Context) {
        Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        }.also {
            context.startActivity(it)
        }
    }
}