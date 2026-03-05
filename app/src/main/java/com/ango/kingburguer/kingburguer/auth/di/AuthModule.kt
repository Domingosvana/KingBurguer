package com.ango.kingburguer.kingburguer.auth.di

import com.ango.kingburguer.kingburguer.auth.data.AuthRepositoryImpl
import com.ango.kingburguer.kingburguer.auth.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {


    @Binds
    @Singleton
   abstract fun providesRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

}





/*
Bem observado.
Não fica em domain porque ele é um DTO, Data Transfer Object (Objeto de Transferência de Dados).

Um DTO é um objeto simples, usado pra transportar dados entre camadas do sistema (ex: API, Banco), sem carregar regra de negócio junto. OU seja, é o “formato de dados que vai viajar” no request/response.

Em outros casos, o que poderia ser feito é ter um objeto que fica em "domain" para as regras do app, assim:

domain -> Login
data -> LoginRequest

Só que, como os dados que vamos usar tanto do lado da API (data) quanto do lado do app (domain) são idênticos, porque a API já está com a mesma regra do app, não vejo necessidade de ficar só copiando dados de um objeto para outro.

Veja o cenário com um objeto de domain:


// domain:
data class Login(
    val username: String,
    val password: String
)

data class LoginInfo (
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresSeconds: Double,
)

// data
data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse (
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("expires_seconds")
    val expiresSeconds: Double,
)


Com isso, precisamos converter de Domain -> DTO  e DTO -> Domain.
Sendo que é o mesmo dado.


fun Login.toDto(): LoginRequest(
    username = username,
    password = password
)

fun LoginResponse.toDomain(): LoginInfo(
    accessToken = accessToken,
    refreshToken = refreshToken,
    tokenType = tokenType,
    expiresSeconds = expiresSeconds,
)


E depois passar no data.


// domain
interface AuthRepository {
    suspend fun login(login: Login, keepLogged: Boolean): ApiResult<LoginInfo>
}

// data
class AuthRepositoryImpl(
    private val service: KingBurguerService,
    private val localStorage: KingBurguerLocalStorage
) : AuthRepository {

    override suspend fun login(login: Login, keepLogged: Boolean): ApiResult<LoginInfo> {
        val loginRequest = login.toDto() // <= converte DOMAIN em DTO
        val result = apiCall { service.login(loginRequest) }
        if (result is ApiResult.Success<LoginResponse>) {
            if (keepLogged) {
                updateCredentials(result.data)
            }
        }
        return result.toDomain() // <= converte DTO em DOMAIN
    }

}


Ou outra é esperar os parametros puros como String, Int, etc.


interface AuthRepository {
    suspend fun login(username: String,password: String, keepLogged: Boolean): ApiResult<LoginInfo>
}


Da forma acima, temos o domain isolado (independente), o que está correto sim. Mas como nossos dados são identicos, só iria inflar a arquitetura nesse contexto. Por tanto, optei por usar o DTO direto.
Mas em alguns casos, os devs optam por fazer sempre essa separação, mesmo que seja copie e cole.

Então, vai muito da equipe de como manter essa regra.

Tem vezes que a arquitetura limpa pode inflar muito o projeto e ter muitos arquivos que poderia ser evitados, quebrar uma regra da arquitetura pode te dar velocidade e performance quando fizer sentido como este.
 */