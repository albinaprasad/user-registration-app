package com.albin.userregistrationapp

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.albin.userregistrationapp.databinding.ItemViewBinding
import com.google.android.material.animation.Positioning

class userAdapter(val context: Context,
           var userList: ArrayList<Users> ): RecyclerView.Adapter<userAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {


        val view = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {
        val currentUser = userList[position]
        holder.adapterBinding.name.text = currentUser.useraname.toString()
        holder.adapterBinding.address.text = currentUser.address.toString()
        holder.adapterBinding.email.text = currentUser.email.toString()
//animation

        val cardView = holder.adapterBinding.card
        cardView.clearAnimation()

        // Set up GradientDrawable for border glow
        val gradientDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(Color.WHITE) // Default CardView background
            setStroke(4, Color.YELLOW) // Initial border color
            cornerRadius = 8f // Match cardCornerRadius
        }
        if (cardView is CardView) {
            cardView.setCardBackgroundColor(Color.WHITE)
            cardView.background = gradientDrawable
        } else {
            cardView.background = gradientDrawable
        }

        // Create border glow animation
        val glowAnimator = ObjectAnimator.ofInt(
            gradientDrawable, "strokeColor",
            Color.CYAN, Color.YELLOW
        ).apply {
            duration = 1200
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            setEvaluator(ArgbEvaluator())
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Create bounce animation
        val bounceAnimator = ObjectAnimator.ofFloat(cardView, "translationY", 0f, -8f).apply {
            duration = 1200
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Combine animations
        val animatorSet = AnimatorSet().apply {
            play(glowAnimator).with(bounceAnimator)
            start()
        }

        // Store animatorSet for cancellation
        cardView.tag = animatorSet


        //click listener for the card View

        holder.adapterBinding.card.setOnClickListener {
            val intent = Intent(context, updateUser::class.java)

            intent.putExtra("id", currentUser.userID)
            intent.putExtra("name", currentUser.useraname)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("address", currentUser.address)

            context.startActivity(intent)
        }


    }


    override fun getItemCount(): Int {


        return userList.size
    }

    //
    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        val cardView = holder.adapterBinding.root
        (cardView.tag as? AnimatorSet)?.cancel()

        val gradientDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(Color.WHITE)
            setStroke(4, Color.YELLOW)
            cornerRadius = 8f
        }
        if (cardView is CardView) {
            cardView.setCardBackgroundColor(Color.WHITE)
            cardView.background = gradientDrawable
        } else {
            cardView.background = gradientDrawable
        }

        val glowAnimator = ObjectAnimator.ofInt(
            gradientDrawable, "strokeColor",
            Color.CYAN, Color.YELLOW
        ).apply {
            duration = 1200
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            setEvaluator(ArgbEvaluator())
            interpolator = AccelerateDecelerateInterpolator()
        }
        val bounceAnimator = ObjectAnimator.ofFloat(cardView, "translationY", 0f, -8f).apply {
            duration = 1200
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
        }
        val animatorSet = AnimatorSet().apply {
            play(glowAnimator).with(bounceAnimator)
            start()
        }
        cardView.tag = animatorSet
    }

    override fun onViewDetachedFromWindow(holder: UserViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val cardView = holder.adapterBinding.root
        (cardView.tag as? AnimatorSet)?.cancel()
    }




    //

    class UserViewHolder(val adapterBinding: ItemViewBinding): RecyclerView.ViewHolder(adapterBinding.root){}



    fun getuserId(position: Int): String{
        return userList[position].userID
    }
}