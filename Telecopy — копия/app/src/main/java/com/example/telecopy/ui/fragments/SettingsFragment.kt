package com.example.telecopy.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.canhub.cropper.*
import com.example.telecopy.MainActivity
import com.example.telecopy.R
import com.example.telecopy.activities.RegisterActivity
import com.example.telecopy.databinding.FragmentChatBinding
import com.example.telecopy.databinding.FragmentSettingsBinding
import com.example.telecopy.utulits.*
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        settings_about_me.text = USER.info
        settings_fullname.text = USER.fullname
        settings_phone_number.text = USER.phone
        settings_status.text = USER.state
        settings_user_name.text = USER.username
        settings_btn_change_user_name.setOnClickListener {
            replaceFragment(ChangeUsernameFragment())
        }
        settings_btn_change_about_me.setOnClickListener {
            replaceFragment(ChangeInfoFragment())
        }
        settings_change_photo.setOnClickListener {
            changePhotoUser()
        }
    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setActivityTitle("My Crop")
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .setCropMenuCropButtonTitle("Done")
            .setCropMenuCropButtonIcon(R.drawable.ic_arrow)
            .start(APP_ACTIVITY,this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AppStates.updateState(AppStates.OFFLINE)
                AUTH.signOut()
                (APP_ACTIVITY).replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> {
                replaceFragment(ChangeNameFragment())
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK && data != null
        ) {
            val uri = CropImage.getActivityResult(data)?.uriContent
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE).child(UID)

            if (uri != null) {
                putImageToStorage(uri, path) {
                    getUrlFromStorage(path) {
                        putUrlToDatabase(it) {
                            settings_user_photo.downloadAndSetImage(it)
                            showToast(getString(R.string.toast_data_update))
                            USER.photoUrl = it
                            APP_ACTIVITY.mAppDrawer.updateHeader()
                        }
                    }
                }
            }
        }
    }
}