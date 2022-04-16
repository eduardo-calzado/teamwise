package es.eduardocalzado.teamwise.ui.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import es.eduardocalzado.teamwise.data.database.Team
import es.eduardocalzado.teamwise.data.extensions.loadUrl

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("toolbarTitle")
fun CollapsingToolbarLayout.setTitle(team: Team?) {
    title = if (team?.code.isNullOrEmpty()) team?.name else "["+team?.code+"] "+team?.name
}