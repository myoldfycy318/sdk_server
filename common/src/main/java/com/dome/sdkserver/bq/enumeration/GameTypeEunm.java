package com.dome.sdkserver.bq.enumeration;

/**
 * GameTypeEunm
 *
 * @author Zhang ShanMin
 * @date 2016/10/18
 * @time 9:54
 */
public enum GameTypeEunm {

    APP_GAME,//手游
    WEB_GAME,//页游
    BW_H5,//宝玩H5游戏
    BQ_H5 //冰趣H5游戏
    ;

    /**
     * 获取游戏类型
     *
     * @param appCode
     * @return
     */
    public static GameTypeEunm getGameType(String appCode) {
        if (appCode.matches("^(H)\\d+")) {
            return BW_H5;
        } else if (appCode.matches("^(HD)\\d+")) {
            return BQ_H5;
        } else if (appCode.matches("^(Y)\\d+")) {
            return WEB_GAME;
        } else if (appCode.matches("^(D)\\d+")) {
            return APP_GAME;
        }
        return null;
    }
}
