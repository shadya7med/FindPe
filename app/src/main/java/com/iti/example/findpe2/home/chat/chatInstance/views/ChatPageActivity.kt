package com.iti.example.findpe2.home.chat.chatInstance.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.ActivityChatPageBinding
import com.iti.example.findpe2.home.chat.chatInstance.viewModels.ChatPageViewModel
import com.iti.example.findpe2.home.chat.chatInstance.viewModels.ChatPageViewModelFactory
import com.iti.example.findpe2.pojos.ChatRoom


class ChatPageActivity : AppCompatActivity() {

    //lateinit var binding : ActivityChatPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatPageBinding.inflate(layoutInflater)
        //setLoading()
        val chatRoom = intent.getParcelableExtra<ChatRoom>(Keys.CHAT_ROOM_KEY)
        val chatPageViewModel = ViewModelProvider(this, ChatPageViewModelFactory(chatRoom!!)).get(
            ChatPageViewModel::class.java
        )
        binding.lifecycleOwner = this
        binding.chatRoom = chatRoom
        binding.chatPageViewModel = chatPageViewModel
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        val messagesListAdapter = MessagesListAdapter(Firebase.auth.currentUser?.uid!!)
        messagesListAdapter.registerAdapterDataObserver(
            ScrollToBottomObserver(
                binding.msgListRcyViewChatPageChatActivity,
                messagesListAdapter,
                layoutManager
            )
        )
        binding.msgListRcyViewChatPageChatActivity.layoutManager = layoutManager
        binding.msgListRcyViewChatPageChatActivity.adapter = messagesListAdapter

        /*chatPageViewModel.messagesList.observe(this){
            it?.let {
                clearLoading()
                messagesListAdapter.submitList(it)
            }
        }
        chatPageViewModel.errorMsg.observe(this){
            it?.let {
                clearLoading()
                binding.msgListRcyViewChatPageChatActivity.visibility = View.GONE
                binding.topLayoutClChatPageChatActivity.visibility = View.GONE
                binding.bottomLayoutClChatPageChatActivity.visibility = View.GONE
                binding.noChatImgViewChatPageChatActivity.visibility = View.GONE
                binding.noChatTxtViewChatPageChatActivity.visibility = View.GONE

            }
        }*/

        binding.msgEditTxtChatPageChatActivity.addTextChangedListener(SendButtonObserver(binding.sendMsgImgViewChatPageChatActivity))
        binding.sendMsgImgViewChatPageChatActivity.setOnClickListener {
            chatPageViewModel.onSendMsg(binding.msgEditTxtChatPageChatActivity.text.toString())
            binding.msgEditTxtChatPageChatActivity.setText("")
        }

        chatPageViewModel.navigateUptoHome.observe(this){
            it?.let{
                finish()
                chatPageViewModel.onDoneNavigationToHome()
            }
        }


        chatPageViewModel.onSendMsgSuccess.observe(this){
            it?.let {
                if (it){
                    scrollToBottom(binding.msgListRcyViewChatPageChatActivity)
                }
                chatPageViewModel.onDoneSendingMsg()
            }
        }

        scrollToBottom(binding.msgListRcyViewChatPageChatActivity)

        setContentView(binding.root)
    }

    /*private fun setLoading() {
        binding.progressBarChatPageChatActivity.visibility = View.VISIBLE
        binding.root.setAllClickable(false)
    }

    private fun clearLoading() {
        binding.progressBarChatPageChatActivity.visibility = View.GONE
        binding.root.setAllClickable(true)

    }*/
    private fun scrollToBottom(recyclerView: RecyclerView) {
        // scroll to last item to get the view of last item
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        val adapter = recyclerView.adapter
        val lastItemPosition = adapter!!.itemCount - 1
        layoutManager!!.scrollToPositionWithOffset(lastItemPosition, 0)
        recyclerView.post { // then scroll to specific offset
            val target = layoutManager.findViewByPosition(lastItemPosition)
            if (target != null) {
                val offset = recyclerView.measuredHeight - target.measuredHeight
                layoutManager.scrollToPositionWithOffset(lastItemPosition, offset)
            }
        }
    }

}