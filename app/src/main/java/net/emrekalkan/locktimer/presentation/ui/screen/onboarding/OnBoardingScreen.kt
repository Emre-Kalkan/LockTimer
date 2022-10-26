package net.emrekalkan.locktimer.presentation.ui.screen.onboarding

import android.content.ComponentName
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.*
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.ui.screen.Screen
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme

object OnBoardingScreen : Screen(name = "OnBoardingScreen")

@Preview
@Composable
fun OnBoardingScreenPreview() {
    LockTimerTheme {
        OnBoardingContent {}
    }
}

@Composable
fun OnBoardingScreen(
    navigateToSchedule: () -> Unit
) {
    val context = LocalContext.current
    val adminComponent = ComponentName(context, LockScreenAdminReceiver::class.java)
    val adminRequestLauncher = rememberLauncherForActivityResult(
        contract = AddAdminDeviceContract(),
        onResult = {
            if (it) {
                navigateToSchedule()
            }
        }
    )
    OnBoardingContent {
        adminRequestLauncher.launch(adminComponent)
    }
}

@Composable
fun OnBoardingContent(requestAdmin: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .border(32.dp, MaterialTheme.colors.background)
            .padding(32.dp),
    ) {
        ConstraintLayout {
            val (headerRef, descriptionRef, enableButtonRef) = createRefs()
            val verticalChainRef = createVerticalChain(
                headerRef,
                descriptionRef,
                enableButtonRef,
                chainStyle = ChainStyle.Packed(0.2f)
            )
            constrain(verticalChainRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            Header(headerRef, descriptionRef)
            PermissionDescriptionBody(headerRef, descriptionRef, enableButtonRef)
            Button(
                onClick = { requestAdmin() },
                modifier = Modifier
                    .padding(top = 32.dp)
                    .constrainAs(enableButtonRef) {
                        top.linkTo(descriptionRef.bottom)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                    },
            ) {
                Text(text = "Enable")
            }
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
            text = "Admin Permission Required",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
        )
    }
}

@Composable
private fun ConstraintLayoutScope.PermissionDescriptionBody(
    permissionTitle: ConstrainedLayoutReference,
    description: ConstrainedLayoutReference,
    enableButton: ConstrainedLayoutReference
) {
    Column(
        modifier = Modifier
            .constrainAs(description) {
                top.linkTo(permissionTitle.bottom)
                bottom.linkTo(enableButton.top)
            }
    ) {
        Text(
            text = "We need your permission to function the app as needed. To be able to use some of the sensitive device functions you must grant needed permission.",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )
        PermissionUsageCard()
    }
}

@Composable
private fun PermissionUsageCard() {
    Card(
        modifier = Modifier.padding(top = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Text(
                text = "Following feature(s) will be used",
                style = MaterialTheme.typography.body1
            )

            PermissionFeatureBox(
                title = "•  Lock screen",
                description = "The screen will be locked after the scheduled time by you has elapsed."
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
