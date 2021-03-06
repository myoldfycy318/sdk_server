package com.dome.sdkserver.constants;


import java.util.ArrayList;
import java.util.List;

public enum PaySwitchEnum {
    /**
     * 支付方式 默认为钱宝和支付宝.
     */
      支付宝(1),钱宝(2),银联(3),微信(4);

      private int status;

    private PaySwitchEnum(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static String getName (int status){
        for(PaySwitchEnum e: PaySwitchEnum.values()){
            if(e.getStatus()==status){
                return e.name();
            }
        }
        return null;
    }

    public static List<Integer> getAllStatus(){
        List<Integer> list = new ArrayList<Integer>();
        for(PaySwitchEnum e : PaySwitchEnum.values()){
            list.add(e.getStatus());
        }
        return list;
    }

    public static boolean inStatus(Integer status){
        List<Integer> list = getAllStatus();
        boolean flag = false;
        for(Integer i : list){
            if(i==status){
                flag = true;
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        System.out.println(getAllStatus());
    }
}


