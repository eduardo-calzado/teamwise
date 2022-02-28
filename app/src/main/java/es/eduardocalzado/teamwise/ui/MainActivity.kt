package es.eduardocalzado.teamwise.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.constants.Constants.Companion.LEAGUE
import es.eduardocalzado.teamwise.constants.Constants.Companion.SEASON
import es.eduardocalzado.teamwise.constants.Constants.Companion.TEAM
import es.eduardocalzado.teamwise.databinding.ActivityMainBinding
import es.eduardocalzado.teamwise.network.APIFootballConnection
import es.eduardocalzado.teamwise.network.TeamRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val teamRepository by lazy { TeamRepository(this) }

    private val adapter = TeamAdapter {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(TEAM, it)
        startActivity(intent)
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Teamwise)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter
        callGetTeams()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun callGetTeams() {
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            val res = teamRepository.getTeamsByRegion()
            if (res.errors.isEmpty() && res.results != 0) {
                adapter.teams = res.teams
                adapter.notifyDataSetChanged()
                binding.noresults.visibility = View.GONE
            } else {
                binding.noresults.visibility = View.VISIBLE
            }
            Log.d(this.toString(), "errors: "+res.errors.toString())
            binding.progressBar.visibility = View.GONE
        }
    }

}