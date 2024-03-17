package com.telerikacademy.oop.trelloboard;

import com.telerikacademy.oop.trelloboard.core.TrelloBoardEngine;
import com.telerikacademy.oop.trelloboard.core.contracts.Engine;

public class Startup {
    public static void main(String[] args){
        //Engine engine = new TrelloBoardEngine();
        //engine.start();
        base d = new derived();
        d.func1();
    }
}

abstract class base{
    public void func1(){
        func2();
    }
    public void func2(){
        System.out.println(" I am in base:func2() \n");
    }

}

class derived extends base{
    public void func1(){
        super.func1();
    }
    public void func2(){
        System.out.println(" I am in derived:func2() \n");
    }
};