@file:OptIn(ExperimentalPermissionsApi::class)

package net.emrekalkan.locktimer.presentation.ui.screen.schedule

import android.Manifest
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.*
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.ui.components.BannerAd
import net.emrekalkan.locktimer.presentation.ui.components.Toolbar
import net.emrekalkan.locktimer.presentation.ui.screen.Screen
import net.emrekalkan.locktimer.presentation.ui.screen.interstitial.InterstitialAdManager
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.ScheduleViewModel.ScheduleUiState
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.SchedulerOption.CustomOption
import net.emrekalkan.locktimer.presentation.ui.screen.schedule.SchedulerOption.SpecificOption
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme
import net.emrekalkan.locktimer.presentation.util.KeyboardState
import net.emrekalkan.locktimer.presentation.util.countdown.CountDownService
import net.emrekalkan.locktimer.presentation.util.dice.DiceChance
import net.emrekalkan.locktimer.presentation.util.extensions.navigateToSettings
import net.emrekalkan.locktimer.presentation.util.extensions.orFalse
import net.emrekalkan.locktimer.presentation.util.extensions.orZero
import net.emrekalkan.locktimer.presentation.util.keyboardAsState

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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect { event ->
            when (event) {
                ScheduleViewModel.ScheduleEvent.HideKeyboard -> keyboardController?.hide()
            }
        }
    }

    ScheduleScreenContent(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState.value,
        scheduleClicked = {
            run {
                val action = viewModel.getCountDownAction() ?: return@run

                val serviceIntent = CountDownService.create(context, action)
                context.startService(serviceIntent)
                viewModel.onCountDownStarted()
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
                        contentDescription = ""
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
        BannerAd(modifier = Modifier.padding(top = 8.dp))
        ScheduleButton(
            modifier = Modifier,
            enabled = uiState.selectedOption != null,
            isScheduled = uiState.isScheduled,
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
            text = stringResource(R.string.schedule_title_first),
            style = typography.h4
        )
        Text(
            text = stringResource(R.string.schedule_title_second),
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
    enabled: Boolean,
    isScheduled: Boolean,
    scheduleClicked: () -> Unit
) {
    val context = LocalContext.current
    val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    } else {
        null
    }

    Column {
        if (permissionState?.status?.isGranted?.not().orFalse) {
            PostNotificationInfoText {
                if (permissionState?.status?.shouldShowRationale.orFalse) {
                    context.navigateToSettings()
                } else {
                    permissionState?.launchPermissionRequest()
                }
            }
        }

        Button(
            modifier = modifier
                .fillMaxWidth(),
            onClick = {
                if (permissionState == null || permissionState.status.run { isGranted.or(shouldShowRationale) }) {
                    InterstitialAdManager.tryShow(context, DiceChance.HIGH, scheduleClicked)
                } else {
                    permissionState.launchPermissionRequest()
                }
            },
            enabled = enabled,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            val text = if (isScheduled) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "",
                    modifier = Modifier.padding(end = 4.dp)
                )
                stringResource(id = R.string.just_scheduled)
            } else {
                stringResource(id = R.string.schedule_now)
            }
            Text(
                text = text,
                style = typography.button,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun PostNotificationInfoText(onClick: () -> Unit) {
    val iconTag = "[icon]"
    val text = buildAnnotatedString {
        append("${stringResource(R.string.permission_notification_info)} ")
        appendInlineContent(iconTag, iconTag)
    }
    val inlineContent = mapOf(
        iconTag to InlineTextContent(Placeholder(14.sp, 14.sp, PlaceholderVerticalAlign.Center)) {
            Icon(Icons.Default.ArrowForward, contentDescription = "")
        }
    )
    Text(
        text = text,
        style = typography.caption,
        textAlign = TextAlign.Center,
        inlineContent = inlineContent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .clickable { onClick() }
    )
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
        val customOptionValue = selectedCustomOption?.timeInMinutes ?: option.timeInMinutes
        val isSelected = selectedCustomOption?.timeInMinutes.orZero<Int>() > 0

        ScheduleTextField(
            modifier = Modifier.weight(1f),
            minutes = customOptionValue,
            onValueChange = { value ->
                if (value.isNotEmpty() && value.isDigitsOnly()) {
                    optionSelected(option.copy(timeInMinutes = value.toInt()))
                }
            },
            onDone = { selectCustomOption(customOptionValue, option, optionSelected) }
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
    modifier: Modifier = Modifier,
    hint: String = stringResource(R.string.custom),
    onValueChange: (String) -> Unit,
    onDone: (String) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    var focused by remember { mutableStateOf(false) }
    val text = minutes.takeIf { it > 0 }?.toString() ?: ""
    val keyboardState by keyboardAsState()

    if (keyboardState == KeyboardState.Closed) {
        focusManager.clearFocus(force = true)
    }

    TextField(
        modifier = modifier
            .onFocusChanged { focused = it.hasFocus },
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            if (text.isEmpty() && focused.not()) {
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
            if (text.isNotEmpty() && text.isDigitsOnly()) {
                onDone(text)
            }
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
