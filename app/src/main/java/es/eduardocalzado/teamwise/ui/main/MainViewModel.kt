package es.eduardocalzado.teamwise.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.eduardocalzado.teamwise.model.database.Team
import es.eduardocalzado.teamwise.model.network.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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

    init {
        viewModelScope.launch {
            teamRepository.teams.collect { teams ->
                _state.value = UiState(teams = teams)
            }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            // we do a copy because a copy won't overwrite the state in uncertain cases
            _state.value = _state.value.copy(loading = true)
            teamRepository.requestTeams()
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