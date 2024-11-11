package com.juanpablo0612.iefjt.ui.element_list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.juanpablo0612.iefjt.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElementListTopBar(navigateBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.element_list_title))
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
            }
        }
    )
}