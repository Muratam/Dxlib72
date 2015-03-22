package chy72.whatevernamewillbeavailable;

public class Cirno {
    Dxlib72 D;
    private int Posx=0;
    private int GraphHandle =0;

    public Cirno(Dxlib72 _D ,int _x ,int _GraphHandle){//コンストラクタ
        D =_D;//Main72以外のクラスでDxlib72を使用するために、参照を得る
        Posx =_x;
        GraphHandle =_GraphHandle;
    }
    public void Draw(){
        D.DrawGraph(Posx ,100 ,GraphHandle);
    }

}
