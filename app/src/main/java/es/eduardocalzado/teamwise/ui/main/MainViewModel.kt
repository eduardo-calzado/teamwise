package es.eduardocalzado.teamwise.ui.main

import androidx.lifecycle.*
import es.eduardocalzado.teamwise.model.Team
import es.eduardocalzado.teamwise.model.network.TeamRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainViewModel (
    private val teamRepository: TeamRepository
): ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val teams: List<Team>? = null,
        val navigateTo: Team? = null
    )

    private val _state = MutableLiveData(UiState())
    val state : LiveData<UiState> get() {
        if (_state.value?.teams == null) {
            refresh()
        }
        return _state
    }

    private fun refresh() {
        viewModelScope.launch {
            // we do a copy because a copy won't overwrite the state in uncertain cases
            _state.value = _state.value?.copy(loading = true)
            _state.value = _state.value?.copy(teams = teamRepository.getTeamsByRegion().teams)
            _state.value = _state.value?.copy(loading = false)
            //_state.value = UiState(teams = teamRepository.getTeamsByRegion().teams)
        }
    }

    fun onTeamClicked(team: Team) {
        _state.value = _state.value?.copy(navigateTo = team)
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