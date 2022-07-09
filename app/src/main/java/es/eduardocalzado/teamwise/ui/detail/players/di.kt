package es.eduardocalzado.teamwise.ui.detail.players

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import es.eduardocalzado.teamwise.di.*

@Module
@InstallIn(ViewModelComponent::class)
class TeamPlayersViewModelModule {

    @Provides
    @ViewModelScoped
    @TeamPlayerId
    fun provideTeamPlayerId(savedStateHandle: SavedStateHandle) =
        TeamPlayersFragmentArgs.fromSavedStateHandle(savedStateHandle).teamPlayerId

    @Provides
    @ViewModelScoped
    @SeasonPlayerId
    fun provideSeasonPlayerId(savedStateHandle: SavedStateHandle) =
        TeamPlayersFragmentArgs.fromSavedStateHandle(savedStateHandle).seasonPlayerId
}