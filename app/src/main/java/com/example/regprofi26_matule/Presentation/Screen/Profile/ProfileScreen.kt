package com.example.regprofi26_matule.Presentation.Screen.Profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.regprofi26_matule.Domain.UserRepository
import com.example.regprofi26_matule.Presentation.Navigation.NavigationRoutes
import com.example.regprofi26_matule.Presentation.Screen.Component.MainTabBar
import com.example.regprofi26_matule.Presentation.ViewModels.MainViewModel
import com.example.regprofi26_matule.R
import com.example.uikit.Buttons.ButtonBig
import com.example.uikit.Controls.Toggle
import com.example.uikit.Tabbar.TabBar
import com.example.uikit.UI.MatuleTheme
import com.example.uikit.UI.SpacerH
import com.example.uikit.UI.SpacerW
import com.example.uikit.UI.createMatuleTypography
import com.github.barteksc.pdfviewer.PDFView

@Composable
fun ProfileScreen(viewModel: MainViewModel, navController: NavHostController){

    val state = viewModel.state

    LaunchedEffect(Unit) {
            viewModel.getUser()
    }

    var notification by remember { mutableStateOf(UserRepository.Notification) }
    var document by remember {
        mutableStateOf<String?>(null)
    }
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        SpacerH(76)

        Text(state.User?.firstname?: "User", style = createMatuleTypography().title1Heavy)
        SpacerH(8)
        Text(state.User?.phone?: "+7-XXX-XXX-XX-XX",
            style = createMatuleTypography().headlineRegular,
            color = MatuleTheme.colors.placeholder
        )

        SpacerH(24)

        Row(
            modifier = Modifier.height(64.dp).clickable{
                navController.navigate(NavigationRoutes.ORDER_LIST)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(painter = painterResource(R.drawable.order),
                contentDescription = null, modifier = Modifier.size(32.dp),
                contentScale = ContentScale.Crop
                )
            SpacerW(20)
            Text("Мои заказы", style = createMatuleTypography().title3Semibold)


        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.height(64.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(painter = painterResource(R.drawable.settings),
                    contentDescription = null, modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Crop
                )
                SpacerW(20)
                Text("Уведомления", style = createMatuleTypography().title3Semibold)
            }

            Toggle(notification) {
                notification = it
                UserRepository.Notification = it
            }
        }

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
            ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Политика конфиденциальности",
                    style = createMatuleTypography().textMedium,
                    color = MatuleTheme.colors.placeholder,
                    modifier = Modifier.clickable{
                        document = "privacy.pdf"
                    }
                )
                SpacerH(24)
                Text("Пользовательское соглашение",
                    style = createMatuleTypography().textMedium,
                    color = MatuleTheme.colors.placeholder,
                    modifier = Modifier.clickable{
                        document = "agreement.pdf"
                    }
                )
                SpacerH(24)
                Text("Выход",
                    style = createMatuleTypography().textMedium,
                    color = MatuleTheme.colors.error,
                    modifier = Modifier.clickable{
                        UserRepository.Act = false
                        UserRepository.Notification = false
                        UserRepository.UserId = ""
                        UserRepository.Pin = ""
                        UserRepository.Token = ""
                        UserRepository.Email = ""

                        navController.navigate(NavigationRoutes.AUTH){
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
                SpacerH(160)
            }
        }
    }

    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {

        Box(modifier = Modifier.background(Color.White)) {
            MainTabBar(
                navController,
                NavigationRoutes.PROFILE
            )
        }

    }

    if (document != null) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                PDFView(context, null).apply {
                    fromAsset(document!!)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .load()
                }
            }
        )

    }
}