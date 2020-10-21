package com.example.hwhard_kolin.util

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.widget.TextView

fun View.downAnimator() {
    val scaleX = ObjectAnimator.ofFloat(this, "ScaleX", 1f, 0.8f)
    val scaleY = ObjectAnimator.ofFloat(this, "ScaleY", 1f, 0.8f)
    val animatorSetScale = AnimatorSet()
    animatorSetScale.playTogether(scaleX, scaleY)
    animatorSetScale.setDuration(200).start()
}

fun View.upAnimator() {
    val scaleX = ObjectAnimator.ofFloat(this, "ScaleX", 0.8f, 1f)
    val scaleY = ObjectAnimator.ofFloat(this, "ScaleY", 0.8f, 1f)
    val animatorSetScale = AnimatorSet()
    animatorSetScale.playTogether(scaleX, scaleY)
    animatorSetScale.setDuration(200).start()
}

/**
 *  章節選取顯示動畫
 */
fun TextView.showAnimator(delay: Long = 0, text: String, finishAnimation: () -> Unit) {

    val scaleX = ObjectAnimator.ofFloat(this, "ScaleX", 0f, 1f)
    val scaleY = ObjectAnimator.ofFloat(this, "ScaleY", 0f, 1f)
    val animatorSetScale = AnimatorSet()
    animatorSetScale.playTogether(scaleX, scaleY)

    animatorSetScale.run {
        duration = 500
        startDelay = delay
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                finishAnimation.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {

                postDelayed({
                    visibility = View.VISIBLE
                    setText(text)
                }, delay + 50L)

            }
        })
        start()
    }

}

/**
 *  章節選取【收回動畫】
 *  0 -> 第一次不觸發
 */
fun TextView.chapterAnimator(
    isFirstAnimation: Int,
    delay: Long = 0,
    text: String,
    finishAnimation: () -> Unit = {}
) {

    if (isFirstAnimation == 0) {
        showAnimator(delay, text, finishAnimation)
        return
    }

    val scaleX = ObjectAnimator.ofFloat(this, "ScaleX", 1f, 1.2f, 0f)
    val scaleY = ObjectAnimator.ofFloat(this, "ScaleY", 1f, 1.2f, 0f)
    val animatorSetScale = AnimatorSet()
    animatorSetScale.playTogether(scaleX, scaleY)
    animatorSetScale.run {
        duration = 500
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                showAnimator(delay, text, finishAnimation)
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
        start()
    }

}