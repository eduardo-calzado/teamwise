package es.eduardocalzado.teamwise.ui.detail.players

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.domain.Error
import kotlinx.coroutines.CoroutineScope

fun Fragment.buildPlayerState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
) = PlayersState(context, scope, navController)

class PlayersState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController,
) {

    fun onPlayerClicked(playerId: Int) {
        // TODO
        // val navAction = PlayersFragmentDirections.actionPlayersToPlayer(playerId)
        val navAction = PlayersFragmentDirections.actionPlayersToPlayer()
        navController.navigate(navAction)
    }

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}