package com.juanpablo0612.iefjt.ui.add_element

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.juanpablo0612.iefjt.R
import com.juanpablo0612.iefjt.data.exceptions.NetworkException
import com.juanpablo0612.iefjt.ui.components.CommonCard
import com.juanpablo0612.iefjt.ui.components.ErrorCard
import com.juanpablo0612.iefjt.ui.components.StatusCard

@Composable
fun AddElementScreen(
    viewModel: AddElementViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToAddBrand: () -> Unit,
    navigateToAddType: () -> Unit,
    navigateToAddStatus: () -> Unit
) {
    val uiState = viewModel.uiState
    val brands = remember(
        uiState.brands,
        uiState.selectedBrand
    ) { uiState.brands.filterNot { it.id == uiState.selectedBrand?.id } }
    val types = remember(
        uiState.types,
        uiState.selectedType
    ) { uiState.types.filterNot { it.id == uiState.selectedType?.id } }
    val statuses = remember(
        uiState.statuses,
        uiState.selectedStatus
    ) { uiState.statuses.filterNot { it.id == uiState.selectedStatus?.id } }

    LaunchedEffect(uiState.successAddElement) {
        if (uiState.successAddElement) navigateBack()
    }

    Scaffold(
        topBar = {
            AddElementTopBar(navigateBack = navigateBack)
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.navigationBars),
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(state = rememberScrollState())
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
                    Text(text = stringResource(R.string.element_name_label))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Title,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                isError = !uiState.nameValid,
                supportingText = if (!uiState.nameValid) {
                    { Text(text = stringResource(R.string.element_name_error)) }
                } else {
                    null
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.serial,
                onValueChange = viewModel::onSerialInput,
                label = {
                    Text(text = stringResource(R.string.element_serial_label))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.location,
                onValueChange = viewModel::onLocationInput,
                label = {
                    Text(text = stringResource(R.string.element_location_label))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AddLocation,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                isError = !uiState.locationValid,
                supportingText = if (!uiState.locationValid) {
                    { Text(text = stringResource(R.string.element_location_error)) }
                } else {
                    null
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.element_brand_label),
                    style = MaterialTheme.typography.titleSmall
                )

                if (uiState.brands.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(8.dp))

                    AssistChip(
                        onClick = navigateToAddBrand,
                        label = { Text(text = stringResource(R.string.add_button_text)) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (uiState.brands.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (uiState.selectedBrand != null) {
                        item {
                            CommonCard(
                                imageUrl = uiState.selectedBrand.imageUrl,
                                name = uiState.selectedBrand.name,
                                selected = true,
                                modifier = Modifier
                                    .width(150.dp)
                                    .animateItem()
                            )
                        }
                    }

                    items(
                        brands,
                        key = { it.id }
                    ) { brand ->
                        CommonCard(
                            imageUrl = brand.imageUrl,
                            name = brand.name,
                            onClick = { viewModel.onBrandSelected(brand) },
                            modifier = Modifier
                                .width(150.dp)
                                .animateItem()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
            } else {
                Text(
                    text = stringResource(R.string.registered_brand_title),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                OutlinedButton(onClick = navigateToAddBrand, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(R.string.add_brand_title))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.element_type_label),
                    style = MaterialTheme.typography.titleSmall
                )

                if (uiState.brands.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(8.dp))

                    AssistChip(
                        onClick = navigateToAddType,
                        label = { Text(text = stringResource(R.string.add_button_text)) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (uiState.types.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (uiState.selectedType != null) {
                        item {
                            CommonCard(
                                imageUrl = uiState.selectedType.imageUrl,
                                name = uiState.selectedType.name,
                                selected = true,
                                modifier = Modifier
                                    .width(150.dp)
                                    .animateItem()
                            )
                        }
                    }

                    items(
                        types,
                        key = { it.id }
                    ) { type ->
                        CommonCard(
                            imageUrl = type.imageUrl,
                            name = type.name,
                            onClick = { viewModel.onTypeSelected(type) },
                            modifier = Modifier
                                .width(150.dp)
                                .animateItem()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
            } else {
                Text(
                    text = stringResource(R.string.registered_types_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                OutlinedButton(onClick = navigateToAddType, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(R.string.add_type_title))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.element_status_label),
                    style = MaterialTheme.typography.titleSmall
                )

                if (uiState.statuses.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(8.dp))

                    AssistChip(
                        onClick = navigateToAddStatus,
                        label = { Text(text = stringResource(R.string.add_button_text)) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (uiState.statuses.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (uiState.selectedStatus != null) {
                        item {
                            StatusCard(
                                status = uiState.selectedStatus,
                                selected = true,
                                modifier = Modifier
                                    .width(150.dp)
                                    .animateItem()
                            )
                        }
                    }

                    items(
                        statuses,
                        key = { it.id }
                    ) { status ->
                        StatusCard(
                            status,
                            onClick = { viewModel.onStatusSelected(status) },
                            modifier = Modifier
                                .width(150.dp)
                                .animateItem()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
            } else {
                Text(
                    text = stringResource(R.string.registered_statuses_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                OutlinedButton(onClick = navigateToAddStatus, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(R.string.add_status_title))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = viewModel::onSaveElement,
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