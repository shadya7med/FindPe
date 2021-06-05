package com.iti.example.findpe2.home.chat.chatRoomsList.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentChatBinding
import com.iti.example.findpe2.home.chat.chatInstance.views.ChatPageActivity
import com.iti.example.findpe2.home.chat.chatRoomsList.viewModels.ChatRoomsListViewModel


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
        binding.lifecycleOwner = viewLifecycleOwner
        val chatRoomViewModel = ViewModelProvider(this).get(ChatRoomsListViewModel::class.java)
        binding.chatRoomsListViewModel = chatRoomViewModel
        //setLoading()

        val chatRoomsListAdapter =
            ChatRoomsListAdapter(ChatRoomsListAdapter.ChatRoomsClickListener { chatRoom ->
                chatRoomViewModel.onNavigateToChatPage(chatRoom)
            })

        chatRoomViewModel.navigateToChatPageData.observe(viewLifecycleOwner) {
            it?.let {
                //open chat page
                val openChatPageIntent = Intent(requireActivity(), ChatPageActivity::class.java)
                openChatPageIntent.putExtra(Keys.CHAT_ROOM_KEY, it)
                startActivity(openChatPageIntent)
                chatRoomViewModel.onDoneNavigateToChatPage()
            }
        }

        binding.chatListRcyViewChatHome.adapter = chatRoomsListAdapter

        /*chatRoomViewModel.chatRoomsList.observe(viewLifecycleOwner) {
            it?.let {
                clearLoading()
                if (it.isEmpty()) {
                    binding.noChatsImgViewChatsListHome.visibility = View.VISIBLE
                    binding.noChatsTxtViewChatsListHome.visibility = View.VISIBLE
                    binding.chatListRcyViewChatHome.visibility = View.GONE
                }
                chatRoomsListAdapter.submitList(it)
            }

        }*/
        chatRoomViewModel.errorMsg.observe(viewLifecycleOwner) {
            it?.let {
                //Show Snack bar with exp
                Snackbar
                    .make(requireActivity().findViewById(android.R.id.content), "Couldn't retrieve chats with error: $it", Snackbar.LENGTH_LONG)
                    .show()
            }

        }


        return binding.root
    }


    companion object {
        const val _4_DAYS_MILLIS = 345600000L
        const val ONE_DAY_MILLIS = 86400000L
        const val ONE_YEAR_MILLIS = 31536000000L
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