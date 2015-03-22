package ;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import static java.lang.System.currentTimeMillis;



public abstract class Dxlib72 extends SurfaceView implements SurfaceHolder.Callback, Runnable ,
        MediaPlayer.OnCompletionListener, GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener{
    //いわゆるSystem.cpp



    @Override
    public void run() {
        Awake72();
        while (thread72 != null) {
            InitTouch();
            TM872.TouchProcess();
            canvas72 = holder72.lockCanvas();
            if(canvas72!= null) {
                canvas72.drawColor(BackColor72);
                Loop72();
                DrawPrintfDx();
                holder72.unlockCanvasAndPost(canvas72);
            }
            try {
                Thread.sleep(Define72.DEFAULTSLEEPMILLITIME);
            } catch (Exception e) {
            }
        }
    }

    abstract void Awake72();

    abstract void Loop72();

    //システマチックな変数定義
    private Resources r72;
    private AssetManager asset72 ;
    private Paint fontp72 = new Paint();//フォント用ペインタ
    private Paint diagp72 = new Paint();//図形用ペインタ
    private ArrayList<Bitmap> ImageVector = new ArrayList<Bitmap>();//DrawGraph用のデータ
    private ArrayList<MusicPlayer72> MusicVector = new ArrayList<MusicPlayer72>();//LoadSoundMemory用のBGM用のデータ
    private ArrayList<Integer>SoundVector = new ArrayList<Integer>();//LoadSoundMemory用のSE用のデータ
    private ArrayList<String> PrintfList = new ArrayList<String>();//printfDx用のデータ
    private Paint Printfp72 = new Paint();//図形用ペインタ
    private Paint Drawp72 = new Paint();
    private SoundPool sp72;
    public TouchMove8 TM872 = new TouchMove8();
    private Canvas canvas72;
    private Thread thread72;
    private Handler handler72 = new Handler();
    private Context context72 = null;
    private SurfaceHolder holder72;
    private GestureDetector gesture72;

    //必要な変数定義
    private int BackColor72 = 0;
    private Random rnd72 = new Random();

    //コンストラクタは3つ作った方がいいらしい
    //初期化処理
    public Dxlib72(Context context) {
        super(context);
        context72 = context;
        LibInit72();
    }
    public Dxlib72(Context context, AttributeSet attrs) {
        super(context, attrs);
        context72 = context;
        LibInit72();
    }
    public Dxlib72(Context context, AttributeSet attrs, int defs) {
        super(context, attrs, defs);
        context72 = context;
        LibInit72();
    }

    private void LibInit72() {
        setFocusable(true);
        r72 = getResources();
        asset72 = context72.getAssets();
        holder72 = getHolder();
        holder72.addCallback(this);
        holder72.setFixedSize(Define72.WINDOW_WIDTH, Define72.WINDOW_HEIGHT);
        fontp72.setAntiAlias(true);
        fontp72.setTextSize(Define72.DefaultFontSize);
        diagp72.setAntiAlias(true);
        diagp72.setStrokeWidth(1);
        Printfp72.setAntiAlias(true);
        Printfp72.setStrokeWidth(1);
        Printfp72.setTextSize(16);
        Printfp72.setColor(Define72.PrintfDxColor);
        BackColor72 = Define72.DefaultBackColor;
        gesture72 = new GestureDetector(context72,this);
        sp72 = new SoundPool(Define72.DefaultMaxSE, AudioManager.STREAM_MUSIC, 0);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread72 = new Thread(this);
        thread72.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread72 = null;
        //while(thread72.isAlive());
    }




    ///Dxlib72用のクラスの定義///////////////////////////////////////////////////////////////////////////////////////////

    public class Color72 {
        public int red = 0;
        public int green = 0;
        public int blue = 0;
        public int alpha = 0;

        public Color72() {
        }

        public int toColor() {
            return Color.argb(alpha, red, green, blue);
        }
    }
    public class DATEDATA{
        public DATEDATA(){};
        public int Year=0; 	// 年
        public int Mon=0; 	// 月
        public int Day=0; 	// 日
        public int Hour=0; 	// 時間
        public int Min=0; 	// 分
        public int Sec=0; 	// 秒
    }




    ///////各関数の定義////////////////////////////////////
    //Graphic関係/////////////////////////////////////////////////////////Graphic関係//////////////////////////////
    public int LoadGraph(int _read) {//R.drawable.ic_launcher (int時)
        try{
            ImageVector.add(BitmapFactory.decodeResource(r72, _read));
            return (ImageVector.size() - 1);
        }catch(Exception e){
            return -1;
        }
    }
    public int LoadGraph(String _FileName) {//(assetsフォルダに配置時)
        BufferedInputStream bis =null;
        try{
            bis = new BufferedInputStream(asset72.open(_FileName));
            ImageVector.add( BitmapFactory.decodeStream(bis));
            bis.close();
            return (ImageVector.size() - 1);
        }catch(Exception e){
            if(bis != null) try { bis.close();} catch (IOException e1) {}
            return -1;
        }
    }

    public int DeleteGraph(int _Handle) {
        if (AbleImageVector(_Handle)) {
            ImageVector.get(_Handle).recycle();
            ImageVector.set(_Handle, null);
            return 0;
        }
        return -1;
    }
    public int InitGraph() {
        if (ImageVector.size() > 0) {
            for (Bitmap arg : ImageVector) {
                if(arg!=null ){
                    if (!arg.isRecycled()) arg.recycle();
                    arg = null;
                }
            }
        }
        return 0;
    }

    public int DrawGraph(double _x, double _y, int _Handle, boolean _TransFlag) {
        return DrawGraph((int)_x, (int)_y, _Handle);
    }
    public int DrawGraph(int _x, int _y, int _Handle, boolean _TransFlag) {
        return DrawGraph(_x, _y, _Handle);
    }
    public int DrawGraph(int _x, int _y, int _Handle) {
        if (AbleImageVector(_Handle) && _x < Define72.WINDOW_WIDTH && _y <Define72.WINDOW_HEIGHT
                                     && _x + ImageVector.get(_Handle).getWidth() > 0 && _y + ImageVector.get(_Handle).getHeight() >0) {
            canvas72.drawBitmap(ImageVector.get(_Handle), _x, _y, Drawp72);
            return 0;
        }
        return -1;
    }
    public int DrawExtendGraph(double _x1, double _y1, double _x2, double _y2, int _Handle, boolean _TransFlag) {
        return DrawExtendGraph((int)_x1,(int) _y1, (int)_x2,(int) _y2, _Handle);
    }
    public int DrawExtendGraph(int _x1, int _y1, int _x2, int _y2, int _Handle, boolean _TransFlag) {
        return DrawExtendGraph(_x1, _y1, _x2, _y2, _Handle);
    }
    public int DrawExtendGraph(int _x1, int _y1, int _x2, int _y2, int _Handle) {
        if (AbleImageVector(_Handle)) {
            Bitmap image = ImageVector.get(_Handle);
            canvas72.drawBitmap(image, new Rect(0, 0, image.getWidth(), image.getHeight()), new Rect(_x1, _y1, _x2, _y2), Drawp72);
            return 0;
        }
        return -1;
    }
    public int DrawTurnGraph( int x, int y,int _Handle) {
        return DrawRotaGraph(x, y,1 ,0,_Handle , true );
    }
    public int DrawRotaGraph(int x, int y,double ExtRate, double Angle,int _Handle ,  boolean TurnFlag ) {
        if (AbleImageVector(_Handle)) {
            Bitmap image = ImageVector.get(_Handle);
            return DrawRotaGraph2(  x,  y, image.getWidth()/2, image.getHeight()/2, ExtRate, Angle,_Handle,  TurnFlag );
        }
        return -1;
    }
    public int DrawRotaGraph2( int x, int y,int cx, int cy,double ExtRate, double Angle,int _Handle,  boolean TurnFlag ) {
        return DrawRotaGraph3( x,  y, cx, cy, ExtRate,ExtRate, Angle, _Handle,  TurnFlag );
    }
    public int DrawRotaGraph3( int x, int y, int cx, int cy, double ExtRateX, double ExtRateY, double Angle, int _Handle, boolean TurnFlag ) {
        if (AbleImageVector(_Handle)) {
            Bitmap image = ImageVector.get(_Handle);
            Matrix mat = new Matrix();
            mat.postRotate((float)(180 * Angle/(Math.PI)),cx,cy);
            mat.postTranslate(image.getWidth()/ -2,image.getHeight()/ -2);
            if(TurnFlag )mat.postScale(-1,1);
            mat.postScale((float) ExtRateX, (float) ExtRateY);
            mat.postTranslate(x,y);
            //mat.postTranslate(image.getWidth()/ 2,image.getHeight()/ 2);
            canvas72.drawBitmap(image, mat, Drawp72);
            return 0;
        }
        return -1;

    }

    public int GetGraphWidth(int _Handle) {
        if (AbleImageVector(_Handle)) {
            return ImageVector.get(_Handle).getWidth();
        }
        return -1;
    }
    public int GetGraphHeight(int _Handle) {
        if (AbleImageVector(_Handle)) {
            return ImageVector.get(_Handle).getHeight();
        }
        return -1;
    }

    public void SetBackgroundColor(int _color) {
        BackColor72 = _color;
    }

    public int LoadGraphScreen(int _x, int _y, int _read, int TransFlag) {
        return LoadGraphScreen(_x, _y, _read);
    }
    public int LoadGraphScreen(int _x, int _y, int _read) {
        Bitmap image = BitmapFactory.decodeResource(r72, _read);
        canvas72.drawBitmap(image, _x, _y, null);
        image.recycle();
        image = null;
        return 0;
    }

    public int GetPixel(int _Handle, int _x, int _y) {
        if (AbleImageVector(_Handle)) {
            Bitmap image = ImageVector.get(_Handle);
            int width = image.getWidth();
            int height = image.getHeight();
            if (_x < 0 || _y < 0 || _x >= width || _y >= height) return -1;
            return image.getPixel(_x, _y);
        }
        return -1;
    }
    public int GetPixelSoftImage(int _Handle, int _x, int _y, Color72 _color72) {
        int Colour = GetPixel(_Handle, _x, _y);
        if (Colour == -1) return -1;
        _color72.red = Color.red(Colour);
        _color72.green = Color.green(Colour);
        _color72.blue = Color.blue(Colour);
        _color72.alpha = Color.alpha(Colour);
        return 0;
    }
    public int SetPixel(int _Handle, int _x, int _y, int _color) {
        if (AbleImageVector(_Handle)) {
            Bitmap image = ImageVector.get(_Handle);
            int width = image.getWidth();
            int height = image.getHeight();
            if (_x < 0 || _y < 0 || _x >= width || _y >= height) return -1;
            image.setPixel(_x, _y, _color);
            return 0;
        }
        return -1;
    }
    public int SetPixelSoftImage(int _Handle, int _x, int _y, Color72 _color72) {
        return SetPixel(_Handle, _x, _y, _color72.toColor());
    }

    public final static int DX_BLENDMODE_NOBLEND = 0;//　:　ノーブレンド（デフォルト）;
    public final static int DX_BLENDMODE_ALPHA =1;//　　:　αブレンド
    public int SetDrawBlendMode( int _BlendMode , int _Pal ) {
        if(_BlendMode == DX_BLENDMODE_ALPHA )Drawp72.setAlpha(_Pal);
        if(_BlendMode == DX_BLENDMODE_NOBLEND)Drawp72.setAlpha(255);
        return 0;
    }

    private boolean AbleImageVector(int _Handle) {
        return !(_Handle < 0 || _Handle >= ImageVector.size()
                || ImageVector.get(_Handle) == null || ImageVector.get(_Handle).isRecycled());
    }

    //文字関係//////////////////////////////////////////////////文字関係///////////////////////////////////////////////
    public int DrawFormatString(int _x, int _y, int _color, String _str) {
        return DrawString(_x, _y, _str, _color);
    }
    public int DrawString(int _x, int _y,  String _str,int _color,boolean b) {
        return DrawString(_x, _y, _str, _color);
    }
    public int DrawString(int _x, int _y, String _str, int _color) {
        fontp72.setColor(_color);
        return DrawString(_x, _y, _str);
    }
    public int DrawString(int _x, int _y, String _str) {
        canvas72.drawText(_str, _x, _y + fontp72.getTextSize(), fontp72);
        return 0;
    }
    public int SetFontSize(int _FontSize) {
        fontp72.setTextSize(_FontSize);
        return 0;
    }
    public int SetFontThickness(int _ThickPal) {
        fontp72.setStrokeWidth(_ThickPal);
        return 0;
    }
    public int GetDrawFormatStringWidth(String _str) {
        fontp72.measureText(_str);
        return 0;
    }
    public int SetFontAntiAlias(boolean _flag) {
        fontp72.setAntiAlias(_flag);
        return 0;
    }

    public int printfDx(String _str) {
        PrintfList.add(_str);
        return 0;
    }
    public int clsDx() {
        PrintfList.clear();
        return 0;
    }


    //図形関係  //////////////////////////////////////////////////図形関係/////////////////////////////////////////////
    public int DrawLine(float _x1, float _y1, float _x2, float _y2, int _color, int _Thickness) {
        diagp72.setStrokeWidth(_Thickness);
        return DrawLine(_x1, _y1, _x2, _y2, _color);
    }
    public int DrawLine(float _x1, float _y1, float _x2, float _y2, int _color) {
        diagp72.setColor(_color);
        return DrawLine(_x1, _y1, _x2, _y2);
    }
    public int DrawLine(float _x1, float _y1, float _x2, float _y2) {
        diagp72.setStyle(Paint.Style.STROKE);
        canvas72.drawLine(_x1, _y1, _x2, _y2, diagp72);
        return 0;
    }

    public int DrawBox(float _x1, float _y1, float _x2, float _y2, int _color, boolean _FillFlag) {
        if (_FillFlag) {
            diagp72.setStyle(Paint.Style.FILL);
        } else {
            diagp72.setStyle(Paint.Style.STROKE);
        }
        return DrawBox(_x1, _y1, _x2, _y2, _color);
    }
    public int DrawBox(float _x1, float _y1, float _x2, float _y2, int _color) {
        diagp72.setColor(_color);
        return DrawBox(_x1, _y1, _x2, _y2);
    }
    public int DrawBox(float _x1, float _y1, float _x2, float _y2) {
        canvas72.drawRect(_x1, _y1, _x2, _y2, diagp72);
        return 0;
    }

    public int DrawCircle(float _x, float _y, float _r, int _color, boolean _FillFlag) {
        if (_FillFlag) {
            diagp72.setStyle(Paint.Style.FILL);
        } else {
            diagp72.setStyle(Paint.Style.STROKE);
        }
        return DrawCircle(_x, _y, _r, _color);
    }
    public int DrawCircle(float _x, float _y, float _r, int _color) {
        diagp72.setColor(_color);
        return DrawCircle(_x, _y, _r);
    }
    public int DrawCircle(float _x, float _y, float _r) {
        canvas72.drawCircle(_x, _y, _r, diagp72);
        return 0;
    }

    // API　21　以上のため、消しておく
//    public int DrawOval(  float _x , float _y , float _rx , float _ry, int _color , boolean _FillFlag ) {
//        if (_FillFlag) {diagp72.setStyle(Paint.Style.FILL);}
//        else {diagp72.setStyle(Paint.Style.STROKE);}
//        diagp72.setColor( _color);
//        canvas72.drawOval(_x -_rx,_y -_ry,_x + _rx ,_y + _ry , diagp72);
//        return 0;
//    }

    public int DrawPixel(float _x, float _y, int _color) {
        diagp72.setColor(_color);
        diagp72.setStrokeWidth(1);
        canvas72.drawPoint(_x, _y, diagp72);
        return 0;
    }

    //音楽関係////////////////////////////////////////////////////音楽関係/////////////////////////////////////////
    private class MusicPlayer72{
        public MusicPlayer72(MediaPlayer _MP){
            MP72 = _MP;
            Stopping=false;
        }
        public MediaPlayer MP72;
        public boolean Stopping =false;

    }


    public int LoadSoundMem( int _FileName , int _PlayType) {
        try {
            if (_PlayType == DX_PLAYTYPE_SE) {
                int sID = sp72.load(context72, _FileName, 1);
                SoundVector.add(sID);
                return MULToSoundPoolNumber(SoundVector.size() - 1);
            } else {//this scope includes _PlayType == DX_PLAYTYPE_LOOP DX_PLAYTYPE_BGM_NOLOOP
                MediaPlayer mp = MediaPlayer.create(context72, _FileName);
                mp.seekTo(0);
                if (_PlayType == DX_PLAYTYPE_LOOP) mp.setLooping(true);
                else if (_PlayType == DX_PLAYTYPE_BGM_NOLOOP) mp.setLooping(false);
                MusicVector.add(new MusicPlayer72(mp));
                return MULToMediaPlayerNumber(MusicVector.size() - 1);
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public int LoadSoundMem( String _FileName , int _PlayType) {
        try {
            if (_PlayType == DX_PLAYTYPE_SE) {
                try {
                    AssetFileDescriptor afd = asset72.openFd(_FileName);
                    int sID = sp72.load(afd, 1);
                    SoundVector.add(sID);
                    return MULToSoundPoolNumber(SoundVector.size() - 1);
                } catch (IOException e) {
                    return -1;
                }

            } else {//this scope includes _PlayType == DX_PLAYTYPE_LOOP DX_PLAYTYPE_BGM_NOLOOP

                MediaPlayer mp = new MediaPlayer();
                try {
                    AssetFileDescriptor afd = asset72.openFd(_FileName);
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    afd.close();
                    mp.prepare();
                    mp.seekTo(0);
                    if (_PlayType == DX_PLAYTYPE_LOOP) mp.setLooping(true);
                    else if (_PlayType == DX_PLAYTYPE_BGM_NOLOOP) mp.setLooping(false);
                    MusicVector.add(new MusicPlayer72(mp));
                    return MULToMediaPlayerNumber(MusicVector.size() - 1);
                } catch (Exception e) {
                    return -1;
                }
            }
        } catch (Exception e) {
            return -1;
        }
    }


    //MediaPlayer::奇数返却 ((int)(n/2))番を使う
    //SoundPool::偶数返却 n/2 番を使う
    //　　　　　　→どちらも ((int)(n/2))番を使う。

    private final int DIVFromMusicNumber(int n){
        if(n < 0)return -1;
        else return ((int)(n / 2));
    }
    private final int MULToMediaPlayerNumber(int n){
        if(n<0)return -1;
        else return ((2 * n) + 1);
    }
    private final int MULToSoundPoolNumber(int n){
        if(n<0)return -1;
        else return (2 * n);
    }
    private final boolean IsSoundPool(int n){
        if((n % 2) == 0)return true;else return false;
    }

    //_PlayTypeはLoadSoundMemの時に指定することに
    public final static int DX_PLAYTYPE_SE =0;//バックグラウンド再生//SoundPoolを使う。効果音用.(先に読み込むため遅延なし)
    public final static int DX_PLAYTYPE_BACK = DX_PLAYTYPE_SE;//バックグラウンド再生//SoundPoolを使う。効果音と判断
    public final static int DX_PLAYTYPE_BGM =1;//ループ再生//MediaPlayerを使う。明らかにBGMと判断。(読み込みながら鳴らすので、最初と最後に少々の遅延)
    public final static int DX_PLAYTYPE_LOOP = DX_PLAYTYPE_BGM;//ループ再生//MediaPlayerを使う。明らかにBGMと判断。
    public final static int DX_PLAYTYPE_BGM_NOLOOP = 2;//MediaPlayerを使うが、ループしない。
    public final static boolean TRUE =true;
    public final static boolean FALSE =false;

    public int PlaySoundMem( int _SoundHandle  ) {return PlaySoundMem(_SoundHandle,true);}
    public int PlaySoundMem( int _SoundHandle , boolean _TopPositionFlag ) {
        int DIVedHandle = DIVFromMusicNumber(_SoundHandle);
        if(IsSoundPool(_SoundHandle)){
            if(AbleSoundVector(DIVedHandle)){
                sp72.play(SoundVector.get(DIVedHandle),  1.0F, 1.0F, 0, 0, 1.0F);
                //2,3引数　左、右スピーカーの音量。(0~1f)
                //5　ループ回数　（ -1 = 無限ループ。0 = ループなし ）
                //6 再生速度（ 0.5～2倍速 ）
                return 0;
            }return -1;
        }else{
            if(AbleMusicVector(DIVedHandle) && !MusicVector.get(DIVedHandle).MP72.isPlaying() ){
                MediaPlayer mp =MusicVector.get(DIVedHandle).MP72;
                if(_TopPositionFlag )mp.seekTo(0);
                mp.start();
                MusicVector.get(DIVedHandle).Stopping=false;
                return 0;
            }return -1;
        }

    }
    //効果音は再生中かは取得できない
    public int CheckSoundMem( int _SoundHandle ) {
        int DIVedHandle = DIVFromMusicNumber(_SoundHandle);
        if(IsSoundPool(_SoundHandle)){
            if(AbleSoundVector(DIVedHandle)){
                return -2;
            }return -1;
        }else{
            if(AbleMusicVector(DIVedHandle)){
                if(MusicVector.get(DIVedHandle).MP72.isPlaying())return 1;
                else return 0;
            }return -1;
        }
    }
    //効果音は一時停止するのが少し面倒なので現在一時停止できない。
    public int StopSoundMem( int _SoundHandle ) {
        int DIVedHandle = DIVFromMusicNumber(_SoundHandle);
        if(IsSoundPool(_SoundHandle)){
            if(AbleSoundVector(DIVedHandle)){
                //sp72.pause(SoundVector.get(DIVedHandle));
                return -1;
            }return -1;
        }else{
            if(AbleMusicVector(DIVedHandle)){
                MediaPlayer mp = MusicVector.get(DIVedHandle).MP72;
                mp.pause();
                MusicVector.get(DIVedHandle).Stopping= true;
                return 0;
            }return -1;
        }
    }
    public int DeleteSoundMem( int _SoundHandle ) {
        int DIVedHandle = DIVFromMusicNumber(_SoundHandle);
        if(IsSoundPool(_SoundHandle)){
            if(AbleSoundVector(DIVedHandle)){
                sp72.unload(SoundVector.get(DIVedHandle));
                SoundVector.set(DIVedHandle , null);
            }return -1;
        }else{
            if(AbleMusicVector(DIVedHandle)){
                MediaPlayer mp =  MusicVector.get(DIVedHandle ).MP72;
                mp.release();
                mp =null;
                return 0;
            }return -1;
        }
    }
    public int InitSoundMem(  ) {
        if (SoundVector.size() > 0) {
            for (Integer arg : SoundVector) {//拡張forでもnullチェックしましょう
                if(arg != null){
                    sp72.stop(arg);
                    sp72.unload(arg);
                    arg = null;
                }
            }
        }
        sp72.release();

        if (MusicVector.size() > 0) {
            for (MusicPlayer72 arg : MusicVector) {
                if(arg!=null &&  arg.MP72 != null) {
                    if (arg.MP72.isPlaying()) arg.MP72.stop();
                    arg.MP72.release();
                    arg.MP72 = null;
                    arg.Stopping =false;
                }
            }
        }
        return 0;

    }
    public int ALLStopSoundMem(){
            for(int i =0; i <MusicVector.size();i++){
                StopSoundMem(MULToMediaPlayerNumber(i));
            }
        return 0;
    }
    public void AllResumeSoundMem() {
        for(int i =0; i <MusicVector.size();i++){
            if(MusicVector.get(i).Stopping )PlaySoundMem(MULToMediaPlayerNumber(i),false);
        }
    }


    public int PlayMovie(String _MovieName){
        int n = LoadSoundMem(_MovieName ,DX_PLAYTYPE_BGM_NOLOOP);
        if(n== -1){return -1;}
        else{
            int DIVedHandle = DIVFromMusicNumber(n);
            MediaPlayer mp =MusicVector.get(DIVedHandle).MP72;
            mp.setDisplay(holder72);
            mp.seekTo(0);
            mp.start();
            return 0;
        }
    }

    public void onCompletion(MediaPlayer mp){

    }
    private boolean AbleMusicVector(int _Handle) {
        return !(_Handle < 0 || _Handle >= MusicVector.size()
                || MusicVector.get(_Handle).MP72 == null );
    }
    private boolean AbleSoundVector(int _Handle) {
        return !(_Handle < 0 || _Handle >= SoundVector.size()
                || SoundVector.get(_Handle) == null );
    }
    //タッチイベント関連///////////////////////////////////タッチイベント関連//////////////////////////////////////////
    private HashMap<String ,PointF> Touchpoints72 = new HashMap<String ,PointF>();

    private class TouchMove8{
        public boolean TouchStarted =false;
        PointF DefaultXY = new PointF(0,0);
        PointF SubXY= new PointF(0,0);
        PointF NowTouchXY= new PointF(0,0);
        public PointF ScrollXY = new PointF(0,0);
        public PointF FlingXY = new PointF(0,0);
        void TouchProcess(){
            Object[]keys = Touchpoints72.keySet().toArray();
            if(keys.length > 0){
                if(!TouchStarted){
                    TouchStarted =true;
                    DefaultXY = new PointF(Touchpoints72.get(keys[0]).x,Touchpoints72.get(keys[0]).y);
                    SubXY = new PointF(0,0);
                    NowTouchXY = new PointF(DefaultXY.x,DefaultXY.y);
                }else{
                    SubXY =new PointF(Touchpoints72.get(keys[0]).x - DefaultXY.x,
                                       Touchpoints72.get(keys[0]).y - DefaultXY.y)  ;
                    NowTouchXY = new PointF(Touchpoints72.get(keys[0]).x,Touchpoints72.get(keys[0]).y);
                }
            }else{
                TouchStarted =false;
                DefaultXY = new PointF(0,0);
                SubXY = new PointF(0,0);
                NowTouchXY= new PointF(0,0);
            }
            FlingXY = new PointF(0,0);
            ScrollXY = new PointF(0,0);
        }

        double Theta(){
            if(TouchStarted && Define72.MOVE_OVER_RADIOUS < SubXY.length()){
                  return Math.atan2(SubXY.y,SubXY.x); //-pi~pi
            }
            else return 72;
        }
        boolean Direction4(double theta , int Code){//-pi~pi
            switch(Code){
                case MOVE_RIGHT:
                    return (-2 * Math.PI/6 <theta && theta < 2 * Math.PI/6);
                case MOVE_DOWN:
                    return (1 * Math.PI/6 <theta && theta < 5 * Math.PI/6);
                case MOVE_LEFT:
                    return (4 * Math.PI/6 <theta || theta < -4 * Math.PI/6);
                case MOVE_UP:
                    return (-5 * Math.PI/6 <theta && theta < -1 * Math.PI/6);
                default:
                    return false;
            }
        }

        boolean AngleOK(int Code){//0<= n <= 3 LURDの順
            if(FlingXY.length()>0){
                double th = Math.atan2(FlingXY.y,FlingXY.x);
                return Direction4(th,Code);
            }
            else if(ScrollXY.length() > Define72.SCROLL_OVER_RADIOUS){
                double th = Math.atan2(ScrollXY.y,ScrollXY.x);
                return Direction4(th,Code);
            }
            else if(TouchStarted){
                double th = Theta();//-pi~pi
                if(th == 72)return false;
                return Direction4(th,Code);
            }else return false;
        }
        boolean  FlingAngleOK (int Code){
            if(FlingXY.length()>0){
                double th = Math.atan2(FlingXY.y,FlingXY.x);
                return Direction4(th,Code -10 );
            }
            else return false;
        }
        boolean ScrollAngleOK(int Code){
            if(ScrollXY.length() > Define72.SCROLL_OVER_RADIOUS){
                double th = Math.atan2(ScrollXY.y,ScrollXY.x);
                return Direction4(th,Code -14 );
            }
            else return false;
        }
    }


    private float RelativeX(float x){return x * Define72.WINDOW_WIDTH /getWidth();}
    private float RelativeY(float y){return y *Define72.WINDOW_HEIGHT/getHeight()  ;}
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        int count = event.getPointerCount();
        int index = event.getActionIndex();
        int pointerID = event.getPointerId(index);
        switch(action&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Touchpoints72.put(""+pointerID,new PointF(RelativeX(event.getX()),RelativeY(event.getY())));break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                Touchpoints72.remove(""+pointerID);break;
            case MotionEvent.ACTION_MOVE:
                for(int i=0;i<count;i++){
                    PointF pos = Touchpoints72.get(""+event.getPointerId(i));
                    pos.x =RelativeX( event.getX());
                    pos.y =RelativeY( event.getY());
                }
                break;

        }
        gesture72.onTouchEvent(event);
        invalidate();
        return true;
    }
    public boolean onDown(MotionEvent e){GestureStore.DOWNGESTURE = GestureStore.TOUCHINT(GestureStore.DOWNGESTURE) ; return false;}
    public void onShowPress(MotionEvent e){GestureStore.SHOWPRESS = GestureStore.TOUCHINT(GestureStore.SHOWPRESS);return ;}
    public boolean onSingleTapUp(MotionEvent e){GestureStore.UPGESTURE = GestureStore.TOUCHINT(GestureStore.UPGESTURE);return false ;}
    public void onLongPress(MotionEvent e){GestureStore.LONGPRESS = GestureStore.TOUCHINT(GestureStore.LONGPRESS);return ;}
    public boolean onSingleTapConfirmed(MotionEvent e){GestureStore.SINGLETAP = GestureStore.TOUCHINT(GestureStore.SINGLETAP); return false;}
    public boolean onDoubleTap(MotionEvent e){GestureStore.DOUBLETAP = GestureStore.TOUCHINT(GestureStore.DOUBLETAP); return false;}
    public boolean onFling(MotionEvent e0,MotionEvent e1 ,float vx,float vy){
        TM872.FlingXY = new PointF(vx,vy);
        if(TM872.FlingAngleOK(FLING_LEFT))GestureStore.FLINGLEFT = GestureStore.TOUCHINT(GestureStore.FLINGLEFT);
        if(TM872.FlingAngleOK(FLING_UP))GestureStore.FLINGUP = GestureStore.TOUCHINT(GestureStore.FLINGUP);
        if(TM872.FlingAngleOK(FLING_RIGHT))GestureStore.FLINGRIGHT = GestureStore.TOUCHINT(GestureStore.FLINGRIGHT);
        if(TM872.FlingAngleOK(FLING_DOWN))GestureStore.FLINGDOWN = GestureStore.TOUCHINT(GestureStore.FLINGDOWN);
        return false;
    }
    public boolean onScroll(MotionEvent e0,MotionEvent e1 ,float dx,float dy){TM872.ScrollXY = new PointF(dx,dy); return false;}
    public boolean onDoubleTapEvent(MotionEvent e){ return false;}
    private void InitTouch(){
        GestureStore.Init();
    }
    public boolean Touched(){return TM872.TouchStarted;}

//    TOUCH =0
//    LOOP00
//    |  EVENT1 ->false
//    |  TOUCH  ->1
//    |  EVENT2 ->false
//    |  INIT  ->2
//    LOOP01
//    |  EVENT1 -true
//    |  TOUCH  ->2
//    |  EVENT2 ->true
//    |  INIT  ->0
//

    public final static int KEY_INPUT_RIGHT = 0;// 左キー
    public final static int KEY_INPUT_DOWN =1;// 上キー
    public final static int KEY_INPUT_LEFT =2;// 右キー
    public final static int KEY_INPUT_UP =3;	// 下キー
    public final static int KEY_INPUT_RETURN =4;// エンターキー GESTURE_SHOWPRESS…所謂軽くタッチ。
    public final static int KEY_INPUT_SPACE =5;// スペースキー  GESTURE_DOWN 普通に触ったら大抵すぐに呼び出される。
    public final static int KEY_INPUT_Z =6;// Zキー GESTURE_SINGLETAP…所謂ちょんタッチ
    public final static int KEY_INPUT_X =7;// Xキー GESTURE_DOUBLETAP 二回連続でちょんちょんっとタッチ
    public final static int KEY_INPUT_C = 8;// Cキー　GESTURE_LONGPRESS 結構長く押したとき。
    public final static int KEY_INPUT_BACK = 8;//BackSpaceキー GESTURE_LONGPRESS 結構長く押したとき。
    public final static int KEY_INPUT_V = 9;//Vキー GESTURE_UP フリックでもなく、ロングブレスでもなく上げたとき。

    //画面の向きによって90度変わることに注意
    //指二個だと、SCROLLしかよばれないのに注意。
    //　MOVE_LEFT = KEY_INPUT_LEFT 基準の位置からのTouch座標の位置の差で8分割区域評価。値は0～255(SCROLLだと、端で0になってしまう)
    //  GESTURE_LEFT_FLING = FLING_KEY_INPUT_LEFT = KEY_INPUT_D 値は true false スワイプみたいな時の。

//常時SCROLLが呼ばれるので専用の関数を作る。(前回との差分を示す。)
//入力中の指の数を取得する。（2個以上では、座標は同じになる。またジェスチャーはSCROLLのみになるため。）
//タッチしているか、タッチしているならどこかの関数。このくらいでいいでしょ。

    public static final int MOVE_RIGHT =0;//基準の位置からのTouch座標の位置の差で8分割区域評価。値は0～255(SCROLLだと、端で0になってしまう)
    public static final int MOVE_DOWN =1;//↑true false で変換して返すこともできる
    public static final int MOVE_LEFT =2;
    public static final int MOVE_UP=3;
    public static final int GESTURE_SHOWPRESS =4;//…所謂軽くタッチ。
    public static final int GESTURE_DOWN =5;// 普通に触ったら大抵すぐに呼び出される。
    public static final int GESTURE_SINGLETAP=6;//…所謂ちょんタッチ
    public static final int GESTURE_DOUBLETAP=7;//二回連続でちょんちょんっとタッチ
    public static final int GESTURE_LONGPRESS =8;//結構長く押したとき
    public static final int GESTURE_UP =9;//フリックでもなく、ロングブレスでもなく上げたとき。
    public static final int FLING_RIGHT =10;//フリック系
    public static final int FLING_DOWN =11;//フリック系
    public static final int FLING_LEFT =12;//フリック系
    public static final int FLING_UP =13;//フリック系
    public static final int SCROLL_LEFT =14;//スクロール系
    public static final int SCROLL_UP =15;//スクロールは、Fling系と逆。
    public static final int SCROLL_RIGHT =16;//スクロール系
    public static final int SCROLL_DOWN =17;//スクロール系
    public boolean CheckGesture(int GestureCode){
        switch(GestureCode){
            case  MOVE_LEFT:case MOVE_RIGHT:case MOVE_DOWN :case MOVE_UP :return TM872.AngleOK(GestureCode);
            case GESTURE_SHOWPRESS:return GestureStore.INT3BOOL2( GestureStore.SHOWPRESS);
            case GESTURE_DOWN:return GestureStore.INT3BOOL2( GestureStore.DOWNGESTURE);
            case GESTURE_SINGLETAP:return GestureStore.INT3BOOL2( GestureStore.SINGLETAP);
            case GESTURE_DOUBLETAP:return GestureStore.INT3BOOL2( GestureStore.DOUBLETAP);
            case GESTURE_LONGPRESS:return GestureStore.INT3BOOL2( GestureStore.LONGPRESS);
            case GESTURE_UP:return GestureStore.INT3BOOL2( GestureStore.UPGESTURE);
            case FLING_RIGHT:return GestureStore.INT3BOOL2( GestureStore.FLINGRIGHT);
            case FLING_DOWN:return GestureStore.INT3BOOL2( GestureStore.FLINGDOWN);
            case FLING_UP :return GestureStore.INT3BOOL2( GestureStore.FLINGUP);
            case FLING_LEFT :return GestureStore.INT3BOOL2( GestureStore.FLINGLEFT);
            case SCROLL_RIGHT:case SCROLL_DOWN:case SCROLL_UP :case SCROLL_LEFT :return TM872.ScrollAngleOK(GestureCode);
        }
        return false;
    }
    public boolean CheckHitKey(int KeyCode){return CheckGesture(KeyCode);}

    public int GetTouchPointX(){return  GetMousePointX( ); }
    public int GetTouchPointY(){return  GetMousePointY( ); }
    public int GetMousePointX( ){
        //printfDx("X::" +TM872.NowTouchXY.x );
        return (int)(TM872.NowTouchXY.x);
        //else return -1;
    }
    public int GetMousePointY( ){
        return (int)(TM872.NowTouchXY.y);//if(TM872.TouchStarted)
        //else return -1;
    }

    private static class GestureStore{
        GestureStore(){};
        static int DOWNGESTURE =0;
        static int UPGESTURE =0;
        static int SHOWPRESS =0;
        static int LONGPRESS =0;
        static int SINGLETAP =0;
        static int DOUBLETAP =0;
        static int FLINGDOWN = 0;
        static int FLINGUP = 0;
        static int FLINGLEFT = 0;
        static int FLINGRIGHT = 0;
        static void Init(){
            DOWNGESTURE=SHIFTINIT(DOWNGESTURE);
            UPGESTURE=SHIFTINIT(UPGESTURE);
            SHOWPRESS=SHIFTINIT(SHOWPRESS);
            LONGPRESS=SHIFTINIT(LONGPRESS);
            SINGLETAP =SHIFTINIT(SINGLETAP);
            DOUBLETAP =SHIFTINIT(DOUBLETAP);
            FLINGUP =SHIFTINIT(FLINGUP);
            FLINGDOWN =SHIFTINIT(FLINGDOWN);
            FLINGLEFT =SHIFTINIT(FLINGLEFT);
            FLINGRIGHT =SHIFTINIT(FLINGRIGHT);

        }
        static int SHIFTINIT(int GES){if(GES==0)return 0;if(GES==1)return 2;if(GES==2)return 0;return 0;}
        static int TOUCHINT(int GES){if ((GES + 1) >2) return 2;else return 1;}
        static boolean INT3BOOL2(int GES){if(GES ==2)return true;else if(GES == 1 || GES == 0)return false;else return false;}
    }

    //ファイル入出力関係/////////////////////////////////////////////////////////ファイル入出力関係//////////////////////
    //FileRead系関数は読み書き可能な、assetsフォルダ以下にあるものを読み込むものです。//////////////////////////////
    public int FileRead_open(String _FileName) {
        try {
            INSLIST.add(asset72.open(_FileName));
            BUFRLIST.add(new BufferedReader(new InputStreamReader(INSLIST.get(INSLIST.size() - 1),"UTF-8")));
            return (INSLIST.size() - 1);
        } catch (Exception e) {
            return -1;
        }
    }
    //DataFile系関数は読み書き可能な、アプリ内でdataフォルダ以下に作られるものです。///////////////////////////////////////////////
    private ArrayList<InputStream> INSLIST = new ArrayList<InputStream>();
    private ArrayList<BufferedReader> BUFRLIST = new ArrayList<BufferedReader>();
    public int DataFileRead_open(String _FileName) {
        try {
            INSLIST.add(context72.openFileInput(_FileName));
            BUFRLIST.add(new BufferedReader(new InputStreamReader(INSLIST.get(INSLIST.size() - 1),"UTF-8")));
            return (INSLIST.size() - 1);
        } catch (Exception e) {
            return -1;
        }
    }
    public int FileRead_close(int _FileHandle) {
        try {
            if(AbleINSBURF(_FileHandle)){
                InputStream ins = INSLIST.get(_FileHandle);
                ins.close();
                ins = null;
                BufferedReader br = BUFRLIST.get(_FileHandle);
                br.close();
                br =null;
                return 0;
            }return -1;

        } catch (Exception e) {
            return -1;
        }
    }
    public int FileRead_size(String _FileName) {
        try {
            InputStream ins = context72.openFileInput(_FileName);
            int retu = ins.available();
            ins.close();
            return retu;
        } catch (Exception e) {
            return -1;
        }
    }
    public int FileRead_gets(StringBuffer _Buf, int _FileHandle) {//一行読み込み(末尾に改行無し)
        try {
            if(AbleINSBURF(_FileHandle)){
                BufferedReader br = BUFRLIST.get(_FileHandle);
                if (br == null) {_Buf =null; return -1;}
                String str =br.readLine() ;
                _Buf.delete(0,_Buf.length());
                _Buf.append(str);
                //_Buf = new StringBuffer( br.readLine() + "\n");
                return 0;
            }return -1;
        } catch (Exception e){
            return -1;
        }
    }
    public int FileRead_getc( int _FileHandle ) {//一文字読み込み
        try {
            if(AbleINSBURF(_FileHandle)){
                BufferedReader br = BUFRLIST.get(_FileHandle);
                if (br == null) { return -1;}
                return  br.read() ;
            }return -1;
        } catch (Exception e){return -1;}
    }
    public boolean FileRead_eof(int _FileHandle ){
        try {
            if(AbleINSBURF(_FileHandle)){
                BufferedReader br = BUFRLIST.get(_FileHandle);
                return (br == null) ;
            }return true;
        } catch (Exception e){return true;}
    }
    private boolean AbleINSBURF(int _Handle){
        return !(_Handle < 0 || _Handle >= INSLIST.size() || INSLIST.get(_Handle) == null);
    }

    private ArrayList<OutputStream> OUTSLIST = new ArrayList<OutputStream>();
    private ArrayList<BufferedWriter> BUFWLIST = new ArrayList<BufferedWriter>();
    public int DataFileWrite_open(String _FileName) {return DataFileWrite_open(_FileName, false); }
    public int DataFileWrite_open(String _FileName,boolean AppendFlag) {
        try {
            if(AppendFlag)OUTSLIST.add(context72.openFileOutput(_FileName, Context.MODE_APPEND));//MODE_APPEND なら追加書き込み
            else OUTSLIST.add(context72.openFileOutput(_FileName, Context.MODE_PRIVATE));//新たに一から作るか
            BUFWLIST.add(new BufferedWriter(new OutputStreamWriter((OUTSLIST.get(OUTSLIST.size() - 1)))));
            return (OUTSLIST.size() - 1);
        } catch (Exception e) {
            return -1;
        }
    }
    public int DataFileWrite_close(int _FileHandle) {
        try {
            if(AbleOUTSBUWF(_FileHandle)){
                OutputStream outs = OUTSLIST.get(_FileHandle);
                outs.close();
                outs = null;
                BufferedWriter bw = BUFWLIST.get(_FileHandle);
                bw.close();
                bw =null;
                return 0;
            }return -1;

        } catch (Exception e) {
            return -1;

        }
    }
    public int DataFileWrite_sets(String _Str, int _FileHandle,boolean _putnewline) {//一行書き出し(末尾に改行有)
        try {
            if(AbleOUTSBUWF(_FileHandle)){
                BufferedWriter bw = BUFWLIST.get(_FileHandle);
                if (_Str == null) { return -1;}
                bw.write(_Str);
                bw.flush();
                if(_putnewline) bw.newLine();
                return 0;
            }return -1;
        } catch (Exception e){
            return -1;
        }
    }
    public int DataFileWrite_setc(Byte _Str, int _FileHandle){
        try {
            if(AbleOUTSBUWF(_FileHandle)){
                BufferedWriter bw = BUFWLIST.get(_FileHandle);
                if (_Str == null) { return -1;}
                bw.write( _Str);
                bw.flush();
                return 0;
            }return -1;
        } catch (Exception e){
            return -1;
        }
    }//一行書き込み
    private boolean AbleOUTSBUWF(int _Handle){
        return !(_Handle < 0 || _Handle >= OUTSLIST.size() || OUTSLIST.get(_Handle) == null);
    }//一文字書き込み

    //全体的に読み書きする場合
    public String DataFileRead(String _FileName) {//現在同期読み込みのみ
    try {
        return DataFiletoData(_FileName).toString();
    } catch (Exception e) {
        return null;
    }
}
    private byte[] DataFiletoData(String _FileName) throws Exception {
        int size = 0;
        byte[] w = new byte[1024];
        InputStream ins = null;
        ByteArrayOutputStream outs = null;
        try {
            ins = context72.openFileInput(_FileName);
            outs = new ByteArrayOutputStream();
            while (true) {
                size = ins.read(w);
                if (size <= 0) break;
                outs.write(w, 0, size);
            }
            outs.close();
            ins.close();
            return outs.toByteArray();
        } catch (Exception e) {
            try {
                if (ins != null) ins.close();
                if (outs != null) outs.close();
            } catch (Exception e2) {
            }
            throw e;
        }
    }
    public int DataFileWrite(String _str, String _FileName) {
        try {
            DatatoDataFile(_str.getBytes(), _FileName);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
    private void DatatoDataFile(byte[] w, String _FileName) throws Exception {
        OutputStream outs = null;
        try {
            outs = context72.openFileOutput(_FileName, Context.MODE_PRIVATE);
            outs.write(w, 0, w.length);
            outs.close();
        } catch (Exception e) {
            try {
                if (outs != null) outs.close();
            } catch (Exception e2) {
            }
            throw e;
        }

    }
    //その他///////////////////////////////////////////////////////////////////////////////////その他////////////////////
    public int GetRand( int _RandMax ) { return rnd72.nextInt(_RandMax + 1);}
    public int SRand( int _Seed ) {rnd72 = new Random (_Seed); return 0;}
    public long GetNowCount( ){return  currentTimeMillis();}
    public long  GetNowHiPerformanceCount( ) {return  (long)(System.nanoTime()/ 1000);}
    public long  GetNowSuperHiPerformanceCount( ) {return  System.nanoTime();}
    public int GetDateTime( DATEDATA _DateBuf ) {
        Calendar cal= Calendar.getInstance();
        cal.setTimeInMillis(currentTimeMillis());
        _DateBuf.Year=cal.get(Calendar.YEAR);
        _DateBuf.Mon=cal.get(Calendar.MONTH);
        _DateBuf.Day=cal.get(Calendar.DAY_OF_MONTH);
        _DateBuf.Min=cal.get(Calendar.MINUTE);
        _DateBuf.Sec=cal.get(Calendar.SECOND);
        return 0;
    }
    public static int GetColor( int _Red , int _Green , int _Blue ,int _Alpha) {return Color.argb(_Alpha, _Red, _Green, _Blue); }
    public static int GetColor( int _Red , int _Green , int _Blue ) {return Color.rgb(_Red, _Green, _Blue); }

    public void CallToast(final String _str ,boolean _LongFlag){
        if(_LongFlag) {
            handler72.post(new Runnable() {public void run() {
                Toast toast = Toast.makeText(context72,_str,Toast.LENGTH_LONG);
                               toast.show();} }
            );
        }else {
            handler72.post(new Runnable() {public void run() {
                               Toast toast = Toast.makeText(context72,_str,Toast.LENGTH_SHORT);
                               toast.show();} }
            );
        }
    }
    public void CallDialog(final String _str,final String _Title){
        handler72.post(new Runnable() {public void run() {
        AlertDialog.Builder ad = new AlertDialog.Builder(context72);
        ad.setTitle(_Title);
        ad.setMessage(_str);
        ad.setPositiveButton("OK",null);
        ad.show();} });
    }


    //内部処理関数
    private void DrawPrintfDx(){
        if(PrintfList.size()>0){
            int i =0;
            for(String arg : PrintfList) {
                canvas72.drawText(arg, 20, 16 * i + 16,Printfp72 );
                i++;
            }
        }
        while(PrintfList.size () > (int)(Define72.WINDOW_HEIGHT / 16  )){
            PrintfList.remove(0);
        }
    }
}