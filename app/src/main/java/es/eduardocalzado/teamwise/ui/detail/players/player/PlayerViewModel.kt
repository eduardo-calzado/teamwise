package es.eduardocalzado.teamwise.ui.detail.players.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import es.eduardocalzado.teamwise.di.*
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Player
import es.eduardocalzado.teamwise.framework.toError
import es.eduardocalzado.teamwise.ui.main.teams.MainViewModel
import es.eduardocalzado.teamwise.usecases.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    @TeamPlayerId private val teamPlayerId: Int,
    @SeasonPlayerId private val seasonPlayerId: Int,
    private val findPlayerByIdUseCase: FindPlayerByIdUseCase,
): ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val player: Player? = null,
        val error: Error? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findPlayerByIdUseCase(130)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { player -> _state.update { UiState(player = player) } }
        }
    }
}