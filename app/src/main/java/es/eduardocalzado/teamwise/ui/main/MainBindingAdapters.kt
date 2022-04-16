package es.eduardocalzado.teamwise.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.data.database.Team

@BindingAdapter("items")
fun RecyclerView.setItems(teams: List<Team>?) {
    if (teams != null) {
        (adapter as? TeamAdapter)?.submitList(teams)
    }
}