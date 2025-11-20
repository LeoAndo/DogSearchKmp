package org.example.project

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

/**
 * 犬種検索画面のビジネスロジックを管理するViewModel
 *
 * Dog CEO APIを使用して犬の画像を取得し、UI状態を管理します。
 */
class DogSearchViewModel : ViewModel() {
    /** 利用可能な犬種のリスト */
    private val breeds = listOf(
        "affenpinscher",
        "african",
        "airedale",
        "akita",
        "appenzeller",
    )

    /** UI状態の内部管理用State */
    private val _uiState = mutableStateOf(
        DogSearchUiState(
            breeds = breeds,
            selectedBreed = breeds.first()
        )
    )

    /** 外部公開用のUI状態（読み取り専用） */
    val uiState: State<DogSearchUiState> = _uiState

    /** Dog CEO APIへのHTTPリクエスト用クライアント */
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    /** コルーチンの例外をハンドリングし、ローディング状態を解除 */
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    init {
        // 初回起動時に最初の犬種の画像を取得
        fetchDogImage(breeds.first())
    }

    /**
     * 犬種を選択し、該当する画像を取得する
     *
     * @param breed 選択された犬種
     */
    fun selectBreed(breed: String) {
        _uiState.value = _uiState.value.copy(selectedBreed = breed)
        fetchDogImage(breed)
    }

    /**
     * Dog CEO APIから指定された犬種の画像をランダムに取得する
     *
     * @param breed 画像を取得する犬種
     */
    private fun fetchDogImage(breed: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            // ローディング開始
            _uiState.value = _uiState.value.copy(isLoading = true)

            // APIリクエスト実行
            val response: DogApiResponse = httpClient
                .get("https://dog.ceo/api/breed/$breed/images/random")
                .body()

            // レスポンスをUI状態に反映
            _uiState.value =
                _uiState.value.copy(dogImageUrl = response.message, isLoading = false)
        }
    }

    /**
     * ViewModelが破棄される際にHTTPクライアントをクローズする
     */
    override fun onCleared() {
        httpClient.close()
        super.onCleared()
    }
}