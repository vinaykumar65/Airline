package com.uniquedeveloper.registration;

//Utility class for generating OTP
public class OTPUtil {
 public static String generateOTP() {
     int randomPin = (int) (Math.random() * 900000) + 100000; // 6 digit random number
     return String.valueOf(randomPin);
 }
 
}
