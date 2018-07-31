package com.asa.meta.rxhttp.utils;

import com.asa.meta.rxhttp.main.RxHttp;
import com.facebook.android.crypto.keychain.AndroidConceal;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.facebook.crypto.Entity;
import com.facebook.crypto.keychain.KeyChain;

public class EncryptUtil {
    // 加密
    public static byte[] encrypt(byte[] plainText) {
        if (plainText == null) {
            return null;
        }

        KeyChain keyChain = new SharedPrefsBackedKeyChain(RxHttp.getContext(), CryptoConfig.KEY_256);
        Crypto crypto = AndroidConceal.get().createDefaultCrypto(keyChain);

        if (!crypto.isAvailable()) {
            return plainText;
        }

        try {
            return crypto.encrypt(plainText, Entity.create("meta"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 解密
    public static byte[] decrypt(byte[] cipherText) {
        if (cipherText == null) {
            return null;
        }

        KeyChain keyChain = new SharedPrefsBackedKeyChain(RxHttp.getContext(), CryptoConfig.KEY_256);
        Crypto crypto = AndroidConceal.get().createDefaultCrypto(keyChain);

        if (!crypto.isAvailable()) {
            return cipherText;
        }

        try {
            byte[] plainText = crypto.decrypt(cipherText, Entity.create("meta"));
            return plainText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
