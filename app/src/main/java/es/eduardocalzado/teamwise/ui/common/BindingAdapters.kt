package es.eduardocalzado.teamwise.ui.common

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import es.eduardocalzado.teamwise.model.database.Team
import es.eduardocalzado.teamwise.model.extensions.loadUrl

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