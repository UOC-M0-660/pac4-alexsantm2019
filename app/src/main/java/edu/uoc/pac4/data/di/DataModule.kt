package edu.uoc.pac4.data.di

import edu.uoc.pac4.data.SessionManager
import edu.uoc.pac4.data.network.Network
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.oauth.OAuthAuthenticationRepository
import edu.uoc.pac4.data.oauth.TokensDataSource
import edu.uoc.pac4.data.streams.StreamsDataSource
import edu.uoc.pac4.data.streams.StreamsRepository
import edu.uoc.pac4.data.streams.TwitchStreamsRepository
import edu.uoc.pac4.data.user.TwitchUserRepository
import edu.uoc.pac4.data.user.UserDataSource
import edu.uoc.pac4.data.user.UserRepository
import org.koin.dsl.module

/**
 * Created by alex on 11/21/20.
 */

val dataModule = module {
    // TODO: Init your Data Dependencies

    // Streams example
    // single<StreamsRepository> { TwitchStreamsRepository() }

    // Data Sources
    single { SessionManager(get()) }
    single { TokensDataSource(Network.createHttpClient(get())) }
    single { StreamsDataSource(Network.createHttpClient(get())) }
    single { UserDataSource(Network.createHttpClient(get())) }

    // Repositories
    single<AuthenticationRepository>{ OAuthAuthenticationRepository(get(), get()) }
    single<StreamsRepository> { TwitchStreamsRepository(get()) }
    single<UserRepository> { TwitchUserRepository(get()) }
}