package es.eduardocalzado.teamwise.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.eduardocalzado.teamwise.databinding.ActivityMainBinding
import es.eduardocalzado.teamwise.model.RemoteConnection
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var adapter = TeamAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter
        getTeams()
    }

    private fun getTeams() {
        lifecycleScope.launch {
            val res = RemoteConnection.service.getTeams()
            adapter.teams = res.teams
            adapter.notifyDataSetChanged()
        }
    }

}