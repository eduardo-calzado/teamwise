package es.eduardocalzado.teamwise.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.eduardocalzado.teamwise.di.TeamId
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.framework.server.RemoteTeamStatsData
import es.eduardocalzado.teamwise.usecases.FindTeamUseCase
import es.eduardocalzado.teamwise.usecases.SwitchTeamFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @TeamId private val teamId: Int,
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