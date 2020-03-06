package com.demo.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CityBO {
    // 城市ID
    private Integer cityId;
    // 城市名
    private String cityName;
}

public class ListToMapDemo {

    static Map<Integer, String> cityNameById = new HashMap<>();

    static List<CityBO> cityInfos = Arrays.asList(
            new CityBO(1, "北京市"),
            new CityBO(1, "北京市"),
            new CityBO(2, "上海市"));

    static void convert1() {
        for (CityBO cityInfo : cityInfos) {
            cityNameById.put(cityInfo.getCityId(), cityInfo.getCityName());
        }
    }

    static void convert2() {
        cityNameById = cityInfos.stream().collect(Collectors.toMap(CityBO::getCityId, CityBO::getCityName));
    }

    // 通过使用新值的合并函数来解决键重复问题
    // (oldData, newData) -> newData 表示使用新值代替旧值
    static void convert3() {
        cityNameById = cityInfos.stream().collect(Collectors.toMap(CityBO::getCityId, CityBO::getCityName, (oldData, newData) -> newData));
    }

    public static void main(String[] args) {
        //convert1(); // 运行成功
        //convert2(); // 运行失败，错误信息：java.lang.IllegalStateException: Duplicate key 1 (attempted merging values 北京市 and 北京市)
        convert3(); // 运行成功
        System.out.println(cityNameById); // 结果：{1=北京市, 2=上海市}
    }

}