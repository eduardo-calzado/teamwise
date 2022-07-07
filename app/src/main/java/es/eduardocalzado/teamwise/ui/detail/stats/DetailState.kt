package es.eduardocalzado.teamwise.ui.detail.stats

import android.content.Context
import androidx.fragment.app.Fragment
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.domain.Error

fun Fragment.buildDetailState(
    context: Context = requireContext()
) = DetailState(context)

class DetailState(
    private val context: Context,
) {

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}