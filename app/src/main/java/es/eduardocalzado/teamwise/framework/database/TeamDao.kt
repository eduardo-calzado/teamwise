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
    suspend fun insertTeams(teams: List<Team>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: Team)

    @Update
    fun updateTeam(team: Team)

    @Query("DELETE FROM Team")
    suspend fun deleteTeams()

    @Query("SELECT * FROM Team WHERE name LIKE :teamName")
    fun searchTeams(teamName: String?): Flow<List<Team>>
}