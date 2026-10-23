package com.example.step4.data

import android.content.Context
import android.content.SharedPreferences

/** Thin SharedPreferences wrapper for the user's saved preferences. */
internal class SettingsStore(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /** When on, the Home screen shows a gentle suggestion based on the time of day. */
    var remindersEnabled: Boolean
        get() = prefs.getBoolean(KEY_REMINDERS, true)
        set(value) {
            prefs.edit().putBoolean(KEY_REMINDERS, value).apply()
        }

    /** When off, breathing, expand/collapse and screen transitions are skipped. */
    var animationsEnabled: Boolean
        get() = prefs.getBoolean(KEY_ANIMATIONS, true)
        set(value) {
            prefs.edit().putBoolean(KEY_ANIMATIONS, value).apply()
        }

    /** Comma separated dosha names from the last completed reflection, or null. */
    var lastReflection: String?
        get() = prefs.getString(KEY_LAST_REFLECTION, null)
        set(value) {
            val editor = prefs.edit()
            if (value == null) editor.remove(KEY_LAST_REFLECTION) else editor.putString(KEY_LAST_REFLECTION, value)
            editor.apply()
        }

    private companion object {
        const val PREFS_NAME = "tridosha_settings"
        const val KEY_REMINDERS = "reminders_enabled"
        const val KEY_ANIMATIONS = "animations_enabled"
        const val KEY_LAST_REFLECTION = "last_reflection"
    }
}
