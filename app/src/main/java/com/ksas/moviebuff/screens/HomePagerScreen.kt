package com.ksas.moviebuff.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ksas.moviebuff.R
import com.ksas.moviebuff.api.generic.Responses
import com.ksas.moviebuff.api.generic.Results


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePager(
    titleResponse: Responses, navController: NavHostController, onCardClicked: (Results) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0)
    Column(
        modifier = Modifier.background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            pageCount = titleResponse.results.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(370.dp),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp)
                    .clickable {
                        titleResponse.results
                            .getOrNull(it)
                            ?.let { it1 -> onCardClicked(it1) }
                    }, shape = RectangleShape, elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = titleResponse.results.getOrNull(it)?.primaryImage?.url
                            ?: R.drawable.noimage,
                        contentDescription = "",
                        modifier = Modifier.height(290.dp),
                        contentScale = ContentScale.FillBounds,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    titleResponse.results[it].titleText?.text?.let { it1 ->
                        Text(
                            text = it1,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = customFontMainHeading


                        )
                    }
                }

            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            repeat(titleResponse.results.size) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(9.dp)

                )
            }
        }
    }
}