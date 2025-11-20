package org.example.project

/**
 * 犬種検索画面のUI状態を表すデータクラス
 *
 * @property breeds 利用可能な犬種のリスト
 * @property selectedBreed 現在選択されている犬種
 * @property dogImageUrl 表示する犬の画像URL（null の場合は画像未取得）
 * @property isLoading 画像取得中かどうかを示すフラグ
 */
data class DogSearchUiState(
    val breeds: List<String> = emptyList(),
    val selectedBreed: String = "",
    val dogImageUrl: String? = null,
    val isLoading: Boolean = false
)