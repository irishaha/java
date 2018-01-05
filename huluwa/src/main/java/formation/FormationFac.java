package formation;

import base.Position;

public class FormationFac {
    public Formation create(int type,Position head,int dir) {
        switch (type) {
            case 0:
                return new HeYi(head,dir);
            case 1:
                return new YanXing(head,dir);
            case 2:
                return new ChongE(head,dir);
            case 3:
                return new Changshe(head,dir);
            case 4:
                return new YuLin(head,dir);
            case 5:
                return new FangYuan(head,dir);
            case 6:
                return new YanYue(head,dir);
            case 7:
                return new FengShi(head,dir);
            default:
                System.err.println("阵型生成失败！");
                return null;
        }
    }
}
