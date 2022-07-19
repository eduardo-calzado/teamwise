package es.eduardocalzado.teamwise

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Region
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.data.RegionRepository

class Prefs (context: Context)
{
    private val preferences: SharedPreferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)

    var countryId: String?
        get() = preferences.getString(Constants.PREFS_COUNTRYID, "")
        set(value) = preferences.edit().putString(Constants.PREFS_COUNTRYID, value).apply()

    var leagueId: Int
        get() = preferences.getInt(Constants.PREFS_LEAGUEID, -1)
        set(value) = preferences.edit().putInt(Constants.PREFS_LEAGUEID, value).apply()

    var seasonId: Int
        get() = preferences.getInt(Constants.PREFS_SEASONID, -1)
        set(value) = preferences.edit().putInt(Constants.PREFS_SEASONID, value).apply()

    var localization: String?
        get() = preferences.getString(Constants.PREFS_LOCALIZATION, "")
        set(value) = preferences.edit().putString(Constants.PREFS_LOCALIZATION, value).apply()

    var firstInstall : Boolean
        get() = preferences.getBoolean(Constants.PREFS_FIRST_INSTALL, true)
        set(value) = preferences.edit().putBoolean(Constants.PREFS_FIRST_INSTALL, value).apply()
}