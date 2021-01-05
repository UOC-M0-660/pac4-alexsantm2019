package edu.uoc.pac4.data.oauth

import android.util.Log
import edu.uoc.pac4.data.SessionManager

/**
 * Created by alex on 11/21/20.
 */
class OAuthAuthenticationRepository(
    // TODO: Add any datasources you may need
    private val tokensDataSource: TokensDataSource,
    private val sessionManager: SessionManager
) : AuthenticationRepository {

    private val TAG = "OAuthAuthenticationRepository"

    override suspend fun isUserAvailable(): Boolean {
        return sessionManager.isUserAvailable()
    }

    override suspend fun login(authorizationCode: String): Boolean {
        tokensDataSource.getTokens(authorizationCode)?.let { response ->
            Log.d(TAG, "Got Access token ${response.accessToken}")

            // Save access token and refresh token using the SessionManager class
            sessionManager.saveAccessToken(response.accessToken)
            response.refreshToken?.let {
                sessionManager.saveRefreshToken(it)
            }
            return true
        } ?: run {
            return false
        }
    }

    override suspend fun logout() {
        sessionManager.clearAccessToken()
        sessionManager.clearRefreshToken()
    }
}