package es.eduardocalzado.teamwise.ui.detail.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import es.eduardocalzado.teamwise.di.*
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.framework.toError
import es.eduardocalzado.teamwise.ui.main.teams.MainViewModel
import es.eduardocalzado.teamwise.usecases.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    @TeamPlayerId private val teamPlayerId: Int,
    @SeasonPlayerId private val seasonPlayerId: Int,
    private val getPlayersUseCase: GetPlayersUseCase,
    private val requestPlayersByTeamUseCase: RequestPlayersByTeamUseCase,
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
            getPlayersUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { players -> _state.update { UiState(players = players) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            // TODO
            val error = requestPlayersByTeamUseCase(39, 2021)
            _state.update { it.copy(error = error) }
            _state.update { it.copy(loading = false) }
        }
    }
}