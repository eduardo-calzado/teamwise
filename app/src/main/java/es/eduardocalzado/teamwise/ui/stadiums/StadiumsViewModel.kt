package es.eduardocalzado.teamwise.ui.stadiums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.usecases.RequestTeamsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StadiumsViewModel @Inject constructor(
): ViewModel() {
}