package es.eduardocalzado.teamwise.ui.main

import androidx.lifecycle.*
import es.eduardocalzado.teamwise.model.Team
import es.eduardocalzado.teamwise.model.network.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel (
    private val teamRepository: TeamRepository
): ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val teams: List<Team>? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    fun onUiReady() {
        viewModelScope.launch {
            // we do a copy because a copy won't overwrite the state in uncertain cases
            _state.value = _state.value.copy(loading = true)
            _state.value = _state.value.copy(teams = teamRepository.getTeamsByRegion().teams)
            _state.value = _state.value.copy(loading = false)
        }
    }
}

// boiler plate required: it will be solved with State Flow.
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory (
    private val teamRepository: TeamRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(teamRepository) as T
    }
}