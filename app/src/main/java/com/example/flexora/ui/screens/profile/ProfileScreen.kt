package com.example.flexora.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.flexora.domain.model.User
import com.example.flexora.ui.components.PremiumCard
import com.example.flexora.ui.theme.PrimaryPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    user: User?,
    onUpdateName: (String) -> Unit,
    onUpdateImage: (String) -> Unit
) {
    var showNameDialog by remember { mutableStateOf(false) }
    var tempName by remember { mutableStateOf("") }
    
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // In a real app, you might want to take persistable URI permission
            // context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            onUpdateImage(it.toString())
        }
    }

    if (showNameDialog) {
        AlertDialog(
            onDismissRequest = { showNameDialog = false },
            title = { Text("Edit Name") },
            text = {
                OutlinedTextField(
                    value = tempName,
                    onValueChange = { tempName = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onUpdateName(tempName)
                    showNameDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, PrimaryPurple, CircleShape)
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (user?.profileImageUri.isNullOrBlank()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.padding(32.dp),
                            tint = PrimaryPurple
                        )
                    }
                } else {
                    AsyncImage(
                        model = user?.profileImageUri,
                        contentDescription = "Profile Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                
                // Edit Overlay
                Surface(
                    color = Color.Black.copy(alpha = 0.3f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .height(30.dp)
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp).padding(4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    user?.name ?: "Set your name",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { 
                    tempName = user?.name ?: ""
                    showNameDialog = true 
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit name", modifier = Modifier.size(20.dp))
                }
            }
            
            Text(
                "Weight Loss • 5 days streak",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            PremiumCard {
                ProfileOption(
                    icon = Icons.Default.Edit, 
                    title = "Edit Profile",
                    onClick = { 
                        tempName = user?.name ?: ""
                        showNameDialog = true 
                    }
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                
                // Dark Mode Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DarkMode, null, tint = PrimaryPurple, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(16.dp))
                        Text("Dark Mode", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                    }
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = onToggleDarkMode,
                        colors = SwitchDefaults.colors(checkedThumbColor = PrimaryPurple)
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                ProfileOption(icon = Icons.Default.Notifications, title = "Notifications")
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                ProfileOption(icon = Icons.Default.Shield, title = "Privacy & Security")
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            TextButton(
                onClick = { },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Log Out", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ProfileOption(icon: ImageVector, title: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )
    }
}
