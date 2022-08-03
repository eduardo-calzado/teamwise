package es.eduardocalzado.teamwise.ui.main.me

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import es.eduardocalzado.teamwise.R
import es.eduardocalzado.teamwise.data.Constants
import es.eduardocalzado.teamwise.databinding.FragmentMainBinding
import es.eduardocalzado.teamwise.databinding.FragmentMeBinding

@AndroidEntryPoint
class MeFragment : Fragment(R.layout.fragment_me) {

    private lateinit var binding: FragmentMeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMeBinding.bind(view).apply {
            meWebview.webViewClient = object: WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.meWebview.visibility = View.VISIBLE
                    binding.progressbar.visibility = View.GONE
                }
            }
            meWebview.loadUrl(Constants.EDUARDOCALZADO_ES)
            meWebview.settings.javaScriptEnabled = true
            meWebview.settings.setSupportZoom(false)
        }
    }
}
