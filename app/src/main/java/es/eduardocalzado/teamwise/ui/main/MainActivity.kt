package es.eduardocalzado.teamwise.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.model.constants.Constants.Companion.TEAM
import es.eduardocalzado.teamwise.databinding.ActivityMainBinding
import es.eduardocalzado.teamwise.model.Team
import es.eduardocalzado.teamwise.model.network.TeamRepository
import es.eduardocalzado.teamwise.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(TeamRepository(this)) }

    lateinit var binding: ActivityMainBinding
    private val adapter = TeamAdapter { viewModel.onTeamClicked(it)}

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Teamwise)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter
        // --
        viewModel.state.observe(this) {
            updateUi(it)
        }
    }

    private fun updateUi(state: MainViewModel.UiState) {
        with(binding) {
            progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE
            if (state.teams.isNullOrEmpty()) {
                if (progressBar.visibility == View.GONE) noresults.visibility = View.VISIBLE
                else noresults.visibility = View.GONE
            }
        }
        state.teams?.let {
            adapter.submitList(it)
        }
        state.navigateTo?.let {
            navigateTo(it)
        }
    }

    private fun navigateTo(team: Team) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(TEAM, team)
        startActivity(intent)
    }
}