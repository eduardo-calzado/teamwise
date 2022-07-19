package es.eduardocalzado.teamwise.data

class Constants {
    companion object {
        const val TAG = "TeamwiseApp"
        const val PREFS = TAG + "SharedPrefs"
        const val EDUARDOCALZADO_ES = "http://www.eduardocalzado.es"
        const val LEAGUE = 2
        const val SEASON = 2021

        const val PREFS_SEASONID = PREFS + "Season"
        const val PREFS_LEAGUEID = PREFS + "League"
        const val PREFS_COUNTRYID = PREFS + "Country"
        const val PREFS_LOCALIZATION = PREFS + "Localization"
        const val PREFS_LOCALIZATION_GPS = PREFS_LOCALIZATION + "GPS"
        const val PREFS_FIRST_INSTALL = PREFS + "FirstInstall"
    }
}