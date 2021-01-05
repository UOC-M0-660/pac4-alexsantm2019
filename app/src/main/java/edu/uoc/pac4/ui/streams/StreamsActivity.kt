package edu.uoc.pac4.ui.streams

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.uoc.pac4.R
import edu.uoc.pac4.data.SessionManager
import edu.uoc.pac4.data.network.UnauthorizedException
import edu.uoc.pac4.ui.login.LoginActivity
import edu.uoc.pac4.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_streams.*
import org.koin.android.viewmodel.ext.android.viewModel

class StreamsActivity : AppCompatActivity() {

    private val TAG = "StreamsActivity"

    private val adapter = StreamsAdapter()
    private val layoutManager = LinearLayoutManager(this)
    private val streamsViewModel by viewModel<StreamsViewModel>()

    //private val twitchApiService = TwitchApiService(Network.createHttpClient(this))
//    private val streamsRepository by inject<StreamsRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_streams)
        // Init RecyclerView
        initRecyclerView()
        // Swipe to Refresh Listener
        swipeRefreshLayout.setOnRefreshListener {
            getStreams()
        }

        observerStreams()
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
                return streamsViewModel.nextCursor == null
            }

            override fun isLoading(): Boolean {
                return swipeRefreshLayout.isRefreshing
            }
        })
    }

    private fun getStreams() {
        // Show Loading
        swipeRefreshLayout.isRefreshing = true
        try {
            streamsViewModel.getStreams()
            // Hide Loading
            swipeRefreshLayout.isRefreshing = false
        } catch (t: UnauthorizedException) {
            Log.w(TAG, "Unauthorized Error getting streams", t)
            // Clear local access token
            SessionManager(this@StreamsActivity).clearAccessToken()
            // User was logged out, close screen and open login
            finish()
            startActivity(Intent(this@StreamsActivity, LoginActivity::class.java))
        }
    }

    private fun observerStreams(){
        streamsViewModel.streams.observe(this){streams ->
            Log.i(TAG, "observeStreams: "+ streams.size)
            adapter.submitList(streams)
            if (adapter.currentList.isNullOrEmpty()){
                Toast.makeText(
                    this@StreamsActivity,
                    getString(R.string.error_streams), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // region Menu
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
    // endregion

}