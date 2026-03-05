package com.ango.kingburguer.kingburguer.core.di

import com.ango.kingburguer.kingburguer.core.api.KingBurguerService
import com.ango.kingburguer.kingburguer.core.data.KingBurguerLocalStore
import com.ango.kingburguer.kingburguer.main.data.KingBurguerLocalDataSource

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //SingletonComponent -> voce quer uma instancia unica do app (Retrofit, Room, DataStore, Repository)
    // ActivityComponent -> algo acontece na activity (viva)classes de permisoes como utilizar uma camera
    //ActivityRetainedComponent -> Sobrevive a mudanca de orientacao ciclo da activity
    //ViewModelComponent -> A dependecia e especifica de um  viewModel (Use Case)
    //FragmentComponent -> Fragment
    //ViewComponent -> Custom View
    //ServiceComponent -> Service Notificacoes





/*

    @Provides
    @Singleton
    fun providesLocalStorage(@ApplicationContext context: Context): KingBurguerLocalStore {
        return KingBurguerLocalStore(context)
    }

 */




//    const val BASE_URL = "https://hades.tiagoaguiar.co/kingburguer/"

    const  val BASE_URL = "https://hades.tiagoaguiar.co/kingburguer/"

//Fornece uma instancia unica do app utilzei o singleton
    @Provides // FORNECER
    @Singleton
    fun provideService(): KingBurguerService {
        //return KingBurguerService.create()



            val  logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY

            }

            val clientOK = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            //para data
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientOK)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(KingBurguerService::class.java)









        }
















    //criar uma instancia unica do app utilzei o singleton   com isto nao vai multiplicar o repositorio
    // e nuca sera destruida , devem ficar vivo durante toda a aplicacao
/*
    @Provides
    @Singleton
    fun providesRepository(service: KingBurguerService, localStorage: KingBurguerLocalStore): KingBurguerRepository {
        return KingBurguerRepository(service, localStorage)


    }

 */


@Provides
@Singleton
fun providesLocalDataSource(localStore: KingBurguerLocalStore): KingBurguerLocalDataSource {
    return KingBurguerLocalDataSource(localStore)
}





}



