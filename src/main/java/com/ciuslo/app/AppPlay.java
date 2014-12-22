/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ciuslo.app;

import com.ciuslo.app.vm.ArtikelVM;

import static spark.Spark.staticFileLocation;
import static spark.Spark.setIpAddress;
import static spark.Spark.setPort;



/**
 *
 * @author HP
 */
public class AppPlay {
    public static String ipAddress = "localhost";
    public static int portAddress = 2222;
    
    public final static void main(String[] args) {
        setPort(portAddress);
        setIpAddress(ipAddress);
        staticFileLocation("/public");
        
        ArtikelVM app = new ArtikelVM();
        app.action(ipAddress, portAddress);
    }
}
