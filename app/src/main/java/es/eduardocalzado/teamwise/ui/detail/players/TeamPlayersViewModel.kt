package es.eduardocalzado.teamwise.ui.detail.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import es.eduardocalzado.teamwise.di.*
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.domain.TeamPlayer
import es.eduardocalzado.teamwise.framework.toError
import es.eduardocalzado.teamwise.usecases.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamPlayersViewModel @Inject constructor(
    @TeamPlayerId private val teamPlayerId: Int,
    @SeasonPlayerId private val seasonPlayerId: Int,
    private val requestTeamPlayersUseCase: RequestTeamPlayersUseCase,
): ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val players: List<TeamPlayer>? = null,
        val error: Error? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    fun onUiReady() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            // TODO
            when (val request = requestTeamPlayersUseCase(39, 2021)) {
                is Either.Left -> _state.update { it.copy(error = request.value) }
                is Either.Right -> _state.update { it.copy(players = request.value) }
            }
            _state.update { it.copy(loading = false) }
        }
    }
}