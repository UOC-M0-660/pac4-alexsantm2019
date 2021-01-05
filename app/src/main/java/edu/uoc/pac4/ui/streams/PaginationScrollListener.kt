package edu.uoc.pac4.ui.streams

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by alex on 07/09/2020.
 */

abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = layoutManager.itemCount
        val visibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemPosition == totalItemCount - 1) {
                recyclerView.post {
                    loadMoreItems()
                }
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

}