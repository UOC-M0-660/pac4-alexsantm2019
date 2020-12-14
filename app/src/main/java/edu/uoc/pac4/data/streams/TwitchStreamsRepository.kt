package edu.uoc.pac4.data.streams

/**
 * Created by alex on 11/21/20.
 */

class TwitchStreamsRepository(
    // TODO: Add any datasources you may need
    private val streamsDataSource: StreamsDataSource
) : StreamsRepository {

    override suspend fun getStreams(cursor: String?): StreamsResponse? {
        return streamsDataSource.getStreams(cursor)
    }

}