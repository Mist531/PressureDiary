package com.mist.mobile_app.ui.screens.main

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mist.common.ui.PDColors
import com.mist.mobile_app.navigation.HomeNavHost
import com.mist.mobile_app.navigation.Screens
import com.mist.mobile_app.ui.screens.main.records.new_rec.NewRecordBottomSheet
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel = getViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val childNavController = rememberNavController()

    var isVisibleNewRecord by rememberSaveable {
        mutableStateOf(false)
    }

    NewRecordBottomSheet(
        isVisible = isVisibleNewRecord,
        onDismissRequest = {
            isVisibleNewRecord = false
            viewModel.onTriggeredRefreshData()
        },
        onCloseBottomSheet = {
            isVisibleNewRecord = false
        }
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navController = childNavController,
                onClickAdd = {
                    isVisibleNewRecord = true
                }
            )
        }
    ) { paddingValues ->
        HomeNavHost(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = 35.dp
                )
                .fillMaxSize(),
            globalNavController = navController,
            childNavController = childNavController,
            parentPaddingValues = paddingValues,
            historyEventRefreshData = state.eventRefreshData,
            historyOnConsumedEventRefreshData = viewModel::onConsumedRefreshData
        )
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onClickAdd: () -> Unit = {}
) {
    Box(
        modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(76.dp)
                .background(PDColors.white)
                .align(Alignment.BottomCenter)
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(2.dp),
                color = PDColors.grey
            )
            NavigationBar(
                modifier = modifier
                    .padding(
                        end = 60.dp
                    )
                    .fillMaxWidth(),
                containerColor = Color.White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                Screens.HomeBottomNavItem.items.forEach { navItem ->
                    NavigationBarItem(
                        selected = remember(currentRoute) {
                            currentRoute == navItem.route
                        },
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        icon = navItem.icon,
                        title = navItem.screenName,
                    ) {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 30.dp,
                    bottom = 48.dp
                )
                .clip(CircleShape)
                .clickable(
                    role = Role.Button,
                    onClick = remember {
                        onClickAdd
                    },
                )
                .background(color = PDColors.orange),
            contentAlignment = Alignment.BottomEnd
        ) {
            Icon(
                modifier = Modifier
                    .padding(13.dp)
                    .size(29.dp),
                painter = painterResource(id = com.mist.common.R.drawable.ic_add),
                contentDescription = null,
                tint = PDColors.black
            )
        }
    }
}


@Composable
fun RowScope.NavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    title: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .selectable(
                selected = false,
                onClick = remember {
                    onClick
                },
                enabled = true,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = PDColors.black
            )
            AnimatedVisibility(
                visible = selected
            ) {
                Text(
                    text = title,
                    color = PDColors.black
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewBottomNavigationBar() {
    val navController = rememberNavController()
    BottomNavigationBar(
        navController = navController
    )
}