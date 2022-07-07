package es.eduardocalzado.teamwise.ui.detail.stats

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import es.eduardocalzado.teamwise.di.TeamId

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {

    @Provides
    @ViewModelScoped
    @TeamId
    fun provideTeamId(savedStateHandle: SavedStateHandle) =
        DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).teamId
}