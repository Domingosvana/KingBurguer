package com.ango.kingburguer.kingburguer.auth.presentation.signup

//import android.net.http.HttpException

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ango.kingburguer.kingburguer.auth.data.AuthRepositoryImpl
import com.ango.kingburguer.kingburguer.core.validation.BirthdayValidator

import com.ango.kingburguer.kingburguer.core.validation.ConfirmPasswordValidator
import com.ango.kingburguer.kingburguer.core.validation.DocumentValidator
import com.ango.kingburguer.kingburguer.core.validation.EmailValidator
import com.ango.kingburguer.kingburguer.core.validation.NameValidator
import com.ango.kingburguer.kingburguer.core.validation.PasswordValidator
import com.ango.kingburguer.kingburguer.core.data.ApiResult
import com.ango.kingburguer.kingburguer.auth.data.UserRequest
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl

): ViewModel() {


    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    var formState by mutableStateOf(FormState())

    private val emailValidator = EmailValidator()

    private val passwordValidator = PasswordValidator()

    private val confirmPasswordValidator = ConfirmPasswordValidator()

    private val nameValidator = NameValidator()

    private val documentValidator = DocumentValidator()

    private val birthdayValidator = BirthdayValidator()


    //var email by mutableStateOf("")
    //  private  set
    //  var password by mutableStateOf("")
    //      private  set
//


    //var name by mutableStateOf("")
    //  private  set
    // var comfirmPassword by mutableStateOf("")
    //   private  set


    //  var document by mutableStateOf("")
    //    private  set
    // var birthday by mutableStateOf("")
    //   private  set


    fun reset() {
        _uiState.update { SignUpUiState() }


    }




    fun updateName(newName: String) {
        val textString = nameValidator.validate(newName)
        formState = formState.copy(
            name = FieldState(field = newName, error = textString,isValid =textString == null)
        )


        updateButton()


        //  if (newName.isBlank()) {
        /*
        formState = formState.copy(
            name = FieldState(field = newName,error = "Campo nome nao pode ser vazio")
        )
        return
    }

    if(newName.length < 3){
        formState = formState.copy(
            name = FieldState(field = newName,error = "Campo nome deve ter no minimo 3 caracteres")
        )
        return
    }


    formState = formState.copy(
        name = FieldState(field = newName,error = null)
    )
    return


    */
        //  }
    }















    fun updateEmail(newEmail: String) {
        val textString = emailValidator.validate(newEmail)
        formState = formState.copy(
            email = FieldState(field = newEmail, error = textString,isValid = textString == null)
        )
        updateButton()

      }




    fun updatePassword(newPassword: String) {
        var textString = passwordValidator.validate(formState.confirmPassword.field, newPassword)
        formState = formState.copy(
            password = FieldState(field = newPassword, error = textString, isValid = textString == null)
        )
        textString = confirmPasswordValidator.validate(newPassword, formState.confirmPassword.field)
        formState = formState.copy(
            confirmPassword = FieldState(field = formState.confirmPassword.field, error = textString, isValid = textString == null)
        )

        updateButton()
    }

    fun updateComfirmPassword(confirmPassword: String) {
        var textString =
            confirmPasswordValidator.validate(formState.password.field, confirmPassword)
        formState = formState.copy(
            confirmPassword = FieldState(field = confirmPassword, error = textString, isValid = textString == null)
        )
        textString = passwordValidator.validate(confirmPassword, formState.password.field)
        formState = formState.copy(
            password = FieldState(field = formState.password.field, error = textString, isValid = textString == null)
        )

        updateButton()
    }


    /*



    fun updatePassword(newPassword: String) {
        var textString = passwordValidator.validate(formState.confirmPassword.field, newPassword)
               formState = formState.copy(
                password = FieldState(field = newPassword, error =textString,isValid = textString == null)
            )

        textString = comfirmPasswordValidator.validate (newPassword, formState.confirmPassword.field)
        formState = formState.copy(
            confirmPassword = FieldState(field = formState.confirmPassword.field, error = textString, isValid = textString == null)
        )
        updateButton()

        /*else if(newPassword.length < 8){
            formState = formState.copy(
                password = FieldState(field = newPassword,error = "Campo senha deve ter no minimo 6 caracteres")
            )
            return
        }
*/


    }


    fun updateComfirmPassword(newComfirmPassword: String) {
        var textString = comfirmPasswordValidator.validate(formState.confirmPassword.field, newComfirmPassword)

            formState = formState.copy(
                confirmPassword = FieldState(field = newComfirmPassword, error = textString, isValid = textString ==null )
            )
        textString = passwordValidator.validate(newComfirmPassword,formState.password.field )
        formState = formState.copy(
            password = FieldState(field = formState.password.field, error = textString, isValid = textString == null)
        )

        updateButton()



        /*
            formState = formState.copy(
                confirmPassword =FieldState(field=newComfirmPassword,error = "Campo confirmacao de senha nao pode ser vazio")
            )
            return

        }



         if(newComfirmPassword != formState.password.field){
            formState = formState.copy(
                confirmPassword = FieldState(field = newComfirmPassword,error = "Campo confirmacao de senha deve ser igual a senha")
            )
            return



        }
        */


    }

     */




        fun UpdateDocument(newDocument: String) {

          //  val pattern = "###.###.###-##"   //Regex("[0-9]+")
         //   val currentDocument = formState.document.field
           // val result = Mask(pattern, currentDocument, newDocument)

            val textString = documentValidator.validate(formState.document.field,newDocument)

            formState = formState.copy(
                document = FieldState(field =documentValidator.result, error =textString ,isValid =textString == null )
            )
            updateButton()
          //  if (result.isBlank()) {}

                /*
        formState = formState.copy(
                document = FieldState(field = result,error = "Campo CPF nao pode ser vazio")
            )
            return
        }

        if(result.length != pattern.length){
            formState = formState.copy(
                document = FieldState(field = result,error ="CPF invalido")
            )
            return


           */

            }














        fun updateBirthday(newBirthday: String) {
           // val pattern = "##/##/####"   //Regex("[0-9]+")
           // val currentBirthday = formState.birthday.field
           // val result = Mask(pattern, currentBirthday, newBirthday)

            val textString =   birthdayValidator.validate(formState.birthday.field,newBirthday)
            formState = formState.copy(
                birthday = FieldState(field = birthdayValidator.result, error = textString, isValid = textString == null)
            )
            updateButton()
            //o numero precisa igual da mascara = invalida
         //   if (result.length != pattern.length) {

                /*
            formState = formState.copy(
                birthday = FieldState(field = result,error ="Data de nascimento invalida")
            )
            return
        }


        if(result.isBlank()){
            formState = formState.copy(
                birthday = FieldState(field = result,error = "Campo  data de nascimento nao pode ser vazio")
            )
            return
        }




        //validar a data = 30/20/2000
      //  val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // esta funcao quer dizer se usuario enserir data errada
        // ele vai tentar enserir uma data proxima
      ///  sdf.isLenient = false
        //val dataFinal = sdf.parse(result)

        try{
           /* val date =*/ SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).run{
                isLenient = false
                parse(result)
            }?.also{
                // validar a data futura
                val now = Date()
                if(it.after(now)){
                    formState = formState.copy(
                        birthday = FieldState(field = result,error ="Data de nascimento  nao pode ser maior que a data atual")
                    )
                    return
                }

            }
            //val now = Date()
        //    if(date != null && date.after(now) ){}

            formState = formState.copy(
                birthday = FieldState(field = result,error = null)
            )
        }
        catch(e: ParseException){
            //invalida = 30/02/2000
            formState = formState.copy(
                birthday = FieldState(field = result,error ="Data de nascimento invalida")
            )

         */
       //     }



    }




        fun send() {
            _uiState.update { it.copy(isLoading = true) }
            //simulacao de latencia de rede / atraso de chama

            viewModelScope.launch() {
               // delay(300L)

                //val service = KingBurguerService.create()
               // val response = service.getTest()

                //Log.i("TAG", "Response status: ${response.code()}")
                //Log.i("TAG", "Response body: ${response.body()}")
              //  Log.i("TAG", "Response message: ${response.message()}")
                //Log.i("TAG", "Response error body: ${response.errorBody()}")
                //Log.i("TAG", "Response raw: ${response.raw()}")
              //  Log.i("TAG", "Response isSuccessful: ${response.isSuccessful}")
               // Log.i("TAG", "Response isExecuted: ${response.isExecuted}")




                    with(formState) {

                        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            .parse(birthday.field)

                        val dateFormatted = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            .format(date!!)

                        //document precisa ser enviado sem pontos
                        val documentFormatted = document.field.filter { it.isDigit() }


                          //  isLenient = false
                            //parse(birthday.field)


                        val userRequest = UserRequest(
                            //susbstituimos o formState por with para ser utlizado uma vez
                            //  name = formState.name.field,
                            //email = formState.email.field,
                            //password = formState.password.field

                             name = name.field,
                            email = email.field,
                            password = password.field
                            ,confirmPassword = confirmPassword.field,
                            document = documentFormatted,
                            birthday = dateFormatted
//kayengacampos@gmail.com
                        )
                      //  val service = KingBurguerService.create()
                       // val content = service.postUser(userRequest)
                       // Log.e("TAG", "Response body: ${content}")


                        val result = repository.postUser(userRequest)
                        Log.e("TAG", "result is: ${result}")



                        when(result){
                            is ApiResult.Error -> {
                                _uiState.update { it.copy(isLoading = false, goToLogin = false, error = result.message) }

                            }
                          //  is ApiResult.Error -> {
                              //  _uiState.update { it.copy(isLoading = false, goToLogin = false, error = result.message) }

                            //}
                            is ApiResult.Success -> {
                                _uiState.update { it.copy(isLoading = false, goToLogin = true, error = null) }

                            }
                        }



                    }
              //  _uiState.update { it.copy(isLoading = false, goToHome = false, error ="erro de conexao") }









                //    _uiState.update { it.copy(isLoading = false, goToHome = false, error ="erro de conexao") }
             //   _uiState.update { it.copy(isLoading = false, goToHome = true) }
            }


        }


/*

       // gerenciamento de independecia manul

        companion object{
            val factory = viewModelFactory {
                initializer{
                    val application  = this[APPLICATION_KEY]!!.applicationContext
                    val localStore = KingBurguerLocalStore(application)
                 //   val service = KingBurguerService.create()


                    val service = KingBurguerService.create()
                    val repository = KingBurguerRepository(service, localStore)
                    SignUpViewModel(repository)
                }
            }
        }

 */







    private fun updateButton(){

        val formIsValid = with(formState){
            email.isValid &&
            name.isValid &&
            password.isValid &&
            confirmPassword.isValid &&
            document.isValid &&
            birthday.isValid
        }
        formState = formState.copy(
            formIsValid = formIsValid
        )

    }



        //  private fun isEmailValid(email:String):Boolean{
        //    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        // }


    }



/*
fun updateEmail(newEmail:String){

        if(newEmail.isBlank()){
            formState = formState.copy(
                email = FieldState(field = newEmail,error = "Campo email nao pode ser vazio")
            )
            return
        }

        if(!isEmailValid(newEmail)){
            formState = formState.copy(
                email = FieldState(field = newEmail,error = "Email invalido. Verifica o campo novamente")
            )
            return
        }


        formState = formState.copy(
            email = FieldState(field = newEmail,error = null)
        )
        return
    }

 */

/*
 try{
                //  val service = KingBurguerService.create()
                 //   val body = service.getTest()

                    with(formState) {

                        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            .parse(birthday.field)

                        val dateFormatted = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            .format(date!!)


                          //  isLenient = false
                            //parse(birthday.field)


                        val userRequest = UserRequest(
                            //susbstituimos o formState por with para ser utlizado uma vez
                            //  name = formState.name.field,
                            //email = formState.email.field,
                            //password = formState.password.field
                             name = name.field,
                            email = email.field,
                            password = password.field
                            ,confirmPassword = confirmPassword.field,
                            document = document.field,
                            birthday = dateFormatted
//kayengacampos@gmail.com
                        )
                        val service = KingBurguerService.create()
                        val content = service.postUser(userRequest)
                        Log.e("TAG", "Response body: ${content}")

                    }




                } catch(e: HttpException){
                    Log.e("TAG", "Response status: ${e.code()}")
               //     Log.i("TAG", "Response body: ${e.response()?.errorBody()?:null}")
                    //  Log.i("TAG", "Response message: ${response.message()}")
                   // Log.i("TAG", "Response error body: ${e.response()?.errorBody()?.string()}")
                    val content = e.response()?.errorBody()?.string()


                    //Log.i("TAG", "Response raw: ${response.raw()}")
                   // Log.i("TAG", "Response isSuccessful: ${e.response()?.isSuccessful}")

                    Log.e("TAG", "Response error body: ${content}")
                    _uiState.update { it.copy(isLoading = false, goToHome = false, error = content) }

                }

 */