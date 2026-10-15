package com.rahmat.project1.ui.features.auth.screen.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahmat.project1.R
import com.rahmat.project1.data.usecase.LoginUseCase
import com.rahmat.project1.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val loginState: StateFlow<UiState<String>> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        val cleanEmail = email.trim()
        val cleanPassword = password.trim()

        if (cleanEmail.isBlank() || cleanPassword.isBlank()) {
            _loginState.value = UiState.Error(context.getString(R.string.error_email_password_empty))
            return
        }

        viewModelScope.launch {
            _loginState.value = UiState.Loading
            try {
                val role = loginUseCase(cleanEmail, cleanPassword)
                _loginState.value = UiState.Success(role)
            } catch (e: Throwable) {
                if (e is kotlinx.coroutines.CancellationException) throw e
                
                val msg = e.message ?: ""
                val errorMessage = when {
                    msg.contains("invalid_credentials", ignoreCase = true) ||
                    msg.contains("invalid login credentials", ignoreCase = true) ||
                    msg.contains("invalid_grant", ignoreCase = true) ||
                    msg.contains("bad credentials", ignoreCase = true) ||
                    msg.contains("user_not_found", ignoreCase = true) ->
                        context.getString(R.string.error_invalid_credentials)

                    msg.contains("email_not_confirmed", ignoreCase = true) ||
                    msg.contains("email not confirmed", ignoreCase = true) ->
                        context.getString(R.string.error_email_not_confirmed)

                    msg.contains("network", ignoreCase = true) ||
                    msg.contains("timeout", ignoreCase = true) ||
                    msg.contains("host", ignoreCase = true) ||
                    msg.contains("connect", ignoreCase = true) ||
                    msg.contains("unknownhost", ignoreCase = true) ->
                        context.getString(R.string.error_network_issue)

                    msg.isNotBlank() -> msg

                    else -> context.getString(R.string.error_server_connection)
                }
                _loginState.value = UiState.Error(errorMessage)
            }
        }
    }

    fun resetState() {
        _loginState.value = UiState.Idle
    }
}
