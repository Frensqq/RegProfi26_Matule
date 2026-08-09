package com.example.regprofi26_matule.Presentation.Screen.Market

import android.R
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.netlibrary.domain.model.News
import com.example.regprofi26_matule.Presentation.ViewModels.AuthViewModel
import com.example.regprofi26_matule.Presentation.ViewModels.MainViewModel
import com.example.uikit.Card.CardPreview
import com.example.uikit.Card.PrimaryCard
import com.example.uikit.CategoryMenu.CategoryMenu
import com.example.uikit.Search.SearchBig
import com.example.uikit.Tabbar.TabBar
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.SpacerW
import com.example.uikit.UI.createMatuleTypography
import kotlin.let

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController){

    val state = viewModel.state

    var launch by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        if (launch){
            viewModel.getNews()
            viewModel.getProducts()
            launch = false
        }
    }
    LaunchedEffect(state.searchString) {
        viewModel.updateState(state.copy(searchFilter = "title ~ '${state.searchString}'"))
        viewModel.getProducts()
    }
    LaunchedEffect(state.currentCategory) {
        viewModel.updateState(state.copy(searchFilter = "type ~ '${state.currentCategory}'"))
        viewModel.getProducts()
    }


    Column() {
        SpacerH(72)

        Column(Modifier.padding(horizontal = 20.dp)) {
        SearchBig(
            state.searchString
            ,"Искать описание",
            {
                viewModel.updateState(state.copy(searchString = it))
            }, {
                viewModel.updateState(state.copy(searchString = ""))
            }

        )

            SpacerH(28)

            Text("Акции и новости",
                style = createMatuleTypography().title3Semibold,
                color = MatuleTheme.colors.placeholder
            )
        }

        SpacerH(16)

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SpacerW(4)
            }

            items(state.News?.items.orEmpty()) { news ->

                val imageUrl = viewModel.getImageUrl(
                    news.collectionName,
                    news.id,
                    news.newsImage
                )

                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(152.dp),
                    contentScale = ContentScale.FillHeight,
                )
            }

            item {
                SpacerW(20)
            }
        }

        SpacerH(32)

        Text("Каталог описаний",
            modifier = Modifier.padding(start = 20.dp),
            style = createMatuleTypography().title3Semibold,
            color = MatuleTheme.colors.placeholder
        )

        SpacerH(16)

        CategoryMenu(
            state.categoryList,
            state.currentCategory,
        ) {
            viewModel.updateState(state.copy(currentCategory = it))
        }

        SpacerH(24)

        LazyColumn(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.Products != null) {
                items(state.Products!!.items) {
                    PrimaryCard(
                        it.title, it.type, it.price.toString(), true,{}
                    )
                }
                item { SpacerH(88) }
            }
        }

    }

    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {

        Box(modifier = Modifier.background(Color.White)) {
            TabBar({
                viewModel.updateState(state.copy(tabBarState = "Главная"))
            },{
                viewModel.updateState(state.copy(tabBarState = "Каталог"))

            },{
                viewModel.updateState(state.copy(tabBarState = "Заказы"))

            },{
                viewModel.updateState(state.copy(tabBarState = "Профиль"))

            },state.tabBarState)
        }

    }

}