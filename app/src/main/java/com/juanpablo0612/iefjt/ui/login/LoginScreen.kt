package com.juanpablo0612.iefjt.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.juanpablo0612.iefjt.R
import com.juanpablo0612.iefjt.data.exceptions.InvalidCredentialsException
import com.juanpablo0612.iefjt.data.exceptions.NetworkException
import com.juanpablo0612.iefjt.ui.components.ErrorCard

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToForgotPassword: () -> Unit
) {
    val uiState = viewModel.uiState
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = uiState.successLogin) {
        if (uiState.successLogin) {
            navigateToHome()
        }
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
//        Image(
//            painter = painterResource(R.drawable.login_bg1),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )
//
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                ) {
                    Text(
                        text = stringResource(R.string.login_welcome),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.login_title),
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    uiState.exception?.let {
                        when (it) {
                            is InvalidCredentialsException -> {
                                ErrorCard(
                                    title = stringResource(R.string.login_error_invalid_credentials_title),
                                    message = stringResource(R.string.login_error_invalid_credentials_message)
                                )
                            }

                            is NetworkException -> {
                                ErrorCard(
                                    title = stringResource(R.string.error_network_title),
                                    message = stringResource(R.string.error_network_message)
                                )
                            }

                            else -> {
                                ErrorCard(
                                    title = stringResource(R.string.error_unknown_title),
                                    message = stringResource(R.string.error_unknown_message)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    TextField(
                        value = uiState.email,
                        onValueChange = viewModel::onEmailInput,
                        label = {
                            Text(text = stringResource(R.string.email_label))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        isError = !uiState.emailValid,
                        supportingText = if (!uiState.emailValid) {
                            { Text(text = stringResource(R.string.email_error)) }
                        } else {
                            null
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    TextField(
                        value = uiState.password,
                        onValueChange = viewModel::onPasswordInput,
                        label = {
                            Text(text = stringResource(R.string.password_label))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Password,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = viewModel::onPasswordVisibilityChange) {
                                if (uiState.passwordVisible) {
                                    Icon(
                                        imageVector = Icons.Default.VisibilityOff,
                                        contentDescription = null
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Visibility,
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = if (uiState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        isError = !uiState.passwordValid,
                        supportingText = if (!uiState.passwordValid) {
                            { Text(text = stringResource(R.string.password_error)) }
                        } else {
                            null
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.onLogin()
                        },
                        enabled = !uiState.isLoading
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator()
                        } else {
                            Text(text = stringResource(R.string.login_button_text))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.login_no_account_text),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        TextButton(
                            onClick = navigateToRegister,
                            enabled = !uiState.isLoading
                        ) {
                            Text(text = stringResource(R.string.login_register_button_text))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = navigateToForgotPassword,
                        enabled = !uiState.isLoading
                    ) {
                        Text(text = stringResource(R.string.login_forgot_password_text))
                    }
                }
            }
        }
    }
}