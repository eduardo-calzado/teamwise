package es.eduardocalzado.teamwise.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.eduardocalzado.teamwise.data.database.Team
import es.eduardocalzado.teamwise.data.errors.toError
import es.eduardocalzado.teamwise.data.network.TeamRepository
import es.eduardocalzado.teamwise.data.remotedata.RemoteTeamStatsData
import es.eduardocalzado.teamwise.domain.FindTeamUseCase
import es.eduardocalzado.teamwise.domain.SwitchTeamFavoriteUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel (
    teamId: Int,
    findTeamUseCase: FindTeamUseCase,
    private val switchTeamFavoriteUseCase: SwitchTeamFavoriteUseCase,
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
            findTeamUseCase(teamId)
                .collect { _state.value = UiState(teamData = it) }
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

// boiler plate required: it will be solved with State Flow.
@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory (
    private val teamId: Int,
    private val findTeamUseCase: FindTeamUseCase,
    private val switchTeamFavoriteUseCase: SwitchTeamFavoriteUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(teamId, findTeamUseCase, switchTeamFavoriteUseCase) as T
    }
}