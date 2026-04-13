package com.example.atmos.ui.alert.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.ui.theme.extraColors

@Composable
fun AlertsHeader(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(R.string.alerts_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = extraColors.textPrimary
            )
            Text(
                text = stringResource(R.string.alerts_subtitle),
                fontSize = 14.sp,
                color = extraColors.textMuted
            )
        }

        FloatingActionButton(
            modifier = Modifier.size(48.dp),
            onClick = onAddClick,
            containerColor = extraColors.violet,
            contentColor = Color.White,
            shape = RoundedCornerShape(size = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.description)
            )
        }
    }
}
