package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.databinding.FragmentStepsBinding
import com.example.workflow_manager_frontend.domain.*

/**
 * A simple [Fragment] subclass.
 * Use the [StepsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StepsFragment(
    private val stepsViewModel: StepsViewModel
) : Fragment(), DeleteStepListener, HtmlUploadListener{
    private var _binding: FragmentStepsBinding? = null
    private val binding get() = _binding!!

    private val classTag = "StepsFragment"

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode != RESULT_OK) {
            Log.d(classTag, result.toString())
            return@registerForActivityResult
        }
        val intent = result.data
            ?: return@registerForActivityResult
        val fileName = intent.data?.let { uri -> getFileNameByUri(uri) }
            ?: return@registerForActivityResult
        val position = stepsViewModel.getSelectedPoition()
        if(position == -1)
            return@registerForActivityResult
        stepsViewModel.updateHtmlForEditableHtml(position,fileName)
        stepsViewModel.setSelectedPosition(-1)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentStepsBinding.inflate(inflater, container, false)

        setUpAddStepsMenu()
        setUpRecycleView()

        bind()
        stepsViewModel.getWorkflowSteps().observe(viewLifecycleOwner) { workflowSteps ->
            val stepAdapter: WorkflowStepsAdapter =
                binding.recyclerView.adapter as WorkflowStepsAdapter
            stepAdapter.setItems(workflowSteps.toMutableList())
        }

        binding.nameTextField.editText?.addTextChangedListener {
            stepsViewModel.setWorkflowName(it.toString())
        }
        binding.descriptionTextField.editText?.addTextChangedListener {
            stepsViewModel.setWorkflowDescription(it.toString())
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setUpAddStepsMenu() {
        binding.addFab.setOnClickListener{ view ->
            val popup = PopupMenu(requireContext(), binding.addFab)
            popup.menuInflater.inflate(R.menu.add_workflow_step_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_item_document -> {
                        Log.d(classTag, "document")
                        stepsViewModel.addItem(WorkflowStep(
                            document = Document(),
                            stepType = "DOCUMENT"))
                        true
                    }
                    R.id.menu_item_form -> {
                        val formFields = mutableListOf<FormField>()
                        formFields.add(FormField())
                        stepsViewModel.addItem(WorkflowStep(
                            formFields = formFields,
                            stepType = "FORM_FIELD"))
                        true
                    }
                    R.id.menu_item_editable_html -> {
                        Log.d(classTag, "editable html")
                        stepsViewModel.addItem(WorkflowStep(
                            editableHtml = EditableHtml(),
                            stepType = "EDITABLE_HTML"))
                        true
                    }
                    R.id.menu_item_email -> {
                        Log.d(classTag, "email step")
                        stepsViewModel.addItem(WorkflowStep(
                            email = Email(),
                            stepType = "EMAIL"
                        ))
                        true
                    }
                    else -> false
                }
            }
            popup.setForceShowIcon(true)
            popup.show()
        }
    }

    private fun setUpRecycleView() {
        binding.recyclerView.adapter = WorkflowStepsAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDeleteStep(position: Int) {
        stepsViewModel.deleteStep(position)
    }

    override fun onHtmlUpload(position: Int) {
            val intent =  ActivityResultContracts.GetContent().createIntent(
                requireContext(),
                "text/html"
            )
        stepsViewModel.setSelectedPosition(position)
        filePickerLauncher.launch(intent)
    }

    private fun getFileNameByUri(uri: Uri) : String? {
        val returnCursor =
                requireContext().contentResolver.query(uri, null, null, null, null)
                ?: return null
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val fileName = returnCursor.getString(nameIndex)
        returnCursor.close()
        return fileName
    }

    private fun bind() {
        stepsViewModel.setWorkflowName(binding.nameTextField.editText?.text.toString())
        stepsViewModel.setWorkflowDescription(binding.descriptionTextField.editText?.text.toString())
    }
}