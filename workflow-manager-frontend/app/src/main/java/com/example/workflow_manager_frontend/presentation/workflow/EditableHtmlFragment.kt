package com.example.workflow_manager_frontend.presentation.workflow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.databinding.FragmentEditableHtmlBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

@AndroidEntryPoint
class EditableHtmlFragment : Fragment() {

    private var _binding: FragmentEditableHtmlBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditableHtmlBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = view.findViewById<WebView>(R.id.webView)
        val htmlContent = resources.openRawResource(R.raw.response)
            .bufferedReader().use {it.readText()}
        Log.d("WebView", "HTML content: $htmlContent")
        webView.loadData(htmlContent, "text/html", "UTF-8")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}