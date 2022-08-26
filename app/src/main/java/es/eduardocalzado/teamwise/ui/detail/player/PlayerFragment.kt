package es.eduardocalzado.teamwise.ui.detail.player

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentPlayerBinding
import es.eduardocalzado.teamwise.ui.common.CustomListAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val viewModel: PlayerViewModel by viewModels()

    private lateinit var binding: FragmentPlayerBinding
    private var adapter: CustomListAdapter = CustomListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // --
        binding = FragmentPlayerBinding.bind(view).apply {
            fragmentPlayerInfoLayout.playerRecycler.adapter = adapter
            fragmentPlayerToolbar.setNavigationOnClickListener{findNavController().popBackStack()}
        }
        // --
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.player = it.player
                }
            }
        }
    }
}