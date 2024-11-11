package com.juanpablo0612.iefjt.ui.element_list

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.juanpablo0612.iefjt.R
import com.juanpablo0612.iefjt.data.exceptions.NetworkException
import com.juanpablo0612.iefjt.domain.model.Element
import com.juanpablo0612.iefjt.ui.components.CommonCard
import com.juanpablo0612.iefjt.ui.components.ErrorCard
import com.juanpablo0612.iefjt.ui.components.StatusCard

@Composable
fun ElementListScreen(
    viewModel: ElementListViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToElementDetail: (String) -> Unit,
    navigateToAddElement: () -> Unit
) {
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            ElementListTopBar(navigateBack = navigateBack)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item {
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
                    text = stringResource(R.string.element_list_filtering_by),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (uiState.type != null) {
                    CommonCard(
                        imageUrl = uiState.type.imageUrl,
                        name = uiState.type.name,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else if (uiState.brand != null) {
                    CommonCard(
                        imageUrl = uiState.brand.imageUrl,
                        name = uiState.brand.name,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else if (uiState.status != null) {
                    StatusCard(
                        status = uiState.status,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (uiState.elements.isNotEmpty()) {
                items(uiState.elements, key = { it.id }) { element ->
                    ElementCard(element, onClick = { navigateToElementDetail(element.id) })
                }
            } else {
                item {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = stringResource(R.string.element_list_no_elements),
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(onClick = navigateToAddElement) {
                                Text(text = stringResource(R.string.element_list_add_element_button_text))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ElementCard(element: Element, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = element.type.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(50.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = element.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = element.brand.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}