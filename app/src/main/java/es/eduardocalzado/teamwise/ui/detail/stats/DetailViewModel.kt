package es.eduardocalzado.teamwise.ui.detail.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.di.TeamId
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.domain.TeamLeague
import es.eduardocalzado.teamwise.domain.TeamStats
import es.eduardocalzado.teamwise.usecases.FindTeamUseCase
import es.eduardocalzado.teamwise.usecases.RequestTeamStatsUseCase
import es.eduardocalzado.teamwise.usecases.SwitchTeamFavoriteUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @TeamId private val teamId: Int,
    private val findTeamUseCase: FindTeamUseCase,
    private val requestTeamStatsUseCase: RequestTeamStatsUseCase,
    private val switchTeamFavoriteUseCase: SwitchTeamFavoriteUseCase,
): ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val teamStats: TeamStats? = null,
        val teamData: Team? = null,
        val error: Error? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findTeamUseCase(teamId)
                .collect {
                    value -> println("Collected team: $value")
                    _state.value = UiState(teamData = value)
                }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            when (val request = requestTeamStatsUseCase(TeamLeague.ENGLAND.leagueId, Constants.SEASON, teamId)) {
                is Either.Left -> _state.update { it.copy(error = request.value) }
                is Either.Right -> _state.update { it.copy(teamStats = request.value) }
            }
            _state.update { it.copy(loading = false) }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.teamData?.let {
                switchTeamFavoriteUseCase(it)
            }
        }
    }
}