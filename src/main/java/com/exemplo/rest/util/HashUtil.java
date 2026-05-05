package com.exemplo.rest.util;

public class HashUtil  {
    public static String hashToken(String token){
        try{
            var digest = java.security.MessageDigest.getInstance("SHA-256");
            var hash = digest.digest(token.getBytes());
            return java.util.Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing token", e);
        }
    }
}
