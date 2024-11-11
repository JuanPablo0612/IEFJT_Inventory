package com.juanpablo0612.iefjt.ui.add_status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.juanpablo0612.iefjt.R
import com.juanpablo0612.iefjt.data.exceptions.NetworkException
import com.juanpablo0612.iefjt.ui.components.ErrorCard

@Composable
fun AddStatusScreen(
    viewModel: AddStatusViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState = viewModel.uiState
    val controller = rememberColorPickerController()

    LaunchedEffect(uiState.successAddStatus) {
        if (uiState.successAddStatus) navigateBack()
    }

    Scaffold(
        topBar = {
            AddStatusTopBar(navigateBack = navigateBack)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(innerPadding)
        ) {
            uiState.exception?.let {
                if (it is NetworkException) {
                    ErrorCard(
                        title = stringResource(R.string.error_network_title),
                        message = stringResource(R.string.error_network_message)
                    )
                } else {
                    ErrorCard(
                        title = stringResource(R.string.error_unknown_title),
                        message = stringResource(R.string.error_unknown_message)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            TextField(
                value = uiState.name,
                onValueChange = viewModel::onNameInput,
                label = {
                    Text(text = stringResource(R.string.status_name_label))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Title,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                isError = !uiState.nameValid,
                supportingText = if (!uiState.nameValid) {
                    { Text(text = stringResource(R.string.status_name_error)) }
                } else {
                    null
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.status_color_label),
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                HsvColorPicker(
                    modifier = Modifier.size(200.dp),
                    controller = controller,
                    onColorChanged = { colorEnvelope ->
                        val colorHex = colorEnvelope.hexCode
                        viewModel.onColorHexChange(colorHex)
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(android.graphics.Color.parseColor("#${uiState.colorHex}")))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.onSaveStatus()
                },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(text = stringResource(R.string.save_button_text))
                }
            }
        }
    }
}