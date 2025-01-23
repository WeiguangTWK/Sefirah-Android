package com.castle.sefirah.presentation.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.castle.sefirah.presentation.onboarding.components.PageIndicator
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
) {

    val steps = remember {
        listOf(
            WelcomeStep(),
            StorageStep(),
            PermissionStep(),
        )
    }

    val viewModel : OnboardingViewModel = hiltViewModel()

    val pagerState = rememberPagerState(initialPage = 0) {
        steps.size
    }

    val scope = rememberCoroutineScope()
    BackHandler(enabled = pagerState.currentPage > 0) {
        scope.launch {
            pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
        }
    }

    val buttonState = remember {
        derivedStateOf {
            when(pagerState.currentPage) {
                0 -> "Get Started"
                1 -> "Next"
                2 -> "Finish"
                else -> ""
            }
        }
    }

    val buttonEnabled = remember {
        derivedStateOf {
            when(pagerState.currentPage) {
                steps.size - 1 -> steps[pagerState.currentPage].isComplete
                else -> true
            }
        }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                PageIndicator(
                    modifier = Modifier.width(80.dp),
                    pageSize = steps.size,
                    selectedPage = pagerState.currentPage
                )
                Row(verticalAlignment = Alignment.CenterVertically){
                    if (buttonState.value.isNotEmpty()) {
                        TextButton(
                            enabled = buttonEnabled.value,
                            onClick = {
                                scope.launch {
                                    if (pagerState.currentPage == steps.size - 1) {
                                        viewModel.saveAppEntry()
                                        onComplete()
                                    } else {
                                        pagerState.animateScrollToPage(
                                            page = pagerState.currentPage + 1
                                        )
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                            ),
                            shape = RoundedCornerShape(size = 6.dp)
                        ) {
                            Text(
                                text = buttonState.value,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            HorizontalPager(state = pagerState) {index ->
                steps[index].Content()
            }
        }
    }
}