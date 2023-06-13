package com.example.workflow_manager_frontend.presentation.main.home.workflow.editablehtml

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.canhub.cropper.parcelable
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.databinding.FragmentEditableHtmlBinding
import com.example.workflow_manager_frontend.domain.EditableHtml
import com.example.workflow_manager_frontend.domain.WorkflowExecutionContext
import com.example.workflow_manager_frontend.presentation.main.home.workflow.NextFragmentListener
import dagger.hilt.android.AndroidEntryPoint
import org.apache.commons.text.StringEscapeUtils
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

@AndroidEntryPoint
class EditableHtmlFragment() : Fragment() {

    private var _binding: FragmentEditableHtmlBinding? = null

    private val binding get() = _binding!!

    private var editableHtml: EditableHtml? = null

    private lateinit var workflowExecutionContext: WorkflowExecutionContext
    private lateinit var nextFragmentListener: NextFragmentListener

    companion object {
        private const val ARG_EDITABLE_HTML = "editableHtml"
        private const val ARG_WORKFLOW_EXECUTION_CONTEXT = "workflowExecutionContext"
        private const val ARG_NEXT_FRAGMENT_LISTENER = "nextFragmentListener"

        fun newInstance(editableHtml: EditableHtml?, workflowExecutionContext: WorkflowExecutionContext, nextFragmentListener: NextFragmentListener): EditableHtmlFragment {
            val fragment = EditableHtmlFragment()
            val args = Bundle().apply {
                putParcelable(ARG_EDITABLE_HTML, editableHtml)
                putParcelable(ARG_WORKFLOW_EXECUTION_CONTEXT, workflowExecutionContext)
                putParcelable(ARG_NEXT_FRAGMENT_LISTENER, nextFragmentListener as Parcelable)
            }
            fragment.arguments = args
            return fragment
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        editableHtml = arguments?.parcelable(ARG_EDITABLE_HTML)
        workflowExecutionContext = arguments?.parcelable(ARG_WORKFLOW_EXECUTION_CONTEXT)!!
        nextFragmentListener = arguments?.parcelable(ARG_NEXT_FRAGMENT_LISTENER)!!

        _binding = FragmentEditableHtmlBinding.inflate(inflater, container, false)

        binding.btnSkip.visibility = if(editableHtml?.isRequired == true) View.GONE
            else View.VISIBLE
        binding.btnSkip.setOnClickListener {
            nextFragmentListener.onNextFragment()
        }

        binding.submitButton.setOnClickListener {
            handleSubmitButton()
        }
        return binding.root

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = view.findViewById<WebView>(R.id.webView)
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true

        val htmlContent = resources.openRawResource(R.raw.response)
            .bufferedReader().use {it.readText()}
        //val htmlContent = Html.fromHtml(editableHtml?.content, Html.FROM_HTML_MODE_LEGACY).toString()
        Log.d("WebView", "HTML content: $htmlContent")
        if (htmlContent != null) {
            webView.loadData(htmlContent, "text/html", "UTF-8")
        }
    }

    private fun handleSubmitButton() {
        binding.webView.evaluateJavascript(
            "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
            ValueCallback {html ->
                if(html == null) {
                    Log.e(tag, "null html")
                    return@ValueCallback
                }
                val uploadedHtmlVariable  = editableHtml?.uploadedEditedHtmlFileVariable
                val htmlFile = saveAsFile(StringEscapeUtils.unescapeEcmaScript(html.removeSurrounding("\"")))
                if(uploadedHtmlVariable != null && htmlFile != null) {
                    workflowExecutionContext.fileMap[uploadedHtmlVariable] = htmlFile
                }
                Log.d(tag, workflowExecutionContext.toString())
                nextFragmentListener.onNextFragment()
            }
        );
    }

    private fun saveAsFile(html: String) :File?{

        val file = File(context?.cacheDir ?: return null,
            UUID.randomUUID().toString() + ".html")
        try {
            file.createNewFile()

            val writer = FileWriter(file)
            writer.write(html)
            writer.flush()
            writer.close()

        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return file
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}