package com.rahmat.project1.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rahmat.project1.R
import com.rahmat.project1.ui.theme.AppDimens

@Composable
fun TermsAndConditionsDialog(
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f),
            shape = RoundedCornerShape(AppDimens.radiusCard),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(AppDimens.spacing24)
            ) {
                Text(
                    text = stringResource(R.string.terms_and_conditions_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(AppDimens.spacing16))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = stringResource(R.string.terms_intro),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing8))

                    TermsSection(
                        title = stringResource(R.string.terms_section_1_title),
                        content = stringResource(R.string.terms_section_1_content)
                    )

                    TermsSection(
                        title = stringResource(R.string.terms_section_2_title),
                        content = stringResource(R.string.terms_section_2_content)
                    )

                    TermsSection(
                        title = stringResource(R.string.terms_section_3_title),
                        content = stringResource(R.string.terms_section_3_content)
                    )

                    TermsSection(
                        title = stringResource(R.string.terms_section_4_title),
                        content = stringResource(R.string.terms_section_4_content)
                    )

                    Spacer(modifier = Modifier.height(AppDimens.spacing16))
                }

                Spacer(modifier = Modifier.height(AppDimens.spacing16))

                Button(
                    onClick = {
                        onAccept()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppDimens.radiusButton)
                ) {
                    Text(stringResource(R.string.terms_accept_button))
                }
            }
        }
    }
}

@Composable
private fun TermsSection(title: String, content: String) {
    Column(modifier = Modifier.padding(vertical = AppDimens.spacing8)) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = content,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
