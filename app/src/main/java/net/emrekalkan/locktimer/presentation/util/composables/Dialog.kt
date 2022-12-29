package net.emrekalkan.locktimer.presentation.util.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import net.emrekalkan.locktimer.R
import net.emrekalkan.locktimer.presentation.ui.theme.LockTimerTheme

data class DialogParams(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    val positive: Pair<Int, () -> Unit>,
    val neutral: Pair<Int, () -> Unit>? = null,
    val negative: Pair<Int, () -> Unit>? = null
)

@Composable
fun Dialog(
    params: DialogParams,
    onDismissRequest: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest) {
        DialogContent(params = params)
    }
}

@Composable
@Preview
private fun DialogContentPreview() {
    LockTimerTheme {
        Surface {
            val params = DialogParams(
                titleRes = R.string.admin_receiver_label,
                descriptionRes = R.string.admin_receiver_description,
                positive = R.string.app_name to {}
            )
            DialogContent(params)
        }
    }
}

@Composable
private fun DialogContent(
    params: DialogParams
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .defaultMinSize(minHeight = 72.dp),
            shape = RoundedCornerShape(16.dp)
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
                    text = stringResource(id = params.titleRes),
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = params.descriptionRes),
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