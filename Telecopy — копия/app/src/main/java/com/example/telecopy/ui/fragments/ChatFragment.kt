package com.example.telecopy.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.telecopy.R
import com.example.telecopy.databinding.FragmentChatBinding
import com.example.telecopy.utulits.APP_ACTIVITY

class ChatFragment : Fragment(R.layout.fragment_chat) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Чаты"
    }

}