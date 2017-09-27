package cn.luo.yuan.encode.number;

/**
 * Copyright @Luo
 * Created by Gavin Luo on 8/4/2017.
 */
public interface EncodeNumber {
    Number getValue();
    void setValue(Number value);
    void setFormatter(ValueStringFormatter foramtter);
}
