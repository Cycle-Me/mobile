package com.example.cycleme.ui.main.recycle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.cycleme.R
import com.example.cycleme.ui.main.upload_photo.UploadPhotoActivity

class RecycleFragment : Fragment() {

    private lateinit var uploadPhoto: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recycle, container, false)

        uploadPhoto = view.findViewById(R.id.btn_upload)
        uploadPhoto.setOnClickListener {
            val intent = Intent(activity, UploadPhotoActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}