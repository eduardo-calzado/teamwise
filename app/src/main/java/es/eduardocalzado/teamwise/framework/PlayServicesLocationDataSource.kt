package es.eduardocalzado.teamwise.framework

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import android.util.Log
import es.eduardocalzado.teamwise.data.datasource.LocationDataSource
import com.google.android.gms.location.LocationServices
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.data.RegionRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume

class PlayServicesLocationDataSource @Inject constructor (application: Application) : LocationDataSource {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private val geocoder = Geocoder(application)

    @SuppressLint("MissingPermission")
    override suspend fun findLastRegion(): String? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result.toRegion())
                }
        }

    private fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return getCountryName(addresses?.firstOrNull()?.countryCode)
    }

    /**
     * getCountryName. Depending where we are executing the app and the mobile locale configuration,
     * the countryName from geocoder API can be received in the default language of the app. In this
     * way, we are going to mapping from country code, the country name in english that works with
     * the API we use.
     * @param: countryCode
     * @return: String - countryName
     */
    private fun getCountryName(countryCode: String?): String {
        if (countryCode.isNullOrBlank()) {
            Log.w("[OWN]: "+this.javaClass.simpleName, "Country code is null. Probably google places is not working as expect. The default value will be: $RegionRepository.DEFAULT_REGION")
            return RegionRepository.DEFAULT_REGION
        }
        val loc = Locale("", countryCode ?: "EN")
        val result = loc.getDisplayCountry(Locale("EN"))
        Log.d("[OWN]: "+this.javaClass.simpleName, "Country code is: $result")
        return result
    }
}
