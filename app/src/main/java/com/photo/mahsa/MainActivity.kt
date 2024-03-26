package com.photo.mahsa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.photo.mahsa.navigation.AppNavHost
import com.photo.mahsa.navigation.Screens
import com.photo.mahsa.ui.theme.MahsaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MahsaTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet(
                            drawerTonalElevation = 32.dp,
                            drawerShape = RoundedCornerShape(0.dp)
                        ) {
                            Text(text = "Content")
                        }
                    },
                    drawerState = drawerState
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                modifier = Modifier.shadow(
                                    elevation = 8.dp,
                                    spotColor = Color.DarkGray
                                ),
                                title = {
                                    Text(text = stringResource(id = R.string.app_name))
                                }, navigationIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            scope.launch {
                                                if (drawerState.isClosed) drawerState.open()
                                            }
                                        })
                                })
                        }, floatingActionButton = {
                            FloatingActionButton(onClick = {
                                navController.navigate(
                                    Screens.AddTask.route,
                                    navOptions = navOptions {
                                        launchSingleTop = true
                                    })
                            }) {
                                Icon(
                                    imageVector = Icons.TwoTone.AddCircle,
                                    contentDescription = null
                                )
                            }
                        }) { innerPadding ->

                        Surface(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            AppNavHost(
                                modifier = Modifier.fillMaxSize(),
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

