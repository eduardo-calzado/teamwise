package es.eduardocalzado.teamwise.ui.detail

import androidx.lifecycle.*
import es.eduardocalzado.teamwise.model.Team
import es.eduardocalzado.teamwise.model.TeamStatsData
import es.eduardocalzado.teamwise.model.network.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel (
    private val teamRepository: TeamRepository,
    val team: Team
): ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val teamStats: TeamStatsData? = null,
        val teamData: Team? = null,
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(teamData = team)
        if (_state.value.teamStats == null) {
            refresh()
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            _state.value = _state.value.copy(teamStats = teamRepository.getTeamStats(team.details.id))
            _state.value = _state.value.copy(loading = false)
        }
    }
}

// boiler plate required: it will be solved with State Flow.
@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory (
    private val teamRepository: TeamRepository,
    private val team: Team
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(teamRepository, team) as T
    }
}