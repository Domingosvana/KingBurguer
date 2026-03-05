package com.ango.kingburguer.kingburguer.main.data

import com.ango.kingburguer.kingburguer.core.data.KingBurguerLocalStore
import com.ango.kingburguer.kingburguer.core.data.UserCredentisls

class KingBurguerLocalDataSource(
    private val localStore: KingBurguerLocalStore
) {

    //da memoria que guarda a varial
    private var userSession: UserCredentisls? = null

    suspend fun fetchInittialUserCredentials(): UserCredentisls {
            return userSession ?: localStore.fetchInittialUserCredentials()
    }


    suspend fun updateUserCredential(userCredentisls: UserCredentisls, isPermanent: Boolean) {
        if (isPermanent) {

            localStore.updateUserCredential(userCredentisls)
        }else{
            userSession = userCredentisls

        }


    }



}