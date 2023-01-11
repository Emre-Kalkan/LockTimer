@file:OptIn(ExperimentalMaterialApi::class)

package net.emrekalkan.locktimer.presentation.util.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme

@Composable
fun BottomSheet(
    state: ModalBottomSheetState,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = state,
        scrimColor = MaterialTheme.colors.background.copy(alpha = 0.32f),
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetContent = { content() }
    ) { }
}

@Preview
@Composable
fun BottomSheetPreview() {
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)
    LockTimerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            BottomSheet(
                state = state,
                params = DialogParams(
                    title = net.emrekalkan.locktimer.presentation.util.Text.Res(textRes = R.string.admin_receiver_label),
                    description = net.emrekalkan.locktimer.presentation.util.Text.Res(
                        textRes = R.string.admin_receiver_description
                    ),
                    positive = R.string.app_name to {}
                )
            )
        }
    }
}

@Composable
fun BottomSheet(
    state: ModalBottomSheetState,
    params: DialogParams
) {
    BottomSheet(state = state) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = params.title.getText(LocalContext.current),
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = params.description.getText(LocalContext.current),
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    params.neutral?.let {
                        TextButton(onClick = params.neutral.second) {
                            Text(text = stringResource(id = params.neutral.first))
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    params.negative?.let {
                        TextButton(onClick = params.negative.second) {
                            Text(text = stringResource(id = params.negative.first))
                        }
                    }
                    TextButton(onClick = params.positive.second) {
                        Text(text = stringResource(id = params.positive.first))
                    }
                }
            }
        }
    }
}
