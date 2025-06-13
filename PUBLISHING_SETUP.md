# Publishing Setup Guide (公開設定ガイド)

このドキュメントは、**初回セットアップ時に手動で行う必要のある設定手順**です。

> **📝 注意**: 一度設定が完了すれば、その後のリリースはGitHub Actionsで自動化されます。自動リリースについては [RELEASE_PROCESS.md](RELEASE_PROCESS.md) を参照してください。

## セットアップが必要な場合

- 新しいIntelliJ Pluginプロジェクトの初回セットアップ
- GitHub Actions による自動公開の準備
- JetBrains Marketplace への初回プラグイン登録

## 必要な事前準備

### 1. JetBrains アカウントの準備

**JetBrains アカウントが必要です：**

1. **アカウント作成**
   - [JetBrains Account Center](https://account.jetbrains.com) でアカウント作成
   - または既存のJetBrains アカウントを使用

2. **Marketplace アクセス確認**
   - [JetBrains Marketplace](https://plugins.jetbrains.com/) にログイン可能であることを確認

## Publishing手順

### Step 1: Personal Access Token の取得

**自動公開用のトークンを取得します：**

1. **Token生成ページにアクセス**
   - [My Tokens](https://plugins.jetbrains.com/author/me/tokens) に移動

2. **新しいトークンを生成**
   - "Generate Token" をクリック
   - トークン名を入力（例: "GitHub Actions"）
   - "Generate Token" をクリック

3. **トークンをコピー**
   - 生成されたトークンをコピーして安全な場所に保存
   
### Step 2: GitHub Repository のシークレット設定

**GitHub Repository に認証情報を設定します：**

1. **GitHub Repository の設定ページにアクセス**
   - GitHubの該当リポジトリページに移動
   - "Settings" タブをクリック

2. **Secrets and variables の設定**
   - 左メニューから "Secrets and variables" → "Actions" をクリック

3. **Repository secret の追加**
   以下の2つのシークレットを追加してください：
   
   **シークレット1: JetBrains Marketplace Token**
   - "New repository secret" をクリック
   - **Name**: `INTELLIJ_PLATFORM_PUBLISHING_TOKEN`
   - **Value**: Step 1で取得したPersonal Access Token
   - "Add secret" をクリック
   
   **シークレット2: GitHub Personal Access Token**
   - "New repository secret" をクリック
   - **Name**: `PAT_TOKEN`
   - **Value**: GitHubのPersonal Access Token (repo権限必要)
   - "Add secret" をクリック
   
   **シークレット3: Codecov Token (オプション)**
   - "New repository secret" をクリック
   - **Name**: `CODECOV_TOKEN`
   - **Value**: CodecovのUpload Token
   - "Add secret" をクリック
   
   > **📝 GitHub Personal Access Tokenの取得方法:**
   > 1. GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
   > 2. "Generate new token" → "Generate new token (classic)"
   > 3. スコープで "repo" を選択
   > 4. 生成されたトークンをコピー
   
   > **📝 Codecov Tokenの取得方法:**
   > 1. [Codecov](https://codecov.io/) にGitHubアカウントでログイン
   > 2. リポジトリを追加・選択
   > 3. Settings → Repository Upload Token をコピー
   > 4. ※Codecovを使用しない場合はこのシークレットは不要です

## セットアップ完了後

**全ての設定が完了すると：**

- ✅ GitHub Actions による自動リリースが利用可能
- ✅ JetBrains Marketplace への自動公開が有効
- ✅ [RELEASE_PROCESS.md](RELEASE_PROCESS.md) の手順でリリース実行可能

## トラブルシューティング

### 手動での動作確認

**緊急時やテスト目的でローカルから手動公開する場合：**

```bash
# 環境変数として設定（一時的）
export ORG_GRADLE_PROJECT_intellijPlatformPublishingToken="your_token_here"

# プラグインを手動公開
./gradlew publishPlugin
```

> **⚠️ セキュリティ注意**: Personal Access Token はローカル環境でのみ使用し、リポジトリにコミットしないでください。

## 関連ドキュメント

- **自動リリース**: [RELEASE_PROCESS.md](RELEASE_PROCESS.md) - セットアップ完了後の自動リリースプロセス
- **JetBrains公式ドキュメント**: [Plugin Publishing](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html)
- **GitHub Actions設定**: `.github/workflows/` ディレクトリ内の各ワークフローファイル
