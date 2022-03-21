package es.eduardocalzado.teamwise.ui.main

import androidx.navigation.NavController
import es.eduardocalzado.teamwise.model.database.Team
import es.eduardocalzado.teamwise.model.utils.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// MainStateHolder. Create it always with the view creation
class MainState(
    private val scope: CoroutineScope,
    private val locationPermissionRequester: PermissionRequester,
    private val navController: NavController,
) {

    fun onTeamClicked(team: Team) {
        val navAction = MainFragmentDirections.actionMainToDetail(team)
        navController.navigate(navAction)
    }

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch{
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }
}