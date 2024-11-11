package com.juanpablo0612.iefjt.ui.update_element_status

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.juanpablo0612.iefjt.R
import com.juanpablo0612.iefjt.data.exceptions.NetworkException
import com.juanpablo0612.iefjt.ui.components.ErrorCard
import com.juanpablo0612.iefjt.ui.components.StatusCard

@Composable
fun UpdateElementStatusScreen(
    viewModel: UpdateElementStatusViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState = viewModel.uiState
    val statuses = remember(
        uiState.statuses,
        uiState.currentStatus,
        uiState.selectedStatus
    ) {
        uiState.statuses
            .filterNot { it.id == uiState.currentStatus?.id }
            .filterNot { it.id == uiState.selectedStatus?.id }
    }

    LaunchedEffect(uiState.successUpdateElementStatus) {
        if (uiState.successUpdateElementStatus) navigateBack()
    }

    Scaffold(
        topBar = {
            UpdateElementStatusTopBar(navigateBack = navigateBack)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
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

            uiState.currentStatus?.let { status ->
                Text(
                    text = stringResource(R.string.update_element_status_current_status),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                StatusCard(status, modifier = Modifier.fillMaxWidth())
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.update_element_status_new_status),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.selectedStatus != null) {
                    item {
                        StatusCard(
                            uiState.selectedStatus,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem(),
                            selected = true,
                            onClick = {}
                        )
                    }
                }

                items(statuses, key = { it.id }) { status ->
                    StatusCard(
                        status,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        onClick = { viewModel.onStatusSelected(status) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = viewModel::onUpdateStatus,
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