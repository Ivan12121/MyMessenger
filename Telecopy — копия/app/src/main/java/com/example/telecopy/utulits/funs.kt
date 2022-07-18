package com.example.telecopy.utulits

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.provider.ContactsContract
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.telecopy.R
import com.example.telecopy.activities.RegisterActivity
import com.example.telecopy.models.CommonModel
import com.example.telecopy.ui.fragments.ChatFragment
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_settings.*
import java.text.SimpleDateFormat
import java.util.*

fun showToast(message: String){
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity){
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}



fun AppCompatActivity.replaceFragment(fragment: Fragment, addstack: Boolean = true) {
    if(addstack) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.data_content, fragment).commit()
    }
    else {
        supportFragmentManager.beginTransaction()
            .replace(R.id.data_content, fragment).commit()
    }

}

fun Fragment.replaceFragment(fragment: Fragment) {

    this.fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(R.id.data_content, fragment)?.commit()
}

fun ImageView.downloadAndSetImage(url:String) {
    Picasso.get()
        .load(url)
        .fit()
        .placeholder(R.drawable.ic_phone)
        .into(this)
}

@SuppressLint("Range")
fun initContacts() {
    if(checkpermissions(Manifest.permission.READ_CONTACTS)) {
        var arrayContacts = arrayListOf<CommonModel>()
        val cursor = APP_ACTIVITY.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.let {
            while(it.moveToNext()) {
                val fullName = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val newModel = CommonModel()
                newModel.fullname = fullName
                newModel.phone = phone.replace(Regex("[\\s,-]"),"")
                arrayContacts.add(newModel)
            }
        }
        cursor?.close()
        updatePhonesToDatabase(arrayContacts)

    }
}

fun String.asTime(): String {
    val time  = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}
