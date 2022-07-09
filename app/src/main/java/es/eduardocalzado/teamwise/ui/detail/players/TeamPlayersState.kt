package es.eduardocalzado.teamwise.ui.detail.players

import android.Manifest
import android.content.Context
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.di.LeagueId
import es.eduardocalzado.teamwise.di.SeasonId
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.domain.Team
import es.eduardocalzado.teamwise.ui.common.PermissionRequester
import es.eduardocalzado.teamwise.ui.common.toggleVisibility
import es.eduardocalzado.teamwise.ui.main.teams.MainState.MainFilters.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildTeamPlayerState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
) = TeamPlayersState(context, scope, navController)

class TeamPlayersState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController,
) {

    fun onPlayerClicked(playerId: Int) {
        // val navAction = TeamPlayersFragmentDirections.actionMainToDetail(playerId)
        // navController.navigate(navAction)
    }

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}