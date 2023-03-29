package com.example.workflow_manager_frontend.presentation.workflow

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class CameraFirstFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentCameraFirstBinding? = null
    private val binding get() = _binding!!

    private var cameraLauncher: ActivityResultLauncher<Uri>? = null

    private lateinit var uri: Uri;

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        when {
            result.isSuccessful -> Log.i("crop", "success")
            result is CropImage.CancelledResult -> Log.e("crop","cropping image was cancelled by the user")
            else -> Log.e("crop","cropping image failed")
        }
    }


    override fun onCreateView(inflater: LayoutInflater,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?) : View?{
        super.onCreate(savedInstanceState)
        _binding = FragmentCameraFirstBinding.inflate(inflater,container,false);
        binding.btnTakePicture.setOnClickListener(::onButtonClicked)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}