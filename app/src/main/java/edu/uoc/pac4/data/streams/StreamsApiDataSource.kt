package edu.uoc.pac4.data.streams

import edu.uoc.pac4.data.network.Endpoints
import edu.uoc.pac4.data.network.UnauthorizedException
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*

class StreamsDataSource(private val httpClient: HttpClient) {
    private val TAG = "StreamsDataSource"

    @Throws(UnauthorizedException::class)
    suspend fun getStreams(cursor: String? = null): StreamsResponse? {
        try {
            val response = httpClient
                .get<StreamsResponse>(Endpoints.streamsUrl) {
                    cursor?.let { parameter("after", it) }
                }
            return response
        } catch (t: Throwable) {
            // Try to handle error
            return when (t) {
                is ClientRequestException -> {
                    // Check if it's a 401 Unauthorized
                    if (t.response?.status?.value == 401) {
                        throw UnauthorizedException
                    }
                    null
                }
                else -> null
            }
        }
    }
}