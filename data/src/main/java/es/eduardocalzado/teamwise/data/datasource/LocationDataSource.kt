package es.eduardocalzado.teamwise.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}
