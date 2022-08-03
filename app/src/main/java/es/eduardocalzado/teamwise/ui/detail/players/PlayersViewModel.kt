package es.eduardocalzado.teamwise.ui.detail.players

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.di.LeagueId
import es.eduardocalzado.teamwise.di.SeasonId
import es.eduardocalzado.teamwise.di.TeamId
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.framework.toError
import es.eduardocalzado.teamwise.ui.main.teams.MainViewModel
import es.eduardocalzado.teamwise.usecases.FindPlayersByTeamUseCase
import es.eduardocalzado.teamwise.usecases.GetPlayersUseCase
import es.eduardocalzado.teamwise.usecases.RequestPlayersByTeamUseCase
import es.eduardocalzado.teamwise.usecases.SearchPlayersUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    @TeamId val teamId: Int,
    @LeagueId val leagueId: Int,
    @SeasonId val seasonId: Int,
    private val requestPlayersByTeamUseCase: RequestPlayersByTeamUseCase,
    private val findPlayersByTeamUseCase: FindPlayersByTeamUseCase,
    private val searchPlayersUseCase: SearchPlayersUseCase,
): ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val players: List<Player>? = null,
        val error: Error? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findPlayersByTeamUseCase(teamId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { players -> _state.update { UiState(players = players) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val error = requestPlayersByTeamUseCase(teamId, seasonId)
            _state.update { it.copy(error = error) }
            _state.update { it.copy(loading = false) }
        }
    }

    fun searchPlayers(query: String) {
        viewModelScope.launch {
            searchPlayersUseCase(query, teamId)
                .collect { players -> _state.update { UiState(players = players) } }
        }
    }
}