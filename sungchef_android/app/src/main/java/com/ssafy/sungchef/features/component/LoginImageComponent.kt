package com.ssafy.sungchef.features.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.ssafy.sungchef.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoginImageComponent(
    modifier : Modifier,
    imageResource : Any,
    contentDescription : String = "",

){
    /**
     * 원형 이미지를 load할 때는 modifier 인자에 .clip(CircleShape)을 붙혀야한다.
     */
    GlideImage(
        model = imageResource,
        modifier = modifier,
        contentDescription = contentDescription,
        contentScale = ContentScale.FillBounds,
        failure = placeholder(R.drawable.icon_image_fail)
    )
}