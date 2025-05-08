package io.pressurediary.android.mobile.ui.screens.main

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import io.pressurediary.android.common.ui.PDColors
import io.pressurediary.android.mobile.navigation.HomeNavHost
import io.pressurediary.android.mobile.navigation.Screens
import io.pressurediary.android.mobile.ui.screens.main.records.new_rec.NewRecordBottomSheet
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel = getViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val childNavController = rememberNavController()

    var isVisibleNewRecord by remember {
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
            HorizontalDivider(
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
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        selected = remember(currentRoute) {
                            currentRoute == navItem::class.qualifiedName
                        },
                        icon = navItem.icon,
                        title = navItem.screenName,
                    ) {
                        navController.navigate(navItem) {
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
                    onClick = onClickAdd,
                )
                .background(color = PDColors.orange),
            contentAlignment = Alignment.BottomEnd
        ) {
            Icon(
                modifier = Modifier
                    .padding(13.dp)
                    .size(29.dp),
                painter = painterResource(id = io.pressurediary.android.common.R.drawable.ic_add),
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
                onClick = onClick,
                enabled = true,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
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
private fun PreviewBottomNavigationBar() {
    val navController = rememberNavController()
    BottomNavigationBar(
        navController = navController
    )
}