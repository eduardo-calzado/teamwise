package es.eduardocalzado.teamwise.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Query("SELECT * FROM Team")
    fun getAll(): Flow<List<Team>>

    @Query("SELECT * FROM Team WHERE id = :id")
    fun findById(id: Int): Flow<Team>

    @Query("SELECT COUNT(id) FROM Team")
    fun teamCount(): Int

    @Insert(onConflict = REPLACE)
    fun insertTeam(teams: List<Team>)

    @Update
    fun updateTeam(team: Team)
}