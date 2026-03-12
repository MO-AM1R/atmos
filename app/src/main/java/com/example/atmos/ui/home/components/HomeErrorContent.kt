package com.example.atmos.ui.home.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.atmos.R
import com.example.atmos.ui.core.components.ResourceIcon
import com.example.atmos.ui.theme.Spacing
import com.example.atmos.ui.theme.WeatherTypography


@Composable
fun HomeErrorContent(
    message : String,
    onRetry : () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier            = modifier
            .fillMaxSize()
            .padding(Spacing.XLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ResourceIcon(
            modifier   = Modifier.size(80.dp),
            resourceId = R.drawable.ic_error,
            color      = Color.White
        )

        Spacer(modifier = Modifier.height(Spacing.XLarge))

        Text(
            text       = stringResource(R.string.something_went_wrong),
            style      = WeatherTypography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color      = Color.White,
            textAlign  = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.Medium))

        Text(
            text      = message,
            style     = WeatherTypography.bodyLarge,
            color     = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spacing.XXLarge))

        Button(
            onClick  = onRetry,
            modifier = Modifier.fillMaxWidth(),
            shape    = RoundedCornerShape(50.dp),
            colors   = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.25f)
            )
        ) {
            Icon(
                imageVector        = Icons.Default.Refresh,
                contentDescription = null,
                tint               = Color.White,
                modifier           = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(Spacing.Small))
            Text(
                text  = stringResource(R.string.try_again),
                color = Color.White,
                style = WeatherTypography.titleMedium
            )
        }
    }
}