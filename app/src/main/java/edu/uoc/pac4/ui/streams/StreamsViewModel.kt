package edu.uoc.pac4.ui.streams

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.data.network.UnauthorizedException
import edu.uoc.pac4.data.streams.Stream
import edu.uoc.pac4.data.streams.StreamsRepository
import kotlinx.coroutines.launch

class StreamsViewModel(
    private val streamsRepository: StreamsRepository
) : ViewModel(){
    private val TAG = "StreamsViewModel"

    var nextCursor: String? = null
    val streams = MutableLiveData<List<Stream>>()
    //val streamsResponse = MutableLiveData<StreamsResponse?>()

    @Throws(UnauthorizedException::class)
    fun getStreams(){
//        viewModelScope.launch {
//            streamsResponse.postValue(streamsRepository.getStreams(cursor))
//        }
        viewModelScope.launch {
            streamsRepository.getStreams(nextCursor)?.let { response ->
                val newStreams = response.data.orEmpty()
                val currentStreams = mutableListOf<Stream>()
                nextCursor?.let {
                    streams.value?.let { currentStreams.addAll(it) }
                }
                currentStreams.addAll(newStreams)
                streams.value = currentStreams

                nextCursor = response.pagination?.cursor
            }
        }
    }

}