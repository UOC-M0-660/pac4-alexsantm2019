package edu.uoc.pac4.ui.streams

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.data.network.UnauthorizedException
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.streams.Stream
import edu.uoc.pac4.data.streams.StreamsRepository
import kotlinx.coroutines.launch

class StreamsViewModel(
    private val streamsRepository: StreamsRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    // Live Data
    val streamList = MutableLiveData<List<Stream>>()
    val isRefreshing = MutableLiveData<Boolean>()
    val showError = MutableLiveData<Boolean>()
    val isLoggedOut = MutableLiveData<Boolean>()

    // Cursor
    private var cursor: String? = null

    // Retrieves streams from the repository
    fun getStreams() {

        // Posts Loading Status
        isRefreshing.postValue(true)

        // Get Twitch Streams
        viewModelScope.launch {
            try {
                // Gets streams from repository
                val streamsPair = streamsRepository.getStreams(cursor)
                val streams = streamsPair.second
                if (streams.isNotEmpty()) {

                    // Posts Streams received
                    streamList.postValue(streams)

                    // Save cursor for next request
                    cursor = streamsPair.first
                }
                else {
                    // Post error value
                    showError.postValue(true)
                }

                // Posts Loading Status
                isRefreshing.postValue(false)

            } catch (t: UnauthorizedException) {
                // Clear local access token
                viewModelScope.launch { authenticationRepository.logout() }
                // Posts logged out value
                isLoggedOut.postValue(true)
            }
        }
    }

    // Returns true if cursor is null
    // Used in scroll listener
    fun isCursorNull(): Boolean {
        return cursor == null
    }
}
