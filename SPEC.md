# 要件
- Dog APIを利用し、犬種を指定した画像取得と表示を行える
- 1画面に表示する犬の画像は１つで良い

# アーキテクチャ
- 画面はJetpack Composeを採用する
- 責務の明確化: ロジック(ViewModel)とUI(Composable)を完全に分離する
- 単一方向データフロー: ViewModel → UiState → UI
- StateFlowよりも Compose Stateを優先的に活用する

# ソースコードコメント
- KotlinのKDoc形式に従い、IDEのドキュメント機能で表示できる形式で記述すること
- できるだけ細かい粒度で記述すること

# 画面イメージ
sample.pngの画像ファイルを参考に画面作成してください。

# Dog API
今回利用するAPIは https://dog.ceo/dog-api/breeds-list で確認できます。

呼び出し例
https://dog.ceo/api/breed/affenpinscher/images/random
https://dog.ceo/api/breed/african/images/random
https://dog.ceo/api/breed/airedale/images/random
https://dog.ceo/api/breed/akita/images/random
https://dog.ceo/api/breed/appenzeller/images/random
https://dog.ceo/api/breed/wolfhound/images/random

レスポンス例 Json形式のデータ
```json
{
  "message": "https://images.dog.ceo/breeds/wolfhound-irish/n02090721_5373.jpg",
  "status": "success"
}
```