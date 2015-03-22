package ;
//↑自分のProjectに合わせる

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class ForDxLib72Activity extends Activity {

    private Main72 M72;
    private boolean bFIRST =true;


    @Override
    protected void onCreate(Bundle bundle) {
        //画面の向き固定
        if(Define72.WINDOW_WIDTH > Define72.WINDOW_HEIGHT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        M72 = new Main72(this);
        setContentView(M72);
    }


    @Override
    public void onResume(){
        super.onResume();
        if(!bFIRST) M72.AllResumeSoundMem();
        bFIRST=false;
    }
    @Override
    public void onPause(){
        M72.ALLStopSoundMem();
        super.onPause();
    }
    @Override
    public void onDestroy(){
        M72.InitSoundMem();
        M72.InitGraph();
        super.onDestroy();
    }

}
