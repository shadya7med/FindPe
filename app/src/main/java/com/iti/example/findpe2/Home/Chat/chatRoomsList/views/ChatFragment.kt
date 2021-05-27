package com.iti.example.findpe2.home.chat.chatRoomsList.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.authentication.setAllClickable
import com.iti.example.findpe2.databinding.FragmentChatBinding
import com.iti.example.findpe2.pojos.Message


class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)
        setLoading()
        val db = Firebase.firestore
        val currentUser = Firebase.auth.currentUser
        val collectionName =
            if (currentUser?.uid!! <= "I9cEcG0u1cP9V9oz7IhP5pZzj5N2") {
                currentUser?.uid + "I9cEcG0u1cP9V9oz7IhP5pZzj5N2"
            } else {
                "I9cEcG0u1cP9V9oz7IhP5pZzj5N2" + currentUser?.uid
            }
        db.collection(collectionName)
            .orderBy("time")
            .get()
            .addOnSuccessListener { result ->
                clearLoading()
                val msgList = result.toObjects(Message::class.java)
                Log.i("Chat", "${msgList[0].msgBody}")
            }
            .addOnFailureListener { exception ->
                clearLoading()
                Log.d("Chat", "Error getting documents: ", exception)
            }

        binding.chatListRcyViewChatHome.adapter =


        return binding.root
    }

    private fun setLoading() {
        binding.progressBarChatsListHome.visibility = View.VISIBLE
        binding.root.setAllClickable(false)
    }

    private fun clearLoading() {
        binding.progressBarChatsListHome.visibility = View.GONE
        binding.root.setAllClickable(true)

    }

}

/**
 * db = Firebase.firestore
 * Add users to Collection
 * //add user collection of Contacts
 *currentUser?.let{
 ** db.collection(it.uid)
 *      .document("I9cEcG0u1cP9V9oz7IhP5pZzj5N2")//yehia ID
 *      .set(User(true,"Yehia Azab"))
 *
 * //add the user to each Contact collection
 ** db.collection("I9cEcG0u1cP9V9oz7IhP5pZzj5N2")
 *      .document(currentUser.uid)
 *      .set(User(true,"Shady Ahmed"))
 *}
 *
 * */

/**Add message to chat room collection
 * 1. determine room name based on comparing IDs
//        val collectionName =
//            if (currentUser?.uid!! <= "I9cEcG0u1cP9V9oz7IhP5pZzj5N2") {
//                currentUser?.uid + "I9cEcG0u1cP9V9oz7IhP5pZzj5N2"
//            } else {
//                "I9cEcG0u1cP9V9oz7IhP5pZzj5N2" + currentUser?.uid
//            }

 * 2. add message to the selected room (collection)
// db.collection(collectionName)
//            .add(//auto generate for the doc id
//                Message(
//                    "Hi",
//                    "I9cEcG0u1cP9V9oz7IhP5pZzj5N2",
//                    currentUser?.uid,
//                    currentUser?.email ?: currentUser?.providerData[1].email,
//                    currentUser?.displayName,
//                    Calendar.getInstance().time
//                )
//            )
 *
 * */