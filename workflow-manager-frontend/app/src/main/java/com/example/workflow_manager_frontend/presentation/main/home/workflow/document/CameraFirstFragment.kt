package com.example.workflow_manager_frontend.presentation.main.home.workflow.document

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.canhub.cropper.*
import com.example.workflow_manager_frontend.BuildConfig
import com.example.workflow_manager_frontend.databinding.FragmentCameraFirstBinding
import com.example.workflow_manager_frontend.domain.Document
import com.example.workflow_manager_frontend.domain.WorkflowExecutionContext
import com.example.workflow_manager_frontend.presentation.main.home.workflow.NextFragmentListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class CameraFirstFragment() : Fragment() {
    private var _binding: FragmentCameraFirstBinding? = null
    private val binding get() = _binding!!

    private var cameraLauncher: ActivityResultLauncher<Uri>? = null

    private lateinit var uri: Uri;

    private var document: Document? = null
    private lateinit var workflowExecutionContext : WorkflowExecutionContext
    private lateinit var nextFragmentListener: NextFragmentListener

    private val classTag = "CameraFirstFragment"

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        when {
            result.isSuccessful -> result.uriContent?.let { handleCroppedImage(it) }
            result is CropImage.CancelledResult -> Log.e("crop","cropping image was cancelled by the user")
            else -> Log.e("crop","cropping image failed")
        }
    }


    companion object {
        private const val ARG_DOCUMENT = "document"
        private const val ARG_WORKFLOW_EXECUTION_CONTEXT = "workflowExecutionContext"
        private const val ARG_NEXT_FRAGMENT_LISTENER = "nextFragmentListener"

        fun newInstance(document: Document?, workflowExecutionContext: WorkflowExecutionContext, listener: NextFragmentListener): CameraFirstFragment {
            val fragment = CameraFirstFragment()
            val args = Bundle().apply {
                putParcelable(ARG_DOCUMENT, document)
                putParcelable(ARG_WORKFLOW_EXECUTION_CONTEXT, workflowExecutionContext)
                putParcelable(ARG_NEXT_FRAGMENT_LISTENER, listener as Parcelable)
            }
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?) : View?{
        super.onCreate(savedInstanceState)
        _binding = FragmentCameraFirstBinding.inflate(inflater,container,false);

        document = arguments?.parcelable(ARG_DOCUMENT)
        workflowExecutionContext = arguments?.parcelable(ARG_WORKFLOW_EXECUTION_CONTEXT)!!
        nextFragmentListener = arguments?.parcelable(ARG_NEXT_FRAGMENT_LISTENER)!!

        binding.btnTakePicture.setOnClickListener(::onButtonClicked)

        binding.btnSkip.visibility = if(document?.isRequired == true) View.GONE
            else View.VISIBLE
        binding.btnSkip.setOnClickListener {
            nextFragmentListener.onNextFragment()
        }

        binding.description.text = document?.description ?: ""
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture(),::onCameraResult)
        return binding.root;
    }

    private fun onButtonClicked(view: View) {
        if(!permissionsGranted()) {
            Log.e("permission", "Permission not granted")
            return
        }
        //uri = createPhotoUri()
        //cameraLauncher?.launch(uri)
        cropImage.launch(
            CropImageContractOptions(
                uri = null,
                cropImageOptions = CropImageOptions(
                    imageSourceIncludeCamera = true,
                    imageSourceIncludeGallery = true,
                ),
            ),
        )
    }

    private fun onCameraResult(success: Boolean) {
        cropImage.launch(
            CropImageContractOptions(
                uri = uri,
                cropImageOptions = CropImageOptions(),
            ),
        )
    }


    private fun permissionsGranted() : Boolean =  ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun createPhotoUri(): Uri {
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val photoFile = File.createTempFile("photo", ".jpg", storageDir)
        return FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".fileprovider", photoFile)
    }

    private fun handleCroppedImage(uri: Uri) {
        Log.d(classTag, "Crop success")
        val uploadedFileVariable = document?.uploadedFileVariable
        val responseFile = saveUriAsFile(uri)
        if( (uploadedFileVariable != null) && (responseFile != null)) {
            workflowExecutionContext.fileMap[uploadedFileVariable] = responseFile
        }
        Log.d(classTag, workflowExecutionContext.toString())
        nextFragmentListener.onNextFragment()
    }

    private fun saveUriAsFile(uri: Uri): File? {
        val context = requireContext()
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, uri.lastPathSegment)

        try {
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            outputStream.close()
            inputStream?.close()
            return file
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}