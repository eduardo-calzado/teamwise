package es.eduardocalzado.teamwise.ui.detail.stats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.di.LeagueId
import es.eduardocalzado.teamwise.di.SeasonId
import es.eduardocalzado.teamwise.di.TeamId
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.domain.Team.Stats
import es.eduardocalzado.teamwise.usecases.FindTeamUseCase
import es.eduardocalzado.teamwise.usecases.RequestTeamStatsUseCase
import es.eduardocalzado.teamwise.usecases.SwitchTeamFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @TeamId val teamId: Int,
    @LeagueId val leagueId: Int,
    @SeasonId val seasonId: Int,
    private val findTeamUseCase: FindTeamUseCase,
    private val requestTeamStatsUseCase: RequestTeamStatsUseCase,
    private val switchTeamFavoriteUseCase: SwitchTeamFavoriteUseCase,
): ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val teamStats: Stats? = null,
        val teamData: Team? = null,
        val error: Error? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findTeamUseCase(teamId)
                .collect { teamData -> _state.update { it.copy(teamData = teamData) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            Log.d(Constants.TAG, "[DetailViewModel.onUiReady] teamId: $teamId seasonId: $seasonId, leagueId: $leagueId")
            when (val request = requestTeamStatsUseCase(leagueId, seasonId, teamId)) {
                is Either.Left -> _state.update { it.copy(error = request.value) }
                is Either.Right -> _state.update { it.copy(teamStats = request.value) }
                else -> {}
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