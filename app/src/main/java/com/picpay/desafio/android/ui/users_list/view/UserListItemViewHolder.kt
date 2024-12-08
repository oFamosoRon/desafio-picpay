package com.picpay.desafio.android.ui.users_list.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.model.User
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {
        itemView.name.text = user.name ?: itemView.context.getString(R.string.name_not_provided)
        itemView.username.text =
            user.username ?: itemView.context.getString(R.string.username_not_provided)
        itemView.progressBar.visibility = View.VISIBLE

        user.img?.let { img ->
            Picasso.get()
                .load(img)
                .error(R.drawable.ic_round_account_circle)
                .into(itemView.picture, object : Callback {
                    override fun onSuccess() {
                        itemView.progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        itemView.progressBar.visibility = View.GONE
                    }
                })
        } ?: run {
            val placeHolderIcon = itemView.context.getDrawable(R.drawable.ic_round_account_circle)
            itemView.picture.setImageDrawable(placeHolderIcon)
            itemView.progressBar.visibility = View.GONE
        }
    }
}
