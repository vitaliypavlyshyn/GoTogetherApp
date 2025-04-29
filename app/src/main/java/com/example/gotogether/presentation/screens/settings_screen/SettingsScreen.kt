package com.example.gotogether.presentation.screens.settings_screen

import android.os.Environment
import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel<SettingsViewModel>(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val settingsState = settingsViewModel.state.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var showConfirmDialog by remember { mutableStateOf(false) }
    LaunchedEffect(settingsState.value.deleteResult) {

        settingsState.value.deleteResult?.onSuccess { res ->
            if (res.isSuccess) {
                navController.navigate("login") {
                    popUpTo("settings") { inclusive = true }
                }
            }
            Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
        }?.onFailure { res ->
            Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
    ) {
        IconButton(
            onClick = {
                navController.popBackStack()
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
                    settingsViewModel.exportActivityLogToJson(context)
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
                    settingsViewModel.exportMyBookedTripsToJson(context)
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
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth().clickable(
                onClick = {
                    settingsViewModel.exportMyPropositionsToJson(context)
                }
            )
        ) {
            Text(
                text = "Завантажити історію моїх пропозицій",
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
                        navController.navigate("login")
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
            modifier = Modifier.clickable {
                showConfirmDialog = true
            }
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


    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Підтвердження") },
            text = { Text("Ви впевнені, що хочете видалити обліковий запис? Цю дію неможливо скасувати.") },
            confirmButton = {
                Text(
                    "Так",
                    modifier = Modifier
                        .clickable {
                            settingsViewModel.deleteUser()
                            showConfirmDialog = false
                        }
                        .padding(8.dp),
                    color = Purple
                )
            },
            dismissButton = {
                Text(
                    "Ні",
                    modifier = Modifier
                        .clickable { showConfirmDialog = false }
                        .padding(8.dp),
                    color = Color.Gray
                )
            }
        )
    }
}