package com.khun.petgallery.presentation.ui.features.pets.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khun.petgallery.R
import com.khun.petgallery.domain.mappers.PetDataModel
import com.khun.petgallery.presentation.contracts.BaseContract
import com.khun.petgallery.presentation.contracts.PetContract
import com.khun.petgallery.presentation.ui.components.EmptyView
import com.khun.petgallery.presentation.ui.navigation.NavigationScreens
import com.khun.petgallery.presentation.ui.navigation.getBottomNavigationItems
import com.khun.petgallery.presentation.ui.theme.PetGalleryTheme
import com.khun.petgallery.presentation.ui.theme.lightYellow
import com.khun.petgallery.utils.TestTags
import com.khun.petgallery.utils.TestTags.PROGRESS_BAR
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@Composable
fun PetScreen(
    state: PetContract.State,
    effectFlow: Flow<BaseContract.Effect>?,
    onNavigationRequested: (itemUrl: String, imageId: String) -> Unit,
    onRefreshCall: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val petMessage = "Pets are loaded"
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    val navController = rememberNavController()
    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->
            if (effect is BaseContract.Effect.DataWasLoaded)
                snackBarHostState.showSnackbar(
                    message = petMessage,
                    duration = SnackbarDuration.Short
                )
        }?.collect { value ->
            if (value is BaseContract.Effect.Error) {
                Toast.makeText(context, value.errorMessage, Toast.LENGTH_LONG)
            }
        }
    }
    Scaffold(
        topBar = {
            PetAppBar(
                showRefreshButton = navigationSelectedItem == 0,
                onNavigationIconClick = {
                    navigationSelectedItem = 0
                    navController.navigate(NavigationScreens.Home.screenRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, onRefreshCall = onRefreshCall
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(R.color.purple_500),
                contentColor = colorResource(R.color.white)
            ) {
                getBottomNavigationItems(context).forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(
                                text = navigationItem.title,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.white)
                            )
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.title,
                                modifier = Modifier.semantics { testTag = navigationItem.title },
                                tint = if (index == navigationSelectedItem) colorResource(id = R.color.purple_500)
                                else colorResource(id = R.color.white)
                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.screenRoute) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        //We need to setup our NavHost in here
        NavHost(
            navController = navController,
            startDestination = NavigationScreens.Home.screenRoute,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(NavigationScreens.Home.screenRoute) {
                AppView(state, false, onNavigationRequested = onNavigationRequested)
            }
            composable(NavigationScreens.MyFavourites.screenRoute) {
                AppView(state, true, onNavigationRequested = onNavigationRequested)
            }
        }
    }
}

@Composable
fun AppView(
    state: PetContract.State,
    isFavPetsCall: Boolean,
    onNavigationRequested: (itemUrl: String, imageId: String) -> Unit
) {
    Surface(
        modifier = Modifier.semantics {
            testTag =
                if (isFavPetsCall) TestTags.MY_FAVOURITE_SCREEN_TAG else TestTags.HOME_SCREEN_TAG
        }) {
        Box {
            val pets = if (isFavPetsCall) state.favPets else state.pets
            if (isFavPetsCall && pets.isEmpty()) {
                EmptyView(message = stringResource(R.string.favourite_screen_empty))
            } else {
                PetsList(
                    pets = pets,
                    isLoading = state.isLoading,
                    isFavPetsCall = isFavPetsCall
                ) { itemUrl, imageId ->
                    onNavigationRequested(itemUrl, imageId)
                }
                if (state.isLoading) {
                    LoadingBar()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceAsColor")
@Composable
private fun PetAppBar(
    showRefreshButton: Boolean,
    onNavigationIconClick: () -> Unit,
    onRefreshCall: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.semantics { testTag = TestTags.PET_SCREEN_APP_BAR },
        navigationIcon = {
            IconButton(
                onClick = { onNavigationIconClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    modifier = Modifier,
                    contentDescription = TestTags.ACTION_ICON,
                    tint = colorResource(R.color.white)
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = colorResource(R.color.white)
            )
        }, actions = {
            if (showRefreshButton) {
                IconButton(
                    onClick = { onRefreshCall() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = TestTags.REFRESH_ACTION,
                        tint = colorResource(R.color.white)
                    )
                }
            }
        },
        colors = centerAlignedTopAppBarColors(
            containerColor = colorResource(R.color.purple_500),
            titleContentColor = Color(R.color.white),
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.secondary

        )
    )
}

@Composable
fun PetsList(
    isLoading: Boolean = false,
    pets: List<PetDataModel>,
    isFavPetsCall: Boolean,
    onItemClicked: (url: String, imageId: String) -> Unit = { _: String, _: String -> }
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        content = {
            this.items(pets) { item ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    border = BorderStroke(0.5.dp, Color.Gray),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                        .clickable {
                            if (!isLoading) {
                                onItemClicked(item.url, item.imageId)
                            }
                        }
                        .semantics { testTag = TestTags.PET_ITEM_TAG }
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .padding(top = 1.dp)
                    ) {
                        ItemThumbnail(thumbnailUrl = item.url)
                        if (!isFavPetsCall && !item.name.isNullOrBlank()) {
                            Column(
                                modifier = Modifier
                                    .background(Color.Black)
                                    .fillMaxWidth()
                                    .align(Alignment.BottomStart)
                            ) {
                                Text(
                                    text = item.name,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    color = colorResource(id = R.color.white)
                                )
                                item.origin?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        color = colorResource(id = R.color.purple_500)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }, modifier = Modifier
            .semantics { testTag = TestTags.PETS_LIST_TAG }
            .fillMaxSize()
    )
}

@Composable
fun ItemThumbnail(thumbnailUrl: String) {
    GlideImage(
        imageModel = thumbnailUrl,
        modifier = Modifier
            .semantics { testTag = TestTags.LIST_IMG }
            .wrapContentSize()
            .wrapContentHeight()
            .fillMaxWidth(),
        contentScale = ContentScale.Crop,
        circularReveal = CircularReveal(duration = 100),
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colorScheme.background,
            highlightColor = Color.Gray,
            durationMillis = 500,
            dropOff = 0.55f,
            tilt = 20f,
        ),
        contentDescription = TestTags.PET_THUMBNAIL_PICTURE
    )
}


@Composable
fun LoadingBar() {
    Box(
        modifier = Modifier
            .semantics { testTag = TestTags.LOADING_BAR_TAG }
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .testTag(PROGRESS_BAR)
                .size(60.dp),
            color = colorResource(id = R.color.purple_500),
            strokeWidth = 5.dp,
            trackColor = lightYellow,
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PetGalleryTheme {
        PetScreen(
            PetContract.State(),
            null,
            onNavigationRequested = { _: String, _: String -> },
            onRefreshCall = {}
        )
    }
}
