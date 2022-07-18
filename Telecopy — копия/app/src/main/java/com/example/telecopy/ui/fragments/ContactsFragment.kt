package com.example.telecopy.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.telecopy.R
import com.example.telecopy.models.CommonModel
import com.example.telecopy.ui.fragments.single_chat.SingleChatFragment
import com.example.telecopy.utulits.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.android.synthetic.main.fragment_contacts.*

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<CommonModel, ContactHolder>
    private lateinit var mRefcontacts: DatabaseReference
    private lateinit var mRefUsers: DatabaseReference
    private lateinit var mRefUsersListener: AppValueEventListener
    private var mapListener = hashMapOf<DatabaseReference, AppValueEventListener>()

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Контакты"
        initRecycleView()
    }

    private fun initRecycleView() {
        mRecyclerView = contact_recycle_view
        mRefcontacts = REF_DATA_BASE_ROOT.child(NODE_PHONES_CONTACTS).child(UID)

        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(mRefcontacts, CommonModel::class.java)
            .build()
        mAdapter = object: FirebaseRecyclerAdapter<CommonModel, ContactHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
                return ContactHolder(view)
            }

            override fun onBindViewHolder(
                holder: ContactHolder,
                position: Int,
                model: CommonModel
            ) {
                mRefUsers = REF_DATA_BASE_ROOT.child(NODE_USERS).child(model.id)
                mRefUsersListener = AppValueEventListener {
                    val contact = it.getCommonModel()
                    if(contact.fullname.isEmpty()) {
                        holder.name.text = model.fullname
                    }
                    else holder.name.text = contact.fullname

                    holder.state.text = contact.state
                    holder.photo.downloadAndSetImage(contact.photoUrl)
                    holder.itemView.setOnClickListener {
                        replaceFragment(SingleChatFragment(model))
                    }
                }
                mRefUsers.addValueEventListener(mRefUsersListener)
                mapListener[mRefUsers] = mRefUsersListener
            }
        }

        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()

    }

    class ContactHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.contact_fullname
        val state: TextView = view.contact_state
        val photo: CircleImageView = view.contact_photo
    }

    override fun onPause() {
        super.onPause()
        mAdapter.startListening()
        mapListener.forEach {
            it.key.removeEventListener(it.value)
        }
    }

}


