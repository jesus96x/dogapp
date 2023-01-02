package com.jd.dogapp.doglist


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.jd.dogapp.api.ApiResponseStatus
import com.jd.dogapp.composables.ErrorDialog
import com.jd.dogapp.composables.LoadingWheel
import com.jd.dogapp.composables.NavigationIcon
import com.jd.dogapp.models.Dog

private const val GRID_SPAN_COUNT = 3

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DogListScreen(
    onNavigationIconClick: () -> Unit,
    onDogClicked: (Dog) -> Unit,
    viewModel: DogListViewModel = hiltViewModel()
)
{
    val status = viewModel.status.value
    val dogList = viewModel.dogList.value

    Scaffold(
        topBar = { TopBar(onNavigationIconClick)  },
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SPAN_COUNT),
            content = {
                items(dogList.size) {
                    DogGridItem(dog = dogList[it], onDogClicked = onDogClicked)
                }
            },
        )
    }

    if(status is ApiResponseStatus.Loading)
    {
        LoadingWheel()
    }
    else if(status is ApiResponseStatus.Error)
    {
        ErrorDialog(status, { viewModel.resetApiResponseStatus() })
    }
}

@Composable
fun TopBar(onClick: () -> Unit)
{
    TopAppBar(
        title = { Text("Mi cole de perros") },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = { NavigationIcon(onClick) }

    )
}

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
@Composable
fun DogGridItem(dog: Dog, onDogClicked: (Dog) -> Unit)
{
    if(dog.inCollection)
    {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            onClick = { onDogClicked(dog) },
            shape = RoundedCornerShape(4.dp),
        ) {
            Image(
                painter = rememberImagePainter(dog.imageUrl),
                contentDescription = null,
                modifier = Modifier.background(Color.White).semantics { testTag = "dog-${dog.name}" }
            )
        }
    }
    else
    {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            color = Color.Red,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                text = dog.index.toString(),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black,
            )
        }
    }
}