/**
 * $RCSfile$
 * $Revision: 2814 $
 * $Date: 2005-09-13 16:41:10 -0300 (Tue, 13 Sep 2005) $
 *
 * Copyright (C) 2004-2008 Jive Software. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yealink.uc.platform.crypto;

import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.platform.exception.BusinessHandleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.yealink.dataservice.client.util.Filter.eq;

/**
 * Copy from openfire
 * <p/>
 * Pluggable authentication service. Users of Openfire that wish to change the AuthProvider
 * implementation used to authenticate users can set the <code>AuthProvider.className</code>
 * system property. For example, if you have configured Openfire to use LDAP for user information,
 * you'd want to send a custom implementation of AuthFactory to make LDAP auth queries.
 * After changing the <code>AuthProvider.className</code> system property, you must restart your
 * application server.
 *
 * @author Matt Tucker
 */
public class AuthFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFactory.class);

    private static MessageDigest digest;
    private static BlowFish cipher = null;
    private static RemoteServiceFactory remoteServiceFactory = RemoteServiceFactory.getInstance();

    static {
        try {
            digest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
        }
    }


    public static String encryptPassword(String password) {
        if (password == null) {
            return null;
        }
        BlowFish cipher = getCipher();
        if (cipher == null) {
            throw new UnsupportedOperationException();
        }
        return cipher.encryptString(password);
    }

    public static String decryptPassword(String password) {
        if (password == null) {
            return null;
        }
        BlowFish cipher = getCipher();
        if (cipher == null) {
            throw new UnsupportedOperationException();
        }
        return cipher.decryptString(password);
    }

    /**
     * Returns a Blowfish cipher that can be used for encrypting and decrypting passwords.
     * The encryption key is stored as the Jive property "passwordKey". If it's not present,
     * it will be automatically generated.
     *
     * @return the Blowfish cipher, or <tt>null</tt> if Openfire is not able to create a Cipher;
     * for example, during setup mode.
     */
    private static synchronized BlowFish getCipher() {
        if (cipher != null) {
            return cipher;
        }
        // Get the password key, stored as a database property. Obviously,
        // protecting your database is critical for making the
        // encryption fully secure.
        try {
            Map<String, Object> p = remoteServiceFactory.getRemoteDataService().queryOne("Property", null, eq("_id", "passwordKey").toMap());
            if (p == null) throw new BusinessHandleException("system.password.key.error");
            String keyString = p.get("propValue").toString();
            cipher = new BlowFish(keyString);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return cipher;
    }

}