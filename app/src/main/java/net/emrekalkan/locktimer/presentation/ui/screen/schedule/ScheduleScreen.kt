@file:OptIn(ExperimentalAnimationApi::class)

package net.emrekalkan.locktimer.presentation.ui.screen.schedule

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import kotlin.math.round

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

            Spacer(modifier = Modifier.padding(top = 16.dp))
            ScheduleButton(
                modifier = Modifier,
                enabled = uiState.selectedOption != null,
                scheduleClicked = scheduleClicked
            )
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
                .size(96.dp)
        )
        Text(
            text = "Pick a time",
            style = typography.h3
        )
        Text(
            text = "in minutes",
            style = typography.h5,
            modifier = Modifier
                .padding(horizontal = 16.dp)
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
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        if (options.isNotEmpty()) {
            LazyColumn(state = lazyListState) {
                itemsIndexed(options) { index, option ->
                    when (option) {
                        is SpecificOption -> SpecificOptionContent(option, selectedOption, optionSelected)
                        is CustomOption -> CustomOptionContent(option, selectedOption, optionSelected)
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
    enabled: Boolean = false,
    scheduleClicked: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = { scheduleClicked() },
        enabled = enabled
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Schedule Now", style = typography.button)
        }
    }
}

@Composable
private fun OptionText(
    modifier: Modifier = Modifier,
    text: String = ""
) {
    Text(
        modifier = modifier,
        text = text,
        style = typography.h6,
        fontSize = 24.sp,
        letterSpacing = 0.5.sp,
        color = MaterialTheme.colors.onBackground
    )
}

@Preview
@Composable
private fun SpecificOptionContent(
    option: SpecificOption = SpecificOption(timeInMinutes = 10),
    selectedOption: SchedulerOption? = null,
    optionClicked: (SchedulerOption) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(64.dp)
            .clickable { optionClicked(option) }
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        val text = "${option.timeInMinutes} "
        OptionText(
            modifier = Modifier.weight(1f),
            text = text
        )

        if (option == selectedOption) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "")
        }
    }
}

@Preview
@Composable
private fun CustomOptionContent(
    option: CustomOption = CustomOption(),
    selectedOption: SchedulerOption? = CustomOption(),
    optionSelected: (SchedulerOption) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        var textState by remember { mutableStateOf("") }
        val isSelected = option.javaClass == selectedOption?.let { it::class.java } && selectedOption.isEqual(textState)

        ScheduleTextField(
            modifier = Modifier.weight(1f),
            text = textState,
            onValueChange = { textState = it },
            onDone = { selectCustomOption(textState, option, optionSelected) }
        )
        if (isSelected) {
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = Icons.Default.Done,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun ScheduleTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "Custom",
    onDone: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val focused = remember { mutableStateOf(false) }

    TextField(
        modifier = modifier
            .onFocusChanged { focused.value = it.hasFocus },
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            if (focused.value.not()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = hint,
                    style = typography.h6,
                    fontSize = 20.sp,
                    letterSpacing = 0.5.sp,
                    color = MaterialTheme.colors.onBackground
                )
            }
        },
        textStyle = typography.h6.copy(
            fontSize = 24.sp,
            letterSpacing = 0.5.sp,
            color = MaterialTheme.colors.onBackground
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.onBackground
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions {
            onDone()
            focusManager.clearFocus()
        }
    )
}

private fun selectCustomOption(text: String, option: CustomOption, optionSelected: (SchedulerOption) -> Unit) {
    val timeInMinutes = round(text.toDouble()).toInt().coerceAtLeast(1)
    val customOption = option.copy(timeInMinutes = timeInMinutes)
    optionSelected(customOption)
}
