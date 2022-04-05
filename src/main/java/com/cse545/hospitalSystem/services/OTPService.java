package com.cse545.hospitalSystem.services;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.cache.LoadingCache;
import com.cse545.hospitalSystem.constants.HospitalSystemConstants;
import com.cse545.hospitalSystem.controllers.OTPController;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

@Service
public class OTPService {
    
    public static Logger logger = LoggerFactory.getLogger(OTPService.class);
    private static final Integer EXPIRE_MINS = HospitalSystemConstants.OTP_EXPIRES_IN;
    // Key-> user id, Val -> Integer that stores a 6 digit number
    private LoadingCache<String, Integer> cacheForOTP;
    
    public OTPService(){
        super();
        cacheForOTP = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }
    
    public int generateOTP(String key){
    	// generate OTP and store in Cache
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        cacheForOTP.put(key, otp);
        return otp;
    }
    
    public int getOtp(String key){
    	// check cache with key(email) to get the respective OTP
        try{
            return cacheForOTP.get(key);
        }catch (Exception e){
            return 0;
        }
    }
    
    public void clearOTP(String key){
        cacheForOTP.invalidate(key);
    }

    public boolean verifyOtp(String email, String otp) {
        //logger.info("email is {} otp is {}", email, otp);
        if(otp==null || otp.length()!=6) {
            return false;
        }
        int otpEntered = Integer.parseInt(otp);
        int otpStored = 0;
        try {
            otpStored = cacheForOTP.get(email);
            //logger.info("otp stored is {}", otpStored);
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(otpEntered!=otpStored) return false;
        
        return true;
        
    }

}
