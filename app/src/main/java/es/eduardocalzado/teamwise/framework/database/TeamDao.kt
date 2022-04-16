package es.eduardocalzado.teamwise.framework.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Query("SELECT * FROM Team")
    fun getAll(): Flow<List<Team>>

    @Query("SELECT * FROM Team WHERE id = :id")
    fun findById(id: Int): Flow<Team>

    @Query("SELECT COUNT(id) FROM Team")
    suspend fun teamCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(teams: List<Team>)

    @Update
    fun updateTeam(team: Team)
}