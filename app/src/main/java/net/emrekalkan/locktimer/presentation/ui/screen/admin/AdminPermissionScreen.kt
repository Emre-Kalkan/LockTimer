package net.emrekalkan.locktimer.presentation.ui.screen.admin

import android.content.ComponentName
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.hilt.navigation.compose.hiltViewModel
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.ui.components.OnBackButtonClick
import net.emrekalkan.locktimer.presentation.ui.screen.Screen
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme

object AdminPermissionScreen : Screen(routeName = "AdminPermissionScreen") {
    const val ARG_BACK_ROUTE = "backRoute"

    override val args: List<String>
        get() = listOf(ARG_BACK_ROUTE)

    fun getRoute(backRoute: String): String {
        return "$routeName/$backRoute"
    }
}

@Preview
@Composable
fun AdminPermissionScreenPreview() {
    LockTimerTheme {
        Surface {
            AdminPermissionContent {}
        }
    }
}

@Composable
fun AdminPermissionScreen(
    viewModel: AdminPermissionViewModel = hiltViewModel(),
    onBackButtonClick: OnBackButtonClick
) {
    val context = LocalContext.current
    val adminComponent = ComponentName(context, LockScreenAdminReceiver::class.java)
    val adminRequestLauncher = rememberLauncherForActivityResult(
        contract = AddAdminDeviceContract(),
        onResult = viewModel::onAdminResult
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect { event ->
            when (event) {
                AdminPermissionViewModel.AdminPermissionScreenEvent.NavigateBack -> onBackButtonClick()
            }
        }
    }

    AdminPermissionContent {
        adminRequestLauncher.launch(adminComponent)
    }
}

@Composable
fun AdminPermissionContent(requestAdmin: () -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .weight(1f)
        ) {
            val (headerRef, descriptionRef, enableButtonRef) = createRefs()
            val verticalChainRef = createVerticalChain(
                headerRef,
                descriptionRef,
                chainStyle = ChainStyle.Packed(0.2f)
            )
            constrain(verticalChainRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            Header(headerRef, descriptionRef)
            PermissionUsageCard(headerRef, descriptionRef, enableButtonRef)
        }
        Button(
            onClick = { requestAdmin() },
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.enable),
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun ConstraintLayoutScope.Header(
    permissionTitle: ConstrainedLayoutReference,
    description: ConstrainedLayoutReference
) {
    Column(
        modifier = Modifier
            .constrainAs(permissionTitle) {
                top.linkTo(parent.top)
                bottom.linkTo(description.top)
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_lock),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
        )
        Text(
            text = stringResource(R.string.admin_permission_required),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
        )
    }
}

@Composable
private fun ConstraintLayoutScope.PermissionUsageCard(
    permissionTitle: ConstrainedLayoutReference,
    description: ConstrainedLayoutReference,
    enableButton: ConstrainedLayoutReference
) {
    Card(
        modifier = Modifier
            .padding(top = 32.dp)
            .constrainAs(description) {
                top.linkTo(permissionTitle.bottom)
                bottom.linkTo(enableButton.top)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.admin_permission_used_features_title),
                style = MaterialTheme.typography.body1
            )

            PermissionFeatureBox(
                title = stringResource(R.string.admin_permission_lock_screen_bullet),
                description = stringResource(R.string.admin_permission_lock_screen_description)
            )
        }
    }
}

@Composable
fun PermissionFeatureBox(
    title: String,
    description: String? = null
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp),
        )
        if (description != null) {
            Text(
                text = description,
                style = MaterialTheme.typography.overline,
                modifier = Modifier
                    .padding(start = 28.dp, top = 4.dp),
            )
        }
    }
}
