package com.marvin.reference;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-07-07 20:54
 **/
public class Wife {
    private Husband husband;
    private IMother mother; // 婆婆
    public String queryHusband() {
        return "Wife.husband、Mother.callMother:" + mother.callMother();
    }
}
