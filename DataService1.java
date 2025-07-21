package com.learning.spring_aop.data;

import org.springframework.stereotype.Repository;

@Repository
public class DataService1 {
    public int[] retrieveData(){
        return new int[]{4,5,6,9,10,7,8,1,2,3};
    }
}
