package com.ango.kingburguer.kingburguer.core.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//Um mini banco de dados para salvar os dados do usuario
private const val USER_CREDENTIALS_NAME = "user_credentials"
private val Context.dataStore by preferencesDataStore(name = USER_CREDENTIALS_NAME)


class KingBurguerLocalStore @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    //agora precisamos ler os dados

    val userCredentialsFlow = dataStore.data.map {preferences ->
        //val expires = preferences[EXPIRES_TIMESTAMP] ?: 0L
        //val accessToken = preferences[ACCESS_TOKEN] ?: ""
        //val refreshToken = preferences[REFRESH_TOKEN] ?: ""
        //val tokenType = preferences[TOKEN_TYPE] ?: ""
        mapUserCredentials(preferences)
            //accessToken = accessToken,
            //refreshToken = refreshToken,
            //expiresTimestamp = expires,
            //tokenType = tokenType






    }


            // verificar as credencias se sao validas ou nao para ser buscado
            suspend fun fetchInittialUserCredentials(): UserCredentisls {
                val pref =  dataStore.data.first().toPreferences()
                val userCredentials = mapUserCredentials(pref)
                Log.d("TAG", "fetchInitialUserCredentials: $userCredentials")
                return userCredentials
            }




    suspend fun updateUserCredential(userCredentials: UserCredentisls){

        dataStore.edit { preferences ->
            preferences[EXPIRES_TIMESTAMP] = System.currentTimeMillis() + ( userCredentials.expiresTimestamp * 1000L)
            preferences[ACCESS_TOKEN] = userCredentials.accessToken
            preferences[REFRESH_TOKEN] = userCredentials.refreshToken
            preferences[TOKEN_TYPE] = userCredentials.tokenType

        }


    }


    // verificar as credencias se sao validas ou nao para ser buscado
    private fun mapUserCredentials(preferences: Preferences): UserCredentisls {
        val expires = preferences[EXPIRES_TIMESTAMP] ?: 0
        val accessToken = preferences[ACCESS_TOKEN] ?: ""
        val refreshToken = preferences[REFRESH_TOKEN] ?: ""
        val tokenType = preferences[TOKEN_TYPE] ?: ""
        return UserCredentisls(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresTimestamp = expires,
            tokenType = tokenType
        )

    }

    companion object{
        //escrita e leitura
        val EXPIRES_TIMESTAMP = longPreferencesKey("expires_timestamp")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val TOKEN_TYPE = stringPreferencesKey("token_type")



    }


}