package com.example.atmos.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmos.R
import com.example.atmos.data.enums.Language
import com.example.atmos.ui.theme.SettingsSectionBackground
import com.example.atmos.ui.theme.extraColors


@Composable
fun LanguageRow(
    language: Language,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.extraColors.violet.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_language),
                    contentDescription = null,
                    tint = MaterialTheme.extraColors.violet,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column {
                Text(
                    text = stringResource(R.string.settings_language),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.extraColors.textPrimary
                )
                Text(
                    text = language.localeCode,
                    fontSize = 12.sp,
                    color = MaterialTheme.extraColors.textMuted
                )
            }
        }

        // TODO: State = current Language (language.localeCode as label)
        // TODO: On click → Show language picker bottom sheet
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(SettingsSectionBackground.copy(alpha = 0.3f))
                .clickable { onClick() }
                .padding(horizontal = 14.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = language.localeCode,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.extraColors.textPrimary
            )
        }
    }
}