package es.eduardocalzado.teamwise.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.eduardocalzado.teamwise.data.database.Team
import es.eduardocalzado.teamwise.data.errors.Error
import es.eduardocalzado.teamwise.data.errors.toError
import es.eduardocalzado.teamwise.data.network.TeamRepository
import es.eduardocalzado.teamwise.domain.GetTeamsUseCase
import es.eduardocalzado.teamwise.domain.RequestTeamsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel (
    private val getTeamsUseCase: GetTeamsUseCase,
    private val requestTeamsUseCase: RequestTeamsUseCase,
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

    fun onUiReady() {
        viewModelScope.launch {
            // we do a copy because a copy won't overwrite the state in uncertain cases
            _state.update { it.copy(loading = true) }
            val error = requestTeamsUseCase()
            _state.update { it.copy(error = error) }
            _state.update { it.copy(loading = false) }
        }
    }
}

// boiler plate required: it will be solved with State Flow.
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory (
    private val getTeamsUseCase: GetTeamsUseCase,
    private val requestTeamsUseCase: RequestTeamsUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getTeamsUseCase, requestTeamsUseCase) as T
    }
}