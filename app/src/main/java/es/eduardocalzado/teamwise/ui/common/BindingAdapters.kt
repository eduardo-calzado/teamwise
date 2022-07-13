package es.eduardocalzado.teamwise.ui.common

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.domain.Team
import kotlinx.coroutines.*

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("value")
fun TextView.setText(value: Int?) {
    text = value.toString()
}

@BindingAdapter("webUrl")
fun WebView.setUrl(url: String) {
    this.loadUrl(url)
}

@BindingAdapter("boolValue")
fun TextView.setText(value: Boolean?) {
    text = when (value) {
        true -> resources.getString(R.string.yes)
        false -> resources.getString(R.string.no)
        else -> resources.getString(R.string.unknown)
    }
}