/* Copyright (c) 2012 Google Inc.
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

package com.dome.sdkserver.bq.util.google;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Security-related methods. For a secure implementation, all of this code
 * should be implemented on a server that communicates with the
 * application on the device. For the sake of simplicity and clarity of this
 * example, this code is included here and is executed on the device. If you
 * must verify the purchases on the phone, you should obfuscate this code to
 * make it harder for an attacker to replace the code with stubs that treat all
 * purchases as verified.
 */
public class Security {
    private static final Log log = LogFactory.getLog(Security.class);
    private static final String TAG = "IABUtil/Security";

    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * Verifies that the data was signed with the given signature, and returns
     * the verified purchase. The data is in JSON format and signed
     * with a private key. The data also contains the {@link }
     * and product ID of the purchase.
     *
     * @param base64PublicKey the base64-encoded public key to use for verifying.
     * @param signedData      the signed JSON string (signed, not encrypted)
     * @param signature       the signature for the data, signed with the private key
     */
    public static boolean verifyPurchase(String base64PublicKey, String signedData, String signature) {
        if (StringUtils.isEmpty(signedData) || StringUtils.isEmpty(base64PublicKey) ||
                org.apache.commons.lang.StringUtils.isEmpty(signature)) {
            log.error("Purchase verification failed: missing data.");
            return false;
        }

        PublicKey key = Security.generatePublicKey(base64PublicKey);
        return Security.verify(key, signedData, signature);
    }

    /**
     * Generates a PublicKey instance from a string containing the
     * Base64-encoded public key.
     *
     * @param encodedPublicKey Base64-encoded public key
     * @throws IllegalArgumentException if encodedPublicKey is invalid
     */
    public static PublicKey generatePublicKey(String encodedPublicKey) {
        try {
//            byte[] decodedKey = Base64.decode(encodedPublicKey, Base64.DEFAULT);
            byte[] decodedKey = org.apache.commons.codec.binary.Base64.decodeBase64(encodedPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            log.error("Invalid key specification.");
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Verifies that the signature from the server matches the computed
     * signature on the data.  Returns true if the data is correctly signed.
     *
     * @param publicKey  public key associated with the developer account
     * @param signedData signed data from server
     * @param signature  server signature
     * @return true if the data and signature match
     */
    public static boolean verify(PublicKey publicKey, String signedData, String signature) {
        byte[] signatureBytes;
        try {
//            signatureBytes = Base64.decode(signature, Base64.DEFAULT);
            signatureBytes = org.apache.commons.codec.binary.Base64.decodeBase64(signature);
        } catch (IllegalArgumentException e) {
            log.error("Base64 decoding failed.");
            return false;
        }
        try {
            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initVerify(publicKey);
            sig.update(signedData.getBytes());
            if (!sig.verify(signatureBytes)) {
                log.error("Signature verification failed.");
                return false;
            }
            return true;
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException.");
        } catch (InvalidKeyException e) {
            log.error("Invalid key specification.");
        } catch (SignatureException e) {
            log.error("Signature exception.");
        }
        return false;
    }

    public static void main(String[] args) {

        String purchaseData = "{\"packageName\":\"com.sola.github.jarcomplexproject\",\"productId\":\"111111112fasdfasd\",\"purchaseTime\":1488509180194,\"purchaseState\":0,\"developerPayload\":\"extraData\",\"purchaseToken\":\"maannlndfbckdnofcgaejehh.AO-J1Oyl0W0IcBXI7hNz_70N0uW5eqZBxpDgkvkA0TRCjS7ltfvASu9fkewVvwZOxoiG1TL6TvF19-7cwglp6P9DlIMqSdRAkISoosoyinkZS1wjBLQ_7NI_HYe0lHTMk5Dc67HSNcO6kUVcCY6q-k0hZGQOYkxs1Q\"}";
        String dataSignature = "V2NyhlP/XX/OYPGYqXJSdgVRf9SY24C7lIUryaBwx46OPyS/A+m04yKFEiJz4n7/A8/lAShbCw1bWTDINUmxe1smzpEU+5lq9z2ElUS6djNWvStOCmIhKYRplaW4kC9IS4/aTxubsMI6QM+As4A23ahVuxsRldFvZ9ixiBBScqXHEMxR8IiLKBb4OxIaOr9wxvUoU5vvt+2LsR1tvG4yJiJTNKaIokPcwBonO2wqjHyEwkAkQAsYIvWax1xhfYEjfC9YcDdTdJrVl19scxA4Wd0B9vaMY6SgaQciLcafjxAa+OdnZVyOtABmT/gg5l/1tFo0jdT6iLNlVJ/i+X6HsA==";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnAny5UnbEJwCP6qugOqersedmO9nVCoeIstPhRuSuEc+6kwMggfHEIBk7jrniuYijlDIu9k9lzHef/JN/FZIiVi39JP+BGKsKsxEj6sDvl7oHZJYc5j6J6DICPEzOOnahn7giVWvsoPm5bJa+7zZwpeRzwap/nhgqW3/jODtagin/iaRrATms2/mnrGSxmO15G551SnzKGFXl/EVuh5edUDz8ngCIDpHx1kk3IASU/ulcUFbBtgtqCeapOQt7AOjzjlld4JMUYltjLLkUFNJGcCLSC1KZKh0bsBcFYsPB62U4+QOWV9lXPlWVPWTUFSLM/hffKeYvBgmKj6zSVnXDQIDAQAB";
        boolean result = Security.verify(generatePublicKey(publicKey), purchaseData, dataSignature);

        System.out.println(result);
    }
}
