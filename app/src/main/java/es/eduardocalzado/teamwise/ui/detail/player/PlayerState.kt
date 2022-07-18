package es.eduardocalzado.teamwise.ui.detail.player

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
) = PlayerState(context, scope, navController)

class PlayerState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController,
) {
    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}