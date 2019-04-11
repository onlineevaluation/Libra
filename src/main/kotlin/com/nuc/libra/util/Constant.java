package com.nuc.libra.util;

/**
 * @author 杨晓辉 2019/4/11 14:52
 */
public class Constant {

    public static final int TOTAL_SCORE = 100;
    /*选择题  类型 1 */
    public static final int SELECT = 5;
    public static final String SELECT_TYPE = "选择题";
    public static final int SELECT_SCORE = 6;
    /*填空题  类型 2 */
    public static final int EXERCISE  = 3;
    public static final String EXERCISE_TYPE  = "填空题";
    public static final int EXERCISE_SCORE = 8;

    /*简答题  类型 3 */
    public static final int SHORT_ANSWER = 1;
    public static final String SHORT_ANSWER_TYPE = "简答题";
    public static final int SHORT_SCORE = 20;

    /*代码题  类型 4 */
    public static final int CODE = 1;
    public static final String CODE_TYPE = "代码题";
    public static final int CODE_SCORE = 24;


    public static int scoreFactory(String type){
        if (type.equals("填空题")) return EXERCISE_SCORE;
        else if (type.equals("选择题")) return SELECT_SCORE;
        else if (type.equals("简答题")) return SHORT_SCORE;
        else if (type.equals("代码题")) return CODE_SCORE;
        else return 0;
    }


}
