package chy72.whatevernamewillbeavailable;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;


public class Main72 extends Dxlib72{

    int Gr;
    int[] GrArray = new int[10];//配列の宣言方法

    Cirno CIRNO ;
    Cirno[] CIRNOArray = new Cirno[10];//クラスの場合

    int SEHandle,BGMHandle;

    //ここに初期処理を書く
    public void Awake72(){
        Gr= LoadGraph("Cirno.png");
        CIRNO = new Cirno(this,0,Gr);//コンストラクタで実体化

        for(int i =0;i<10;i++){
            GrArray[i] =   LoadGraph("CirnoFolder/Ci"+ i  +".png");//更に奥のフォルダからも読み込める
            CIRNOArray[i] = new Cirno(this,i*32,GrArray[i]);//コンストラクタで実体化
        }

        SEHandle  =LoadSoundMem("Attack.ogg",DX_PLAYTYPE_SE);
        BGMHandle = LoadSoundMem("TOGEN.mp3",DX_PLAYTYPE_LOOP);
        PlaySoundMem(BGMHandle);
    }

    //ここにゲーム内容を書く
    public void Loop72(){
        PlaySoundMem(SEHandle);
        for(int i =0;i<10;i++){
            CIRNOArray[i].Draw();
        }

    }

    //只のコンストラクタです。
    public Main72(Context context) {super(context);}
    public Main72(Context context, AttributeSet attrs) {super(context, attrs);}
    public Main72(Context context, AttributeSet attrs, int defs) {super(context, attrs, defs);}
}