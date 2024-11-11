package com.juanpablo0612.iefjt.ui.element_detail

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.juanpablo0612.iefjt.R
import com.juanpablo0612.iefjt.ui.utils.getDateText
import com.pushpal.jetlime.ItemsList
import com.pushpal.jetlime.JetLimeColumn
import com.pushpal.jetlime.JetLimeEvent
import com.pushpal.jetlime.JetLimeEventDefaults

@Composable
fun ElementDetailScreen(
    viewModel: ElementDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToUpdateElementStatus: (String, String) -> Unit
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ElementDetailTopBar(navigateBack = navigateBack)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            uiState.element?.let { element ->
                val statusHistory =
                    remember(element.statusHistory) { element.statusHistory.sortedByDescending { it.updatedAt } }

                ElementInfo(
                    title = stringResource(R.string.element_detail_name),
                    value = element.name,
                    icon = Icons.Default.Title
                )

                Spacer(modifier = Modifier.height(16.dp))

                ElementInfo(
                    title = stringResource(R.string.element_detail_serial),
                    value = element.serial,
                    icon = Icons.Default.Code
                )

                Spacer(modifier = Modifier.height(16.dp))

                ElementInfo(
                    title = stringResource(R.string.element_detail_location),
                    value = element.location,
                    icon = Icons.Default.AddLocation
                )

                Spacer(modifier = Modifier.height(16.dp))

                ElementInfo(
                    title = stringResource(R.string.element_detail_last_update_date),
                    value = context.getDateText(element.lastUpdate),
                    icon = Icons.Default.History
                )

                Spacer(modifier = Modifier.height(16.dp))

                ElementInfo(
                    title = stringResource(R.string.element_detail_status),
                    value = element.status.name,
                    color = Color(parseColor("#${element.status.color}")),
                    buttonIcon = Icons.Default.Edit,
                    onButtonClick = { navigateToUpdateElementStatus(element.id, element.status.id) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.element_detail_type),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        AsyncImage(
                            model = element.type.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(50.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = element.type.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.element_detail_brand),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        AsyncImage(
                            model = element.brand.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(50.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = element.brand.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.element_detail_status_history),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                JetLimeColumn(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .heightIn(max = 500.dp),
                    itemsList = ItemsList(statusHistory),
                    key = { _, item -> "$item.id${item.updatedAt}" }
                ) { _, item, position ->
                    JetLimeEvent(
                        style = JetLimeEventDefaults.eventStyle(
                            position = position,
                            pointColor = Color(parseColor("#${item.color}")),
                            pointFillColor = Color(parseColor("#${item.color}"))
                        ),
                    ) {
                        ElementInfo(
                            title = item.name,
                            value = context.getDateText(item.updatedAt)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ElementInfo(
    title: String,
    value: String,
    icon: ImageVector? = null,
    color: Color? = null,
    buttonIcon: ImageVector? = null,
    onButtonClick: () -> Unit = {}
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (buttonIcon != null) {
            IconButton(onClick = onButtonClick) {
                Icon(
                    imageVector = buttonIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        if (color != null) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(color = color)
            )
        }
    }
}