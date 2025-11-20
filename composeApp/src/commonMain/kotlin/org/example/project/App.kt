package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * アプリケーションのエントリーポイント
 *
 * ViewModelを作成し、UI状態を監視してDogSearchScreenを表示します。
 */
@Composable
@Preview
fun App() {
    MaterialTheme {
        // ViewModelの取得
        val viewModel: DogSearchViewModel = viewModel { DogSearchViewModel() }
        // UI状態の購読
        val uiState by viewModel.uiState

        DogSearchScreen(
            uiState = uiState,
            onBreedClick = viewModel::selectBreed
        )
    }
}

/**
 * 犬種検索画面のメインレイアウト
 *
 * 画面を上部の犬種リスト（40%）と下部の画像表示エリア（60%）に分割します。
 *
 * @param uiState 現在のUI状態
 * @param onBreedClick 犬種がクリックされた際のコールバック
 */
@Composable
private fun DogSearchScreen(
    uiState: DogSearchUiState,
    onBreedClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 犬種リスト（画面上部40%）
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.breeds) { breed ->
                val isSelected = breed == uiState.selectedBreed
                BreedItem(
                    breed = breed,
                    isSelected = isSelected,
                    onBreedClick = { onBreedClick(breed) }
                )
            }
        }

        // 犬の画像表示エリア（画面下部60%）
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            DogImageContent(
                isLoading = uiState.isLoading,
                dogImageUrl = uiState.dogImageUrl
            )
        }
    }
}

/**
 * 犬種選択アイテム
 *
 * 選択状態に応じて背景色を変更します（選択時: 黄色、未選択時: グレー）。
 *
 * @param breed 犬種名
 * @param isSelected 選択状態
 * @param onBreedClick クリック時のコールバック
 */
@Composable
private fun BreedItem(
    breed: String,
    isSelected: Boolean,
    onBreedClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) Color(0xFFFFD54F) else Color(0xFFE0E0E0)
            )
            .clickable(onClick = onBreedClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = breed.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

/**
 * 犬の画像表示コンテンツ
 *
 * ローディング中はプログレスインジケーターを、
 * 画像取得完了後は犬の画像を表示します。
 *
 * @param isLoading ローディング状態
 * @param dogImageUrl 犬の画像URL
 */
@Composable
private fun DogImageContent(
    isLoading: Boolean,
    dogImageUrl: String?
) {
    when {
        // ローディング中
        isLoading -> {
            CircularProgressIndicator()
        }

        // 画像取得完了
        dogImageUrl != null -> {
            AsyncImage(
                model = dogImageUrl,
                contentDescription = "Dog image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}