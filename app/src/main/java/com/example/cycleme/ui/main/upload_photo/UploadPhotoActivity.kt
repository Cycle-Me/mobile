package com.example.cycleme.ui.main.upload_photo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cycleme.databinding.ActivityUploadPhotoBinding
import com.example.cycleme.repository.local.SessionPreferences
import com.example.cycleme.ui.main.camera.CameraActivity
import com.example.cycleme.ui.main.upload_result.UploadResultActivity
import com.example.cycleme.utils.reduceImageSize
import com.example.cycleme.utils.rotateFile
import com.example.cycleme.utils.uriToFile
import com.example.cycleme.data.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadPhotoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadPhotoBinding
    private lateinit var mSessionPreferences: SessionPreferences
    private val uploadPhotoViewModel by viewModels<UploadPhotoViewModel>()

    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSessionPreferences = SessionPreferences(this)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        setupActionBar()
        setupClickListeners()
        showLoading(false)
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Upload Photo"
    }

    private fun setupClickListeners() {
        binding.btnTakePicture.setOnClickListener {
            startCameraX()
        }

        binding.btnUploadPhoto.setOnClickListener {
            startGallery()
        }

        binding.btnSubmit.setOnClickListener {
            uploadImage()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.ivImage.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private fun startGallery() {
        val chooser = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        val galleryIntent = Intent.createChooser(chooser, "Choose a picture")
        launcherIntentGallery.launch(galleryIntent)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@UploadPhotoActivity)
                getFile = myFile
                binding.ivImage.setImageURI(uri)
            }
        }
    }

    private fun uploadImage() {

        showLoading(true)

        if (getFile != null) {
            val file = reduceImageSize(getFile as File)
            val reqImgFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image", file.name, reqImgFile
            )

            uploadPhotoViewModel.uploadPhoto(imageMultipart)

            uploadPhotoViewModel.resultUploadPhotoResponse.observe(this@UploadPhotoActivity) {
                when (it) {
                    is Result.Success -> {
                        val bundle = Bundle().apply {
                            putString("acc", it.data.accuracy)
                            putString("cat", it.data.class_label)
                        }
                        val intent = Intent(this, UploadResultActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                        showToast("Upload success")
                        showLoading(false)
                        finish()
                    }
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Error -> {
                        showToast(it.error)
                        showLoading(false)
                    }
                }
            }
        } else {
            showToast("Please upload a picture first")
            showLoading(false)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Failed to get permission",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun showToast(text: String) {
        Toast.makeText(this@UploadPhotoActivity, text, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        val progressBar = binding.progressBar
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        const val CAMERA_X_RESULT = 200
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}