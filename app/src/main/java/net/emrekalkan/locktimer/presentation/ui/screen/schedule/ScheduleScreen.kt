package net.emrekalkan.locktimer.presentation.ui.screen.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.ui.components.Toolbar
import net.emrekalkan.locktimer.presentation.ui.screen.Screen
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.ScheduleViewModel.ScheduleUiState
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.SchedulerOption.CustomOption
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.SchedulerOption.SpecificOption
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme
import net.emrekalkan.locktimer.presentation.util.countdown.CountDownService
import net.emrekalkan.locktimer.presentation.util.extensions.orZero

object ScheduleScreen : Screen(routeName = "ScheduleScreen")

@Preview
@Composable
private fun ScheduleScreenPreview() {
    LockTimerTheme {
        Surface {
            ScheduleScreenContent(
                uiState = ScheduleUiState(),
                scheduleClicked = {},
                optionSelected = {},
                navigateToPreferences = {}
            )
        }
    }
}

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    ScheduleScreenContent(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState.value,
        scheduleClicked = {
            run {
                val action = viewModel.getCountDownAction() ?: return@run

                val serviceIntent = CountDownService.create(context, action)
                context.startService(serviceIntent)
            }
        },
        optionSelected = viewModel::onOptionClicked,
        navigateToPreferences = navigateToSettings
    )
}

@Composable
private fun ScheduleScreenContent(
    modifier: Modifier = Modifier,
    uiState: ScheduleUiState,
    scheduleClicked: () -> Unit,
    optionSelected: (SchedulerOption) -> Unit,
    navigateToPreferences: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            Toolbar(
                backButton = false,
                title = "",
                actions = {
                    Icon(
                        modifier = Modifier.clickable { navigateToPreferences() },
                        imageVector = Icons.Default.Settings,
                        contentDescription = "",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            )
            Header()
            Spacer(modifier = Modifier.height(32.dp))
            OptionsList(
                modifier = Modifier,
                options = uiState.options,
                selectedOption = uiState.selectedOption,
                optionSelected = optionSelected
            )
        }

        Spacer(modifier = Modifier.padding(top = 16.dp))
        ScheduleButton(
            modifier = Modifier,
            enabled = uiState.selectedOption != null,
            scheduleClicked = scheduleClicked
        )
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
                .size(72.dp)
        )
        Text(
            text = "Pick a time",
            style = typography.h4
        )
        Text(
            text = "in minutes",
            style = typography.h6,
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
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        if (options.isNotEmpty()) {
            Column {
                options.forEachIndexed { index, option ->
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
            .fillMaxWidth(),
        onClick = { scheduleClicked() },
        enabled = enabled,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Text(text = "Schedule Now", style = typography.button, modifier = Modifier.padding(vertical = 8.dp))
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
    selectedOption: SchedulerOption? = null,
    optionSelected: (SchedulerOption) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        val selectedCustomOption = selectedOption as? CustomOption
        var textState by remember { mutableStateOf(option.timeInMinutes) }
        val isSelected = selectedCustomOption?.timeInMinutes.orZero<Int>() > 0

        ScheduleTextField(
            modifier = Modifier.weight(1f),
            minutes = textState,
            onValueChange = { value ->
                if (value.isDigitsOnly()) {
                    textState = value.toIntOrNull().orZero()
                }
            },
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
    minutes: Int,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "Custom",
    onDone: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val focused = remember { mutableStateOf(false) }
    val text = minutes.takeIf { it > 0 }?.toString() ?: ""

    TextField(
        modifier = modifier
            .onFocusChanged { focused.value = it.hasFocus },
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            if (text.isEmpty() && focused.value.not()) {
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

private fun selectCustomOption(minutes: Int, option: CustomOption, optionSelected: (SchedulerOption) -> Unit) {
    if (minutes < 1) return

    val timeInMinutes = minutes.coerceAtLeast(1)
    val customOption = option.copy(timeInMinutes = timeInMinutes)
    optionSelected(customOption)
}
