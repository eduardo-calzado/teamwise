package es.eduardocalzado.teamwise.ui.coaches

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R

@AndroidEntryPoint
class CoachesFragment : Fragment(R.layout.fragment_coaches) {

    private val viewModel: CoachesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}