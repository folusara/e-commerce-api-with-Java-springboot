package com.example.myEcommerceAPI.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CloudinaryConfig {

   @Bean
   @Lazy
   public Cloudinary cloudinary() {
        Dotenv dotenv = Dotenv.load();
		Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
		System.out.println(cloudinary.config.cloudName);
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudinary.config.cloudName,
                "api_key", cloudinary.config.apiKey,
                "api_secret", cloudinary.config.apiSecret
        ));
    } 
}
