package com.rahmat.project1.ui.features.auth.screen.register

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import com.rahmat.project1.ui.components.AppSuccessDialog
import com.rahmat.project1.ui.components.AppTextField
import com.rahmat.project1.ui.components.MessageType
import com.rahmat.project1.ui.components.TermsAndConditionsDialog
import com.rahmat.project1.ui.theme.AppDimens

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var companyName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isTermsAccepted by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Validation States
    var companyNameError by remember { mutableStateOf<String?>(null) }
    var ownerNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    val registerState by viewModel.registerState.collectAsState()
    val isOnline = LocalNetworkStatus.current

    // Error string resources
    val companyNameErrorMsg = stringResource(R.string.error_company_name_empty)
    val ownerNameErrorMsg = stringResource(R.string.error_owner_name_empty)
    val emailEmptyErrorMsg = stringResource(R.string.error_email_empty)
    val emailInvalidErrorMsg = stringResource(R.string.error_email_invalid_format)
    val passwordMinLengthErrorMsg = stringResource(R.string.error_password_min_length)
    val passwordMismatchErrorMsg = stringResource(R.string.error_password_mismatch)

    LaunchedEffect(registerState) {
        when (registerState) {
            is UiState.Success -> {
                showSuccessDialog = true
            }
            is UiState.Error -> {
                Log.d("Error When Register", "RegisterScreen: ${(registerState as UiState.Error).message}")
            }
            else -> {}
        }
    }

    if (showTermsDialog) {
        TermsAndConditionsDialog(
            onDismiss = { showTermsDialog = false },
            onAccept = { isTermsAccepted = true }
        )
    }

    if (showSuccessDialog) {
        AppSuccessDialog(
            title = stringResource(R.string.register_success_title),
            message = stringResource(R.string.register_success_message),
            buttonText = stringResource(R.string.register_success_button),
            onConfirm = {
                showSuccessDialog = false
                viewModel.resetState()
                onRegisterSuccess()
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopEnd)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(AppDimens.radiusFull)
                )
                .blur(60.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = AppDimens.screenPaddingHorizontal,
                    vertical = AppDimens.screenPaddingVertical
                ),
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
                        subtitle = stringResource(R.string.register_subtitle)
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing4))

                    Text(
                        text = stringResource(R.string.register_description),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing24))

                    AppTextField(
                        value = companyName,
                        onValueChange = {
                            companyName = it
                            companyNameError = if (it.isBlank()) companyNameErrorMsg else null
                        },
                        label = stringResource(R.string.company_name_label),
                        placeholder = stringResource(R.string.company_name_placeholder),
                        leadingIcon = Icons.Default.Business,
                        isError = companyNameError != null,
                        errorText = companyNameError
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing16))

                    AppTextField(
                        value = ownerName,
                        onValueChange = {
                            ownerName = it
                            ownerNameError = if (it.isBlank()) ownerNameErrorMsg else null
                        },
                        label = stringResource(R.string.owner_name_label),
                        placeholder = stringResource(R.string.owner_name_placeholder),
                        leadingIcon = Icons.Default.Person,
                        isError = ownerNameError != null,
                        errorText = ownerNameError
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing16))

                    AppTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = if (it.isBlank()) {
                                emailEmptyErrorMsg
                            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                                emailInvalidErrorMsg
                            } else {
                                null
                            }
                        },
                        label = stringResource(R.string.company_email_label),
                        placeholder = stringResource(R.string.register_email_placeholder),
                        leadingIcon = Icons.Default.Mail,
                        keyboardType = KeyboardType.Email,
                        isError = emailError != null,
                        errorText = emailError
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing16))

                    AppTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = if (it.length < 6) passwordMinLengthErrorMsg else null
                        },
                        label = stringResource(R.string.register_password_label),
                        placeholder = stringResource(R.string.password_placeholder),
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true,
                        keyboardType = KeyboardType.Password,
                        isError = passwordError != null,
                        errorText = passwordError
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing16))

                    AppTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            confirmPasswordError = if (it != password) passwordMismatchErrorMsg else null
                        },
                        label = stringResource(R.string.confirm_password_label),
                        placeholder = stringResource(R.string.password_placeholder),
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        isError = confirmPasswordError != null,
                        errorText = confirmPasswordError
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing16))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isTermsAccepted,
                            onCheckedChange = { isTermsAccepted = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary,
                                uncheckedColor = MaterialTheme.colorScheme.outline
                            )
                        )
                        Row(
                            modifier = Modifier.clickable { isTermsAccepted = !isTermsAccepted }
                        ) {
                            Text(
                                text = stringResource(R.string.terms_agree_prefix),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = stringResource(R.string.terms_and_conditions),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.clickable {
                                    showTermsDialog = true
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(AppDimens.spacing24))

                    AppMessageBanner(
                        message = if (registerState is UiState.Error) (registerState as UiState.Error).message else null,
                        type = MessageType.ERROR,
                        modifier = Modifier.padding(bottom = AppDimens.spacing16)
                    )

                    AppButton(
                        text = stringResource(R.string.register_button),
                        isLoading = registerState is UiState.Loading,
                        enabled = isOnline && isTermsAccepted,
                        onClick = {
                            // Final validation check
                            companyNameError = if (companyName.isBlank()) companyNameErrorMsg else null
                            ownerNameError = if (ownerName.isBlank()) ownerNameErrorMsg else null
                            emailError = if (email.isBlank()) {
                                emailEmptyErrorMsg
                            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                emailInvalidErrorMsg
                            } else {
                                null
                            }
                            passwordError = if (password.length < 6) passwordMinLengthErrorMsg else null
                            confirmPasswordError = if (confirmPassword != password) passwordMismatchErrorMsg else null

                            if (companyNameError == null && ownerNameError == null && emailError == null && 
                                passwordError == null && confirmPasswordError == null) {
                                viewModel.register(companyName, ownerName, email, password, confirmPassword)
                            }
                        },
                        trailingIcon = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.spacing24))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.register_already_have_account_prefix),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = stringResource(R.string.register_login_link),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onLoginClick() }
                )
            }
        }
    }
}
