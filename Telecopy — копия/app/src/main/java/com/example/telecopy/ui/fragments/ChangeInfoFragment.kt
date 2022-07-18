package com.example.telecopy.ui.fragments

import com.example.telecopy.R
import com.example.telecopy.utulits.*
import kotlinx.android.synthetic.main.fragment_change_info.*

class ChangeInfoFragment : BaseChangeFragment(R.layout.fragment_change_info) {

    override fun onResume() {
        super.onResume()
        settings_input_info.setText(USER.info)
    }


    override fun change() {
        super.change()
        val newInfo = settings_input_info.text.toString()
        REF_DATA_BASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_INFO).setValue(newInfo)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    USER.info = newInfo
                    fragmentManager?.popBackStack()
                }
            }
    }
}