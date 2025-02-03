package org.example;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Base64;

public class Decrypt {

    public Decrypt(PaddedBufferedBlockCipher cipher, ParametersWithIV keyParamWithIV
            ,String choice) throws InvalidCipherTextException {
        cipher.init(false,keyParamWithIV);
        byte[] out2 = Base64.decode(choice);
        byte[] comparisonBytes = new byte[cipher.getOutputSize(out2.length)];
        int length = cipher.processBytes(out2, 0, out2.length, comparisonBytes, 0);
        cipher.doFinal(comparisonBytes,length);
        String s2 = new String(comparisonBytes);
        System.out.println("Password is: " + s2 + "\n");
    }
}
