package com.rahmat.project1.ui.features.auth.screen.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rahmat.project1.R
import com.rahmat.project1.common.network.LocalNetworkStatus
import com.rahmat.project1.ui.common.UiState
import com.rahmat.project1.ui.components.AppButton
import com.rahmat.project1.ui.components.AppLogoHeader
import com.rahmat.project1.ui.components.AppMessageBanner
import com.rahmat.project1.ui.components.AppTextField
import com.rahmat.project1.ui.components.MessageType
import com.rahmat.project1.ui.theme.AppDimens

@Composable
fun LoginScreen(
    onLoginSuccess: (role: String) -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val loginState by viewModel.loginState.collectAsState()
    val isOnline = LocalNetworkStatus.current

    val emptyEmailErrorMsg = stringResource(R.string.error_email_empty)
    val emptyPasswordErrorMsg = stringResource(R.string.error_password_empty)

    LaunchedEffect(loginState) {
        if (loginState is UiState.Success) {
            val role = (loginState as UiState.Success<String>).data
            onLoginSuccess(role)
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.TopStart)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(AppDimens.radiusFull)
                )
                .blur(80.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppDimens.radiusCard),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = AppDimens.spacing4
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppDimens.spacing24),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppLogoHeader(
                        title = stringResource(R.string.login_title),
                        subtitle = stringResource(R.string.login_subtitle)
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing32))

                    AppTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = if (it.isBlank()) emptyEmailErrorMsg else null
                        },
                        label = stringResource(R.string.email_label),
                        placeholder = stringResource(R.string.login_email_placeholder),
                        leadingIcon = Icons.Default.Mail,
                        keyboardType = KeyboardType.Email,
                        isError = emailError != null,
                        errorText = emailError,
                        imeAction = ImeAction.Next
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing16))

                    AppTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = if (it.isBlank()) emptyPasswordErrorMsg else null
                        },
                        label = stringResource(R.string.password_label),
                        placeholder = stringResource(R.string.password_placeholder),
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true,
                        actionLabel = stringResource(R.string.login_forgot_password),
                        onActionClick = onForgotPasswordClick,
                        keyboardType = KeyboardType.Password,
                        isError = passwordError != null,
                        errorText = passwordError,
                        imeAction = ImeAction.Done
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing24))

                    AppMessageBanner(
                        message = if (loginState is UiState.Error) (loginState as UiState.Error).message else null,
                        type = MessageType.ERROR,
                        modifier = Modifier.padding(bottom = AppDimens.spacing16)
                    )
                    if (loginState is UiState.Error) {
                        Log.d("Error Login", "LoginScreen: ${(loginState as UiState.Error).message}")
                    }

                    AppButton(
                        text = stringResource(R.string.login_button),
                        isLoading = loginState is UiState.Loading,
                        enabled = isOnline,
                        onClick = {
                            emailError = if (email.isBlank()) emptyEmailErrorMsg else null
                            passwordError = if (password.isBlank()) emptyPasswordErrorMsg else null

                            if (emailError == null && passwordError == null) {
                                viewModel.login(email, password)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing24))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.login_no_account_prefix),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = stringResource(R.string.login_register_link),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { onRegisterClick() }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.spacing32))

            Text(
                text = "v2.0.0",
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

        }
    }
}
