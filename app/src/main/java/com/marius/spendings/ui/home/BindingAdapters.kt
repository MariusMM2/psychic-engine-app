package com.marius.spendings.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.LayoutTransition
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.databinding.BindingAdapter

/**
 * Animates the visibility change on a View using a fade in/out animation
 *
 * @param view the target view
 * @param show boolean to indicate the new visibility state ([View.VISIBLE]/[View.INVISIBLE]
 */
@BindingAdapter("animateVisibilityChange")
fun animateVisibilityChange(view: View, show: Boolean) {
    val oldVisibility = view.visibility
    val visibility = if (show) View.VISIBLE else View.INVISIBLE

    view.animate().cancel()

    if (oldVisibility != visibility) {
        view.animate().apply {
            alpha(if (show) 1f else 0f)
            duration = 150
            interpolator = LinearInterpolator()
            setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    if (view.visibility != View.VISIBLE) {
                        view.visibility = View.VISIBLE
                    }
                }

                override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                    if (!show) {
                        view.visibility = View.INVISIBLE
                    }
                }
            })
        }
    }
}

/**
 * Override for android:animateLayoutChanges to enable animations when a
 * child View's content (eg. text, color) changes.
 *
 * @param viewGroup the target ViewGroup
 * @param animate whether to enable or disable layout animations
 */
@BindingAdapter("animateLayoutChanges")
fun animateLayoutChanges(viewGroup: ViewGroup, animate: Boolean) {
    if (animate) {
        viewGroup.layoutTransition = LayoutTransition()
        viewGroup.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    } else {
        viewGroup.layoutTransition = null
    }
}