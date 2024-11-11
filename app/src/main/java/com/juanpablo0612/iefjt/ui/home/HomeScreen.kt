package com.juanpablo0612.iefjt.ui.home

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.juanpablo0612.iefjt.R
import com.juanpablo0612.iefjt.ui.MainViewModel
import com.juanpablo0612.iefjt.ui.components.CommonCard
import com.juanpablo0612.iefjt.ui.components.StatusCard

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToAddBrand: () -> Unit,
    navigateToAddType: () -> Unit,
    navigateToAddStatus: () -> Unit,
    navigateToElementDetail: (String) -> Unit,
    navigateToElementList: (brandId: String?, typeId: String?, statusId: String?) -> Unit
) {
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            HomeTopBar(
                onLogout = {
                    mainViewModel.onLogout()
                    navigateToLogin()
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.navigationBars)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.home_last_month_updated_elements_title),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.lastMonthUpdatedElements.isEmpty()) {
                Text(
                    text = stringResource(R.string.home_last_month_updated_elements_empty),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.lastMonthUpdatedElements) { element ->
                        CommonCard(
                            imageUrl = element.brand.imageUrl,
                            name = element.name,
                            modifier = Modifier.width(150.dp),
                            onClick = { navigateToElementDetail(element.id) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.home_message),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.registered_brands_title),
                    style = MaterialTheme.typography.titleLarge
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

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.brands.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.registered_brand_title),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = navigateToAddBrand,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.home_add_brand_button_text))
                }
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.brands, key = { it.id }) { brand ->
                        CommonCard(
                            imageUrl = brand.imageUrl,
                            name = brand.name,
                            modifier = Modifier.width(150.dp),
                            onClick = { navigateToElementList(brand.id, null, null) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.registered_types_title),
                    style = MaterialTheme.typography.titleLarge
                )

                if (uiState.types.isNotEmpty()) {
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

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.types.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.registered_types_empty),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = navigateToAddType,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.home_add_type_button_text))
                }
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.types, key = { it.id }) { type ->
                        CommonCard(
                            imageUrl = type.imageUrl,
                            name = type.name,
                            modifier = Modifier.width(150.dp),
                            onClick = { navigateToElementList(null, type.id, null) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.registered_statuses_title),
                    style = MaterialTheme.typography.titleLarge
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

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.statuses.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.registered_statuses_empty),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = navigateToAddStatus,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.home_add_statuses_button_text))
                }
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.statuses, key = { it.id }) { status ->
                    StatusCard(
                        status = status,
                        modifier = Modifier.width(150.dp),
                        onClick = { navigateToElementList(null, null, status.id) }
                    )
                }
            }
        }
    }
}