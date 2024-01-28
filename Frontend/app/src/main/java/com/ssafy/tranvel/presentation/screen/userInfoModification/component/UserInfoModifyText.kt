package com.ssafy.tranvel.presentation.screen.userInfoModification.component

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.ssafy.tranvel.presentation.ui.theme.bmjua

@Composable
fun UserInfoModifyText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily = bmjua,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = AnnotatedString(text),
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontFamily = bmjua,
        letterSpacing = letterSpacing,
        textAlign = textAlign,
        style = style,
        lineHeight = lineHeight,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Preview
@Composable
private fun BodyPreview(){
    UserInfoModifyText("Text test 중입니다.",Modifier)
}
