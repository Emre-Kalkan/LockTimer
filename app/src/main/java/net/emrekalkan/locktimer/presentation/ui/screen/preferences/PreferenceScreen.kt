package net.emrekalkan.locktimer.presentation.ui.screen.preferences

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import net.emrekalkan.locktimer.presentation.ui.components.OnBackButtonClick
import net.emrekalkan.locktimer.presentation.ui.components.Toolbar
import net.emrekalkan.locktimer.presentation.ui.screen.Screen
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.PreferencesViewModel.PreferencesUiState
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme

object PreferenceScreen : Screen(routeName = "PreferenceScreen")

private typealias CheckChange = (Boolean, BooleanPreferenceModel) -> Unit

@Preview
@Composable
private fun PreferenceScreenPreview() {
    LockTimerTheme {
        Surface {
            PreferenceScreenContent()
        }
    }
}

@Composable
fun PreferenceScreen(
    viewModel: PreferencesViewModel = hiltViewModel(),
    navigateToOnBoarding: () -> Unit,
    onBackButtonClick: OnBackButtonClick
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect { event ->
            when (event) {
                PreferencesViewModel.PreferencesEvent.NavigateToOnBoarding -> navigateToOnBoarding()
            }
        }
    }

    val uiState = viewModel.uiState.collectAsState()

    PreferenceScreenContent(
        uiState = uiState.value,
        checkChange = { checked, model -> viewModel.onBooleanPrefChange(checked, model) },
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun PreferenceScreenContent(
    uiState: PreferencesUiState = PreferencesUiState(),
    checkChange: CheckChange = { _, _ -> },
    onBackButtonClick: OnBackButtonClick = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(scrollState, Orientation.Vertical),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Toolbar(
            onBackButtonClick = onBackButtonClick
        )
        Header()
        Preferences(preferenceModels = uiState.preferences, checkChange = checkChange)
    }
}

@Composable
private fun Header() {
    Text(
        text = "Preferences",
        style = MaterialTheme.typography.h3,
        color = MaterialTheme.colors.onBackground,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 56.dp),
    )
}

@Composable
private fun Preferences(preferenceModels: List<PreferenceModel<*>>, checkChange: CheckChange) {
    val lazyListState = rememberLazyListState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        LazyColumn(state = lazyListState) {
            itemsIndexed(preferenceModels) { index, item ->
                when (item) {
                    is BooleanPreferenceModel -> BooleanPreferenceItem(item, checkChange)
                }

                if (index < preferenceModels.lastIndex) {
                    Divider(thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
private fun BooleanPreferenceItem(model: BooleanPreferenceModel, checkChange: CheckChange) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        PreferencesText(
            modifier = Modifier.weight(1f),
            text = stringResource(id = model.titleRes)
        )
        Switch(
            checked = model.value,
            onCheckedChange = { isChecked ->
                checkChange(isChecked, model)
            }
        )
    }
}

@Composable
private fun PreferencesText(
    modifier: Modifier = Modifier,
    text: String = ""
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.subtitle1,
        letterSpacing = 0.5.sp,
        color = MaterialTheme.colors.onBackground
    )
}