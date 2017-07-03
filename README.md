# Dxlib72 メモ
- ver 0.72, Code CHY

# 目次
- 初めに
- システム的な関数関連
- 言語の違い関連
- 初心者向け C++からJavaに書き直すときのヒント

# 始めに
- Dxlib72は、C++でDxlibを使用していた人が、Android用のゲームを作りたくなった時に、Dxlibを使用していたときと同じ感覚でプログラミングが出来るように作られたライブラリです。
  昨今のアンドロイドブームの中で、Windowsプログラミングだけで終わるのは、もったいない！
　もしもDxlibと同じようにプログラミング出来たら、便利なのではないか？そう思い、作りました。
  72個の関数を利用できます。

- Dxlib72.java、Define72.java、ForDxLib72Activity.java、Main72.javaの４つのコードからなります
  - 一応説明しますと
  - Dxlib72はDxlib系の関数が入っているSurfaceviewのクラスです。
    分からなければ変更しない方がいいでしょう。
  - ForDxLib72Activityは、Activityを継承した、Dxlib72用のクラスです。
    分からなければ変更しない方がいいでしょう。
  - Define72は画面の大きさなどの定義が入っているクラスです。
  値は自分でカスタムできます。
  - Main72は、Dxlib72を継承した、ユーザーが主に処理を書くためのクラスです。

- 主にMain72に処理を書きましょう。
  - Awake72()の中にゲーム開始時に行う処理を、
  Loop72()の中に毎ゲームループ毎に行う処理を書きます。
  - Main72はDxlibの関数をそのまま呼び出すことが出来ます。
  - 他の自作クラスでDxlibの関数を呼び出したいときは、Dxlib72の参照をどこかで得て使いましょう。



# システム的な関数関連
- `SetWindowIconID( int )`
　-  使用しない。
　- アイコンを変えるには、AndroidManifest.xmlの、`android:icon="@drawable/ic_launcher"` を変更するか、 ic_launcher.png 自体を変更してください
- `SetWindowText( String )`
　- 使用しない。
　- ホーム画面で表示される名前を変えるには、`<string name="app_name">Dxlib72</string>` の Dxlib72 を 変更してください。


# 言語の違い関連
- 文字列はString のみで、*charをサポートしません。そのため、文字列関連は適宜書き直してください。
　- 例えば、`DrawFormatString(,,,,"cirno %d", 99)` としていたのが、`DrawFormatString(,,,,"cirno " + 99)` になる感じです。楽でしょ？
- Javaでは参照渡しが出来ないため、一部の関数を変更しました。
- `GetGraphSize` は、`GetGraphWidth,GetGraphHeight`
 　- GetNowCount　はint型からlong型になりました。、1970年1月1日からのミリ秒を取得します- 透過色の概念は無し、（初めからpngなどで透過指定(手軽に透明 や Gimpのαチャンネル追加など)出来るため、）



# 初心者向け C++からJavaに書き直すときのヒント
- 配列
　- `int enemyx[2];` は `int[] enemyx = new int[2];` のように書きます。使い方は同じ
- 多次元配列
　- `int map[3000][3000];` は `int[][] map = new int[3000][3000]` ;
　- `Color72 C[9][100][100];` は `Color72[][][] C = new Color72[9][100][100];` のように、自作クラスでも適用可能
- 色にはColor.BLUEなどを使うと楽.書いた後AndroidStudioならAlt+Enterをすると必要なものをimportしてくれる。
- static変数を、 final staticでなく使うと危険なので非推奨である。詳しくはググって。
- クラスは何もしないとき参照渡しと同じように渡せる。
  - ヘッダーは無いのでクラスの中に変数を書いたりしよう
- CMeクラスを書き換えるとして、CMe.javaを作る
  - コンストラクタ　`CMe::CMe(){~~}` は `public CMe{~~}` このように `CMe::` の部分はなくす
  ->で書き換えようと参照しているところは, .で書き換えようと参照する。
- defineレベルの定数は `static final int` とか無難ですね
  - public をつけるとどこからでもアクセスできる
- クラスを作るときは、`CMe = new CMe();` と、実体を作るべし
  - `static final boolean TRUE =true;` 
  - `static final boolean FALSE =false;` (C++でのboolはjavaでのboolean)
  - `Dxlib72.Color72 C = D. new Color72();` (Color72を外部クラスからインスタンス化するとき(DというDxlib72のインスタンスを作ってから))
