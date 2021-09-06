package com.daqsoft.busquery.bean;


/**
 * 车次对象
 * @author MouJunFeng
 * @time 2018-4-9.
 */

public class TrainCodeBean  {
    /**
     * //	车次编码	string	@mock=$order('240000K57113','2400000Z7109','240000Z1330K','2400000Z650K','240000Z1070C','2400000Z670M','240000K1051N')
     */
    private String trainNo;
    /**
     * //	车次
     */
    private String trainNum;

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }
}
