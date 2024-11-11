package com.juanpablo0612.iefjt.ui.add_brand

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.juanpablo0612.iefjt.R
import com.juanpablo0612.iefjt.data.exceptions.NetworkException
import com.juanpablo0612.iefjt.ui.components.ErrorCard

@Composable
fun AddBrandScreen(
    viewModel: AddBrandViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState = viewModel.uiState
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                viewModel.onImageUriSelected(it)
            }
        }

    LaunchedEffect(key1 = uiState.successAddBrand) {
        if (uiState.successAddBrand) navigateBack()
    }

    Scaffold(
        topBar = {
            AddBrandTopBar(navigateBack = navigateBack)
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

            Text(
                text = stringResource(id = R.string.image_title),
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                InputChip(
                    selected = uiState.uploadImageSelected,
                    onClick = { viewModel.onUploadImageSelectedChange(true) },
                    label = {
                        Text(text = stringResource(id = R.string.image_source_upload))
                    },
                    enabled = !uiState.isLoading
                )

                Spacer(modifier = Modifier.width(8.dp))

                InputChip(
                    selected = !uiState.uploadImageSelected,
                    onClick = { viewModel.onUploadImageSelectedChange(false) },
                    label = {
                        Text(text = stringResource(id = R.string.image_source_url))
                    },
                    enabled = !uiState.isLoading
                )
            }

            AnimatedVisibility(
                visible = uiState.uploadImageSelected,
                enter = slideInHorizontally { height -> -height },
                exit = slideOutHorizontally { height -> -height }
            ) {
                OutlinedCard(
                    onClick = {
                        imagePickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        if (uiState.imageUri != null) {
                            AsyncImage(
                                model = uiState.imageUri,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Text(text = stringResource(R.string.image_source_upload))
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = !uiState.uploadImageSelected,
                enter = slideInHorizontally { height -> height },
                exit = slideOutHorizontally { height -> height }
            ) {
                TextField(
                    value = uiState.imageUrl,
                    onValueChange = viewModel::onImageUrlInput,
                    label = {
                        Text(text = stringResource(R.string.brand_image_url_label))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    isError = !uiState.imageUrlValid,
                    supportingText = if (!uiState.imageUrlValid) {
                        { Text(text = stringResource(R.string.image_url_error)) }
                    } else {
                        null
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = uiState.name,
                onValueChange = viewModel::onNameInput,
                label = {
                    Text(text = stringResource(R.string.brand_name_label))
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
                    { Text(text = stringResource(R.string.brand_name_error)) }
                } else {
                    null
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.onSaveBrand()
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