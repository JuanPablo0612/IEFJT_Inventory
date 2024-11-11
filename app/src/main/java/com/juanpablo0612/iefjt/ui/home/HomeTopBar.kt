package com.juanpablo0612.iefjt.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.juanpablo0612.iefjt.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onLogout: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineLarge)
        },
        actions = {
            IconButton(onClick = onLogout) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Logout,
                    contentDescription = null
                )
            }
        }
    )
}