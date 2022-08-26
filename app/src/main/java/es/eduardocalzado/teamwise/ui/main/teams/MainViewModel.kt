package es.eduardocalzado.teamwise.ui.main.teams

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.data.TeamRepository
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.framework.toError
import es.eduardocalzado.teamwise.prefs
import es.eduardocalzado.teamwise.usecases.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTeamsUseCase: GetTeamsUseCase,
    private val requestTeamsUseCase: RequestTeamsUseCase,
    private val deleteTeamsUseCase: DeleteTeamsUseCase,
    private val searchTeamsUseCase: SearchTeamsUseCase,
    private val getRegionRepositoryUseCase: GetRegionRepositoryUseCase,
    ): ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val teams: List<Team>? = null,
        val error: Error? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getTeamsUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { teams -> _state.update { UiState(teams = teams) } }
        }
    }

    suspend fun onRequestLastRegion() = getRegionRepositoryUseCase()

    fun onSubmitClicked(country: String, league: Int, season: Int) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val error = requestTeamsUseCase(country, league, season)
            _state.update { it.copy(error = error) }
            _state.update { it.copy(loading = false) }
        }
    }

    fun saveFilterValues(country: String, league: Int, season: Int) {
        prefs.countryId = country
        prefs.leagueId = league
        prefs.seasonId = season
    }

    fun onDeleteClicked() {
        viewModelScope.launch {
            deleteTeamsUseCase()
        }
    }

    fun searchTeams(query: String) {
        viewModelScope.launch {
            searchTeamsUseCase(query)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { teams -> _state.update { UiState(teams = teams) } }
        }
    }
}