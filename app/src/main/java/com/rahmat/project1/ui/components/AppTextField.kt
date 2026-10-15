package com.rahmat.project1.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahmat.project1.ui.theme.AppDimens

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    readOnly: Boolean = false,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isError: Boolean = false,
    errorText: String? = null
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 1.sp
            )

            if (actionLabel != null && onActionClick != null) {
                Text(
                    text = actionLabel,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onActionClick() }
                )
            }
        }

        Spacer(modifier = Modifier.height(AppDimens.spacing8))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .scale(1f, 0.9f),
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle Visibility",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else null,
            visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            readOnly = readOnly,
            singleLine = true,
            isError = isError,
            shape = RoundedCornerShape(AppDimens.radiusButton),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                errorTrailingIconColor = MaterialTheme.colorScheme.error
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            )
        )

        if (isError && errorText != null) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                fontSize = 10.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}