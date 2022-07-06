package es.eduardocalzado.teamwise.ui.players

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.databinding.FragmentPlayersBinding
import es.eduardocalzado.teamwise.ui.main.MainState
import es.eduardocalzado.teamwise.ui.main.MainViewModel
import es.eduardocalzado.teamwise.ui.main.TeamAdapter
import es.eduardocalzado.teamwise.ui.main.buildMainState
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayersFragment : Fragment(R.layout.fragment_players), AdapterView.OnItemClickListener {

    private val viewModel: PlayersViewModel by viewModels()

    private lateinit var binding: FragmentPlayersBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding = FragmentPlayersBinding.bind(view).apply {
            val countries = resources.getStringArray(R.array.countries)
            val countriesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, countries)
            autoCompleteTextViewCountry.setAdapter(countriesAdapter)
            autoCompleteTextViewCountry.onItemClickListener = this@PlayersFragment

            val seasons = resources.getStringArray(R.array.seasons)
            val seasonsAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, seasons)
            autoCompleteTextViewSeason.setAdapter(seasonsAdapter)
        }*/
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        /*var leagues = when(parent?.getItemAtPosition(position).toString()) {
            "Spain" -> resources.getStringArray(R.array.spanish_leagues)
            "England" -> resources.getStringArray(R.array.england_leagues)
            "Italy" -> resources.getStringArray(R.array.italy_leagues)
            else -> return
        }
        val leaguesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, leagues)
        binding.autoCompleteTextViewLeague.setText(leaguesAdapter.getItem(0).toString())
        binding.autoCompleteTextViewLeague.setAdapter(leaguesAdapter)*/
    }
}