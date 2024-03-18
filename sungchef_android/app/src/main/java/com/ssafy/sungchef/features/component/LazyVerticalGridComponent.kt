package com.ssafy.sungchef.features.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LazyVerticalGridComponent(
    photoUrls: List<String>,
    onClick:(String) -> (Unit)
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(photoUrls) { item ->
            ImageComponent(modifier = Modifier.clickable {
                onClick(item)
            }, imageResource = com.ssafy.sungchef.R.drawable.test_image_small)
        }
    }

}

@Preview
@Composable
private fun BodyPreview() {
    val lst = mutableListOf<String>()
    lst.add("원")
    lst.add("투")
    lst.add("쓰리")
    lst.add("포")
    lst.add("ㅍㅇㅂ")
    lst.add("ㅅㅅ")
    lst.add("ㅅㅂ")
    lst.add("ㄷㄷ")
    lst.add("ㅜㅇ")
    lst.add("ㅌ")
    LazyVerticalGridComponent(photoUrls = lst, onClick = { Log.d("TAG", "BodyPreview: $it")})
}