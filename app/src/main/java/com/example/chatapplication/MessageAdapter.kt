package com.example.chatapplication

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.chatapplication.databinding.DeleteLayoutBinding
import com.example.chatapplication.databinding.SentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class MessageAdapter(
    var context: Context,
    messages:ArrayList<Message>?,
    senderRoom:String,
    receiverRoom:String
):RecyclerView.Adapter<ViewHolder?>()
{
    lateinit var messages: ArrayList<Message>
    val ITEM_SENT=1
    val ITEM_RECEIVE=2
    val senderRoom :String
    var receiverRoom:String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return if (viewType == ITEM_SENT){
            val view:View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            SentMsgHolder(view)
        }
        else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            ReceiveMsgHolder(view)

        }
    }

    override fun getItemViewType(position: Int): Int {
        val messages = messages[position]
        return if (FirebaseAuth.getInstance().uid == messages.senderId){
            ITEM_SENT
        }else{
            ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        if (holder.javaClass == SentMsgHolder::class.java){
            val viewHolder = holder as SentMsgHolder
            if (message.message.equals("photo")){
                viewHolder.binding.image.visibility = View.VISIBLE
                viewHolder.binding.message.visibility = View.GONE
                viewHolder.binding.mLinear.visibility = View.GONE
                Glide.with(context)
                    .load(message.imageUrl)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(viewHolder.binding.image)
            }
            viewHolder.binding.message.text = message.message
            viewHolder.itemView.setOnLongClickListener {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.delete_layout,null)

                val binding:DeleteLayoutBinding = DeleteLayoutBinding.bind(view)

                val dialog = AlertDialog.Builder(context)
                    .setView(binding.root)
                    .create()
                binding.deleteForEveryone.setOnClickListener{
                    message.message = "This message is removed"
                    message.messageId?.let {it1->
                        FirebaseDatabase.getInstance().reference.child("chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it1).setValue(message)
                    }
                    message.messageId?.let {it1->
                        FirebaseDatabase.getInstance().reference.child("chats")
                            .child(receiverRoom)
                            .child("message")
                            .child(it1).setValue(message)
                    }
                    dialog.dismiss()
                }

                binding.cancel.setOnClickListener { dialog.dismiss() }

                binding.deleteForMe.setOnClickListener{
                    message.messageId.let { it1->
                        FirebaseDatabase.getInstance().reference.child("chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it1!!).setValue(null)
                    }
                    dialog.dismiss()
                }
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                false
            }
        }
        else{
            val viewHolder = holder as ReceiveMsgHolder
            if (message.message.equals("photo")){

               viewHolder.binding.image.visibility = View.VISIBLE
                viewHolder.binding.message.visibility = View.GONE
                viewHolder.binding.mLinear.visibility = View.GONE
                Glide.with(context)
                    .load(message.imageUrl)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(viewHolder.binding.image)
            }
            viewHolder.binding.message.text = message.message
            viewHolder.itemView.setOnLongClickListener {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.delete_layout, null)

                val binding: DeleteLayoutBinding = DeleteLayoutBinding.bind(view)

                val dialog = AlertDialog.Builder(context)
                    .setView(binding.root)
                    .create()
                binding.deleteForEveryone.setOnClickListener {
                    message.message = "This message is removed"
                    message.messageId?.let { it1 ->
                        FirebaseDatabase.getInstance().reference.child("chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it1).setValue(message)
                    }
                    message.messageId?.let { it1 ->
                        FirebaseDatabase.getInstance().reference.child("chats")
                            .child(receiverRoom)
                            .child("message")
                            .child(it1).setValue(message)
                    }
                    dialog.dismiss()
                }
                binding.deleteForMe.setOnClickListener {
                    message.messageId.let { it1 ->
                        FirebaseDatabase.getInstance().reference.child("chats")
                            .child(senderRoom)
                            .child("message")
                            .child(it1!!).setValue(null)
                    }
                    dialog.dismiss()
                }
                binding.cancel.setOnClickListener { dialog.dismiss() }
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                false
            }
        }
    }

    override fun getItemCount(): Int = messages.size

    inner class SentMsgHolder(itemView: View):ViewHolder(itemView){
        var binding:SentBinding = SentBinding.bind(itemView)
    }

    inner class ReceiveMsgHolder(itemView: View):ViewHolder(itemView){
        var binding:SentBinding = SentBinding.bind(itemView)
    }

    init {
        if (messages != null){
            this.messages = messages
            }
        this.senderRoom = senderRoom
        this.receiverRoom = receiverRoom
    }
}