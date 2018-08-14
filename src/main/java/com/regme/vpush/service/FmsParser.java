package com.regme.vpush.service;

import java.io.File;

/**
 * Created by admin on 13.08.2018.
 */
public interface FmsParser {

   File load();
   String unzip();
   String parse();
   String update();
}
