package edu.uoc.pac4.data.streams

import edu.uoc.pac4.data.network.UnauthorizedException

/**
 * Created by alex on 11/21/20.
 */

class TwitchStreamsRepository(
    // TODO: Add any datasources you may need
    private val streamsDataSource: StreamsDataSource
) : StreamsRepository {

    @Throws(UnauthorizedException::class)
    override suspend fun getStreams(cursor: String?): Pair<String?, List<Stream>> {
        val streamsResponse = streamsDataSource.getStreams(cursor)
        streamsResponse?.let {
            val streamsList = it.data.orEmpty()
            val newCursor = it.pagination?.cursor
            return Pair(newCursor, streamsList)
        }
        return Pair(null, emptyList())
    }

}