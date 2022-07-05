package es.eduardocalzado.teamwise.ui.stadiums

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.ui.main.MainState
import es.eduardocalzado.teamwise.ui.main.buildMainState

@AndroidEntryPoint
class StadiumsFragment : Fragment(R.layout.fragment_stadiums) {

    private val viewModel: StadiumsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}