package es.eduardocalzado.teamwise.ui.main.teams

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.eduardocalzado.teamwise.domain.Team

@BindingAdapter("items")
fun RecyclerView.setItems(teams: List<Team>?) {
    if (teams != null) {
        (adapter as? TeamAdapter)?.submitList(teams)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("teams", "copy")
fun TextView.setText(teams: List<Team>?, copy: String) {
    text = copy + " ("+ teams?.size.toString() + ") "
}