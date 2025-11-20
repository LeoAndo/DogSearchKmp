package org.example.project

import kotlinx.serialization.Serializable

/**
 * Dog CEO API からのレスポンスを表すデータクラス
 *
 * @property message 犬の画像URL
 * @property status APIリクエストのステータス（"success" または "error"）
 */
@Serializable
data class DogApiResponse(
    val message: String,
    val status: String
)