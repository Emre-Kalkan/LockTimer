@file:OptIn(ExperimentalAnimationApi::class)

package net.emrekalkan.locktimer.presentation.ui.screen.schedule

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.ui.screen.Screen
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.ScheduleViewModel.ScheduleUiState
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.SchedulerOption.CustomOption
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.SchedulerOption.SpecificOption
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.count_down.CountDownService
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme

object ScheduleScreen : Screen(name = "ScheduleScreen")

@Preview
@Composable
private fun ScheduleScreenPreview() {
    LockTimerTheme {
        Surface {
            ScheduleScreenContent(
                uiState = ScheduleUiState(),
                scheduleClicked = {},
                optionSelected = {}
            )
        }
    }
}

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    ScheduleScreenContent(
        uiState.value,
        scheduleClicked = {
            run {
                val action = viewModel.getCountDownAction() ?: return@run

                val serviceIntent = CountDownService.create(context, action)
                context.startService(serviceIntent)
            }
        },
        optionSelected = viewModel::onOptionClicked
    )
}

@Composable
private fun ScheduleScreenContent(
    uiState: ScheduleUiState,
    scheduleClicked: () -> Unit,
    optionSelected: (SchedulerOption) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Header()
            Spacer(modifier = Modifier.height(32.dp))
            OptionsList(
                modifier = Modifier,
                options = uiState.options,
                selectedOption = uiState.selectedOption,
                optionSelected = optionSelected
            )

            AnimatedVisibility(visible = uiState.selectedOption != null) {
                Spacer(modifier = Modifier.padding(top = 16.dp))
                ScheduleButton(scheduleClicked = scheduleClicked)
            }
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_lock),
            contentDescription = "",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(96.dp)
        )
        Text(
            text = "Pick a time",
            style = typography.h3,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun OptionsList(
    modifier: Modifier = Modifier,
    options: List<SchedulerOption>,
    selectedOption: SchedulerOption?,
    optionSelected: (SchedulerOption) -> Unit
) {
    val lazyListState = rememberLazyListState()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        if (options.isNotEmpty()) {
            LazyColumn(state = lazyListState) {
                itemsIndexed(options) { index, option ->
                    when (option) {
                        is SpecificOption -> SpecificOptionContent(option, selectedOption, optionSelected)
                        is CustomOption -> CustomOptionContent(option)
                    }

                    if (index < options.lastIndex) {
                        Divider(thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}

@Composable
private fun ScheduleButton(
    modifier: Modifier = Modifier,
    scheduleClicked: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = { scheduleClicked() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Schedule Now")
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
private fun SpecificOptionContent(
    option: SpecificOption,
    selectedOption: SchedulerOption?,
    optionClicked: (SchedulerOption) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { optionClicked(option) }
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        val text = "${option.timeInMinutes} min"
        Text(
            text = text,
            style = typography.h6,
            modifier = Modifier.weight(1f),
            fontSize = 24.sp,
            letterSpacing = 0.5.sp
        )

        if (option == selectedOption) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "")
        }
    }
}

@Composable
private fun CustomOptionContent(option: CustomOption) {

}
