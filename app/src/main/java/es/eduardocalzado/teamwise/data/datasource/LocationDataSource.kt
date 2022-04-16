package es.eduardocalzado.teamwise.data.datasource

import android.location.Location

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}
