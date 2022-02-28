package es.eduardocalzado.teamwise.network

import android.Manifest
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import es.eduardocalzado.teamwise.utils.LocationDataSource
import es.eduardocalzado.teamwise.utils.PermissionChecker
import es.eduardocalzado.teamwise.utils.PlayServicesLocationDataSource
import java.util.*

class RegionRepository(activity: AppCompatActivity) {

    companion object {
        private const val DEFAULT_REGION = "France"
    }

    private val locationDataSource: LocationDataSource = PlayServicesLocationDataSource(activity)
    private val coarsePermissionChecker = PermissionChecker(
        activity,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val geocoder = Geocoder(activity, Locale.ENGLISH)

    suspend fun findLastRegion(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? {
        val success = coarsePermissionChecker.request()
        return if (success) locationDataSource.findLastLocation() else null
    }

    private fun Location?.toRegion(): String {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        Log.d("TAG", ""+addresses?.firstOrNull()?.countryName)
        return addresses?.firstOrNull()?.countryName ?: DEFAULT_REGION
    }
}