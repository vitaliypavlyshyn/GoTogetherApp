package com.example.gotogether.presentation.screens.settings_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80


@Composable
@Preview(showBackground = true)
fun SettingsScreenPreview(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
    ) {
        IconButton(
            onClick = {
               // navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = Purple
            )
        }
        Text(
            text = "Налаштування",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = MediumGray,
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth().clickable(
                onClick = {

                }
            )
        ) {
            Text(
                text = "Завантажити історію активності",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Purple
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "right",
                tint = Purple
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth().clickable(
                onClick = {

                }
            )
        ) {
            Text(
                text = "Завантажити історію поїздок",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Purple
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "right",
                tint = Purple
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            thickness = 2.dp,
            color = PurpleGrey80
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(
                    onClick = {

                    }
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "exit",
                tint = DarkGreen,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Вийти",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = DarkGreen
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "right",
                tint = DarkGreen
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            thickness = 2.dp,
            color = PurpleGrey80
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(
                    onClick = {

                    }
                )
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete account",
                tint = DarkGreen,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Видалити обліковий запис",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = DarkGreen
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "right",
                tint = DarkGreen
            )
        }
    }
}