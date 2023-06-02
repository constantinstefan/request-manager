package com.example.workflow_manager_frontend.presentation.workflow.document
import android.os.Bundle
import android.view.*
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.workflow_manager_frontend.databinding.FragmentDocumentCameraBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocumentCameraFragment : Fragment() {
    private var _binding : FragmentDocumentCameraBinding? = null;
    private val binding get() = _binding!!
    private lateinit var camera: Camera

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentDocumentCameraBinding.inflate(inflater, container, false)

        activity?.runOnUiThread {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
            cameraProviderFuture.addListener(Runnable {
                val cameraProvider = cameraProviderFuture.get()
                bindPreview(cameraProvider)
            }, ContextCompat.getMainExecutor(requireContext()))
        }

        return binding.root
    }

    fun bindPreview(cameraProvider : ProcessCameraProvider) {
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(binding.previewView.surfaceProvider)
            }
        camera = cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}