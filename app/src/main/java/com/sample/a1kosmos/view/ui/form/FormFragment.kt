package com.sample.a1kosmos.view.ui.form

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sample.a1kosmos.data.model.form.UserForm
import com.sample.a1kosmos.databinding.FormFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class FormFragment : Fragment() {


    companion object {
        private const val REQUEST_PERMISSION = 100
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_PICK_IMAGE = 2
    }


    private var _binding: FormFragmentBinding? = null

    private val binding: FormFragmentBinding
        get() = _binding!!


    private val viewModel: FormViewModel by viewModels()
    private val userData = UserForm()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FormFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        viewModel.user.observe(viewLifecycleOwner, {

            with(binding) {
                edtFirstName.setText(it?.first_name)
                edtLastName.setText(it?.last_name)

                try {
                    if (it.avatar != null) {
                        val bmp = BitmapFactory.decodeByteArray(it.avatar, 0, it.avatar!!.size)
                        binding.imageView.setImageBitmap(bmp)
                    } else if (it.uri != null) {
                        binding.imageView.setImageURI(Uri.parse(it.uri))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })

        with(binding) {

            edtFirstName.doAfterTextChanged { userData.first_name = it.toString() }
            edtLastName.doAfterTextChanged { userData.last_name = it.toString() }

            btnCamera.setOnClickListener {
                openCamera()
            }
            btnGallery.setOnClickListener {
                openGallery()
            }
        }

    }


    private fun checkCameraPermission() {
        if (checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION
            )
        }
    }

    private fun openCamera() {
        try {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
                intent.resolveActivity(requireActivity().packageManager)?.also {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun openGallery() {
        try {
            Intent(Intent.ACTION_GET_CONTENT).also { intent ->
                intent.type = "image/*"
                intent.resolveActivity(requireActivity().packageManager)?.also {
                    startActivityForResult(intent, REQUEST_PICK_IMAGE)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val bitmap = data?.extras?.get("data") as Bitmap
                userData.avatar = bitmap.toByteArray()
                userData.uri = null
                binding.imageView.setImageBitmap(bitmap)
            } else if (requestCode == REQUEST_PICK_IMAGE) {
                val uri = data?.data
                userData.avatar = null
                userData.uri = uri.toString()
                binding.imageView.setImageURI(uri)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }


    override fun onPause() {
        super.onPause()
        viewModel.saveUsers(userData)
    }


    private fun Bitmap.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 90, stream)
        return stream.toByteArray()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}