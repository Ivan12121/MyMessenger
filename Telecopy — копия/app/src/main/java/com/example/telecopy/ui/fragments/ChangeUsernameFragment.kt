package com.example.telecopy.ui.fragments

import com.example.telecopy.R
import com.example.telecopy.utulits.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*

class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    lateinit var mNewUsername: String

    override fun onResume() {
        super.onResume()
        settings_input_username.setText(USER.username)
    }

    override fun change() {
        mNewUsername = settings_input_username.text.toString().toLowerCase(Locale.getDefault())
        if(mNewUsername.isEmpty()) {
            showToast("Поле не может быть пустым")
        }
        else {
            REF_DATA_BASE_ROOT.child(NODE_USERNAME)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if(it.hasChild(mNewUsername)) {
                        showToast(getString(R.string.toast_username_is_exist))
                    }
                    else {
                        changeUsername()
                    }
                })
        }
    }

    private fun changeUsername() {
        REF_DATA_BASE_ROOT.child(NODE_USERNAME).child(mNewUsername).setValue(UID)
            .addOnCompleteListener{
                if(it.isSuccessful) {
                    updateCurrentUsername()
                }
            }
    }

    private fun updateCurrentUsername() {
        REF_DATA_BASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USER_NAME)
            .setValue(mNewUsername)
            .addOnCompleteListener{
                if(it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    deleteOldUsername()
                }
                else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun deleteOldUsername() {
        REF_DATA_BASE_ROOT.child(NODE_USERNAME).child(USER.username).removeValue()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    fragmentManager?.popBackStack()
                    USER.username = mNewUsername
                }
                else {
                    showToast(it.exception?.message.toString())
                }
            }
    }
}