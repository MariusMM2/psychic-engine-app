package com.marius.spendings.ui.common

/**
 * A [ItemBaseAdapter] that assigned objects to a single type of view.
 *
 *  @param layoutId the id of the layout each object will be assigned to
 */
abstract class SingleLayoutAdapter(private val layoutId: Int) : ItemBaseAdapter() {

    override fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }
}