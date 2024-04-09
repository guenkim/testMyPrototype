package com.guen.converter;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {

    // 암호화 키 (실제 사용 시에는 더 안전한 방식으로 관리해야 함)
    private static final String SECRET_KEY = "MySecretKey12345";

    @Override
    public String convertToDatabaseColumn(String attribute) {
        // 데이터베이스에 저장할 때 수행되는 암호화 로직
        try {
            // 간단한 XOR 암호화를 사용한 예시
            byte[] encryptedBytes = xorEncrypt(attribute.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        // 데이터베이스에서 읽을 때 수행되는 복호화 로직
        try {
            // Base64 디코딩 후 XOR 복호화를 사용한 예시
            byte[] encryptedBytes = Base64.getDecoder().decode(dbData);
            byte[] decryptedBytes = xorEncrypt(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }

    private byte[] xorEncrypt(byte[] input) {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = (byte) (input[i] ^ keyBytes[i % keyBytes.length]);
        }
        return result;
    }
}