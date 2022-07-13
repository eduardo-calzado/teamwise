package es.eduardocalzado.teamwise.ui.detail.stats

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.domain.Error
import es.eduardocalzado.teamwise.ui.detail.players.PlayersFragmentDirections

fun Fragment.buildDetailState(
    context: Context = requireContext(),
    navController: NavController = findNavController(),
    ) = DetailState(context, navController)

class DetailState(
    private val context: Context,
    private val navController: NavController,
) {

    fun onPlayersButtonClicked(teamId: Int, seasonId: Int, leagueId: Int ) {
        val navAction = DetailFragmentDirections.actionDetailToPlayers(teamId, seasonId, leagueId)
        navController.navigate(navAction)
    }

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}