@file:OptIn(ExperimentalPermissionsApi::class)

package net.emrekalkan.locktimer.presentation.ui.screen.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.*
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.domain.model.PreferenceModel
import net.emrekalkan.locktimer.domain.model.TimerActionPreferenceModel
import net.emrekalkan.locktimer.presentation.ui.components.BannerAd
import net.emrekalkan.locktimer.presentation.ui.components.DefaultCard
import net.emrekalkan.locktimer.presentation.ui.components.OnBackButtonClick
import net.emrekalkan.locktimer.presentation.ui.components.Toolbar
import net.emrekalkan.locktimer.presentation.ui.screen.Screen
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.PreferencesViewModel.PreferencesUiState
import net.emrekalkan.locktimer.presentation.ui.screen.preferences.actions.TimerActionPermissionDialog
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme

object PreferenceScreen : Screen(routeName = "PreferenceScreen")

private typealias CheckChange = (Boolean, TimerActionPreferenceModel) -> Unit
private typealias RemoveAdmin = () -> Unit

@Preview
@Composable
private fun PreferenceScreenPreview() {
    LockTimerTheme {
        Surface {
            PreferenceScreenContent(
                uiState = PreferencesUiState(
                    preferences = listOf(),
                    isAdmin = true
                )
            )
        }
    }
}

@Composable
fun PreferenceScreen(
    viewModel: PreferencesViewModel = hiltViewModel(),
    navigateToAdminPermission: () -> Unit,
    onBackButtonClick: OnBackButtonClick
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect { event ->
            when (event) {
                PreferencesViewModel.PreferencesEvent.NavigateToAdminPermission -> navigateToAdminPermission()
            }
        }
    }

    val uiState = viewModel.uiState.collectAsState()

    PreferenceScreenContent(
        uiState = uiState.value,
        checkChange = { checked, model -> viewModel.onBooleanPrefChange(checked, model) },
        onBackButtonClick = onBackButtonClick,
        removeAdmin = viewModel::removeAdmin
    )
}

@Composable
private fun PreferenceScreenContent(
    uiState: PreferencesUiState = PreferencesUiState(),
    checkChange: CheckChange = { _, _ -> },
    onBackButtonClick: OnBackButtonClick = {},
    removeAdmin: RemoveAdmin = {}
) {
    val scrollState = rememberScrollState()

    Column {
        Toolbar(onBackButtonClick = onBackButtonClick)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Header()
            Preferences(preferenceModels = uiState.preferences, checkChange = checkChange)
            if (uiState.isAdmin) {
                RemoveAdmin(
                    removeAdmin = removeAdmin
                )
            }
            BannerAd(modifier = Modifier.padding(top = 8.dp))
        }
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
    DefaultCard(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column {
            preferenceModels.forEachIndexed { index, item ->
                when (item) {
                    is TimerActionPreferenceModel -> BooleanPreferenceItem(item, checkChange)
                }

                if (index < preferenceModels.lastIndex) {
                    Divider(thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
private fun BooleanPreferenceItem(model: TimerActionPreferenceModel, checkChange: CheckChange) {
    val multiplePermissionState = rememberMultiplePermissionsState(permissions = model.actionPermission?.permissions.orEmpty())
    val multiplePermissionDialogVisible = remember { mutableStateOf(false) }

    if (multiplePermissionDialogVisible.value && multiplePermissionState.revokedPermissions.isNotEmpty()) {
        TimerActionPermissionDialog(
            revokedPermissions = multiplePermissionState.revokedPermissions,
            onDismiss = {
                multiplePermissionDialogVisible.value = false
            }
        )
    }

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
                when {
                    multiplePermissionState.shouldShowRationale -> multiplePermissionDialogVisible.value = true
                    isChecked && multiplePermissionState.allPermissionsGranted.not() -> multiplePermissionState.launchMultiplePermissionRequest()
                    else -> checkChange(isChecked, model)
                }
            }
        )
    }
}

@Composable
private fun PreferencesText(
    modifier: Modifier = Modifier,
    text: String = "",
    color: Color = MaterialTheme.colors.onBackground
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.subtitle1,
        letterSpacing = 0.5.sp,
        color = color
    )
}

@Composable
private fun RemoveAdmin(
    removeAdmin: RemoveAdmin
) {
    DefaultCard(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        PreferencesText(
            modifier = Modifier
                .padding(16.dp)
                .clickable { removeAdmin() },
            text = stringResource(id = R.string.remove_admin),
            color = MaterialTheme.colors.error
        )
    }
}