package es.eduardocalzado.teamwise.framework.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM Player")
    fun getAll(): Flow<List<Player>>

    @Query("SELECT * FROM Player WHERE id = :id")
    fun findById(id: Int): Flow<Player>

    @Query("SELECT * FROM Player WHERE team = :team")
    fun findByTeam(team: Int): Flow<List<Player>>

    @Query("SELECT * FROM Player WHERE team = :team")
    suspend fun filterByTeam(team: Int): List<Player>

    @Query("SELECT COUNT(id) FROM Player")
    suspend fun playersCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(players: List<Player>)

    @Update
    fun updatePlayer(team: Player)

    @Query("DELETE FROM Player")
    suspend fun deletePlayers()

    @Query("SELECT * FROM Player WHERE name LIKE :playerName OR firstName LIKE :playerName OR lastName LIKE :playerName")
    fun searchPlayers(playerName: String?): Flow<List<Player>>
}