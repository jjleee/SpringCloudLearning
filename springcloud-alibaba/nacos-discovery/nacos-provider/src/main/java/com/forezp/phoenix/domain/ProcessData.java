package com.forezp.phoenix.domain;

import lombok.Data;
import org.junit.Test;

@Data
public class ProcessData {
    String rowKey;
    Double batteryTemperature;
    Integer lineNo;
    Integer cabNo;
    Integer channelNo;
    Double capacity;
    Integer cellNo;
    Double current;
    Long currentTime;
    Double energy;
    Integer funcCode;
    Integer loopNo;
    Double povl;
    Double ratio;
    Integer runState;
    Long runTime;
    Integer stepNo;
    Integer stepType;
    Integer sumStep;
    Double voltage;
}
