package com.example.workflow_manager_frontend.presentation.workflow.formfields

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.canhub.cropper.parcelable
import com.example.workflow_manager_frontend.databinding.FragmentFormFieldsBinding
import com.example.workflow_manager_frontend.domain.FormField
import com.example.workflow_manager_frontend.domain.WorkflowExecutionContext
import com.example.workflow_manager_frontend.presentation.workflow.NextFragmentListener

/**
 * A simple [Fragment] subclass.
 * Use the [FormFieldsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FormFieldsFragment(
) : Fragment() {

    private var _binding: FragmentFormFieldsBinding? = null

    private val binding get() = _binding!!

    private var formFields: List<FormField>? = null
    private lateinit var workflowExecutionContext : WorkflowExecutionContext
    private lateinit var nextFragmentListener: NextFragmentListener


    private val classTag = "FormFieldsFragment"


    companion object {
        private const val ARG_FORMFIELDS = "formFields"
        private const val ARG_WORKFLOW_EXECUTION_CONTEXT = "workflowExecutionContext"
        private const val ARG_NEXT_FRAGMENT_LISTENER = "nextFragmentListener"

        fun newInstance(formFields: List<FormField>?, workflowExecutionContext: WorkflowExecutionContext, listener: NextFragmentListener): FormFieldsFragment {
            val fragment = FormFieldsFragment()
            val args = Bundle().apply {
                putParcelableArrayList(ARG_FORMFIELDS, ArrayList(formFields))
                putParcelable(ARG_WORKFLOW_EXECUTION_CONTEXT, workflowExecutionContext)
                putParcelable(ARG_NEXT_FRAGMENT_LISTENER, listener as Parcelable)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormFieldsBinding.inflate(inflater, container, false)

        formFields = arguments?.getParcelableArrayList(ARG_FORMFIELDS)
        workflowExecutionContext = arguments?.parcelable(ARG_WORKFLOW_EXECUTION_CONTEXT)!!
        nextFragmentListener = arguments?.parcelable(ARG_NEXT_FRAGMENT_LISTENER)!!

        binding.recycleView.adapter = FormFieldAdapter(FormFieldDiffCallback())
        binding.recycleView.layoutManager = LinearLayoutManager(context)
        val listAdapter = binding.recycleView.adapter as ListAdapter<FormField, FormFieldAdapter.FormFieldViewHolder>
        listAdapter.submitList(formFields?.toMutableList())

        binding.submitButton.setOnClickListener {
            val formfieldAdapter = binding.recycleView.adapter as FormFieldAdapter
            formfieldAdapter.onSubmitForm(workflowExecutionContext)
            nextFragmentListener.onNextFragment()
            Log.d(classTag, workflowExecutionContext.toString())
        }


        return binding.root
    }

}