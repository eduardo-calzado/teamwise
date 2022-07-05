package es.eduardocalzado.teamwise.ui.players

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.ui.main.MainState
import es.eduardocalzado.teamwise.ui.main.MainViewModel
import es.eduardocalzado.teamwise.ui.main.TeamAdapter
import es.eduardocalzado.teamwise.ui.main.buildMainState
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayersFragment : Fragment(R.layout.fragment_players) {

    private val viewModel: PlayersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}