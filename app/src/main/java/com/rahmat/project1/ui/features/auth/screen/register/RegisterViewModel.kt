package com.rahmat.project1.ui.features.auth.screen.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahmat.project1.R
import com.rahmat.project1.data.usecase.RegisterUseCase
import com.rahmat.project1.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _registerState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val registerState: StateFlow<UiState<Unit>> = _registerState.asStateFlow()

    fun register(companyName: String, ownerName: String, email: String, password: String, confirmPassword: String) {
        val cleanEmail = email.trim()
        val cleanCompanyName = companyName.trim()
        val cleanOwnerName = ownerName.trim()

        if (cleanCompanyName.isBlank() || cleanOwnerName.isBlank() || cleanEmail.isBlank() || password.isBlank()) {
            _registerState.value = UiState.Error(context.getString(R.string.error_all_fields_required))
            return
        }

        if (password != confirmPassword) {
            _registerState.value = UiState.Error(context.getString(R.string.error_password_mismatch))
            return
        }

        viewModelScope.launch {
            _registerState.value = UiState.Loading
            try {
                registerUseCase(cleanCompanyName, cleanOwnerName, cleanEmail, password)
                _registerState.value = UiState.Success(Unit)
            } catch (e: Throwable) {
                if (e is kotlinx.coroutines.CancellationException) throw e

                val msg = e.message ?: ""
                val errorMessage = when {
                    msg.contains("user_already_exists", ignoreCase = true) ->
                        context.getString(R.string.error_email_already_registered)

                    msg.contains("email_address_invalid", ignoreCase = true) ->
                        context.getString(R.string.error_email_invalid_format_detail)

                    msg.contains("network", ignoreCase = true) || 
                    msg.contains("timeout", ignoreCase = true) ||
                    msg.contains("host", ignoreCase = true) ||
                    msg.contains("connect", ignoreCase = true) ||
                    msg.contains("unknownhost", ignoreCase = true) ->
                        context.getString(R.string.error_network_issue)

                    msg.isNotBlank() -> msg

                    else -> context.getString(R.string.error_register_failed)
                }

                _registerState.value = UiState.Error(errorMessage)
            }
        }
    }

    fun resetState() {
        _registerState.value = UiState.Idle
    }
}
