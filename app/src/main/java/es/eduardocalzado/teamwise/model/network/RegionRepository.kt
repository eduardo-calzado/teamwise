package es.eduardocalzado.teamwise.model.network

import android.Manifest
import android.app.Application
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import es.eduardocalzado.teamwise.model.utils.LocationDataSource
import es.eduardocalzado.teamwise.model.utils.PermissionChecker
import es.eduardocalzado.teamwise.model.utils.PlayServicesLocationDataSource
import java.util.*

class RegionRepository(application: Application) {

    companion object {
        private const val DEFAULT_REGION = "France"
    }

    private val locationDataSource: LocationDataSource = PlayServicesLocationDataSource(application)
    private val coarsePermissionChecker = PermissionChecker(
        application,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val geocoder = Geocoder(application, Locale.ENGLISH)

    suspend fun findLastRegion(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? {
        val success = coarsePermissionChecker.check()
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