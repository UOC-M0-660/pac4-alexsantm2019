package edu.uoc.pac4.ui.streams

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.uoc.pac4.R
import edu.uoc.pac4.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_streams.*
import org.koin.android.viewmodel.ext.android.viewModel

class StreamsActivity : AppCompatActivity() {

    private val TAG = "StreamsActivity"

    private val adapter = StreamsAdapter()
    private val layoutManager = LinearLayoutManager(this)
    private val streamsViewModel by viewModel<StreamsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_streams)
        // Init RecyclerView
        initRecyclerView()
        // Swipe to Refresh Listener
        swipeRefreshLayout.setOnRefreshListener {
            getStreams()
        }

        // Get Streams
        getStreams()
    }

    private fun initRecyclerView() {
        // Set Layout Manager
        recyclerView.layoutManager = layoutManager
        // Set Adapter
        recyclerView.adapter = adapter
        // Set Pagination Listener
        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                //getStreams(nextCursor)
                getStreams()
            }
            override fun isLastPage(): Boolean {
                //return nextCursor == null
                return streamsViewModel.isCursorNull()
            }
            override fun isLoading(): Boolean {
                return swipeRefreshLayout.isRefreshing
            }
        })
    }

    private fun getStreams() {
            // Get Streams
            streamsViewModel.getStreams()

            // Observe ViewModel Values
            // Stream List
            streamsViewModel.streamList.observe(this, { streamList ->
                // Update UI with Streams
                if (adapter.currentList.isNotEmpty()) {
                    // We are adding more items to the list
                    adapter.submitList(adapter.currentList.plus(streamList))
                } else {
                    // It's the first n items, no pagination yet
                    adapter.submitList(streamList)
                }
            })

            // isRefreshing
            streamsViewModel.isRefreshing.observe(this, { isRefreshing ->
                // Shows loading icon
                swipeRefreshLayout.isRefreshing = isRefreshing
            })

            // Show Error
            streamsViewModel.showError.observe(this, { showError ->
                // Show Error message to not leave the page empty
                if (adapter.currentList.isNullOrEmpty() && showError) {
                    Toast.makeText(
                        this@StreamsActivity,
                        getString(R.string.error_streams), Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate Menu
        menuInflater.inflate(R.menu.menu_streams, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_user -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}