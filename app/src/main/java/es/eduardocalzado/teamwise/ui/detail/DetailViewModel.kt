package es.eduardocalzado.teamwise.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.eduardocalzado.teamwise.model.database.Team
import es.eduardocalzado.teamwise.model.network.TeamRepository
import es.eduardocalzado.teamwise.model.remotedata.RemoteTeamStatsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel (
    private val teamRepository: TeamRepository,
    teamId: Int
): ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val teamStats: RemoteTeamStatsData? = null,
        val teamData: Team? = null,
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            teamRepository.findById(teamId).collect {
                _state.value = UiState(teamData = it)
            }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.teamData?.let {
                teamRepository.switchFavorite(it)
            }
        }
    }
}

// boiler plate required: it will be solved with State Flow.
@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory (
    private val teamRepository: TeamRepository,
    private val teamId: Int
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(teamRepository, teamId) as T
    }
}