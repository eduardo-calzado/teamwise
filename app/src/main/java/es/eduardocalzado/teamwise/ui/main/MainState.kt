package es.eduardocalzado.teamwise.ui.main

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import es.eduardocalzado.teamwise.data.errors.Error

fun Fragment.buildMainState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) = MainState(context, scope, locationPermissionRequester, navController)

class MainState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val locationPermissionRequester: PermissionRequester,
    private val navController: NavController,
) {

    fun onTeamClicked(teamId: Int) {
        val navAction = MainFragmentDirections.actionMainToDetail(teamId)
        navController.navigate(navAction)
    }

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch{
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}