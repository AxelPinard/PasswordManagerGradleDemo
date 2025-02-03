package org.example;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Base64;
import java.io.FileWriter;
import java.io.IOException;

public class Encryption {

    public Encryption(PaddedBufferedBlockCipher cipher, ParametersWithIV keyParamWithIV
            ,String input, byte[] inputBytes, String inputName) throws InvalidCipherTextException, IOException {
        cipher.init(true, keyParamWithIV);
        byte[] outputBytes = new byte[cipher.getOutputSize(input.length())];
        int length = cipher.processBytes(inputBytes, 0, input.length(), outputBytes, 0);
        cipher.doFinal(outputBytes, length);
        String encryptedInput = new String(Base64.encode(outputBytes));

        FileWriter myWriter = new FileWriter("Passwords.txt",true);
        myWriter.append("\n" + inputName + ": " + encryptedInput);
        myWriter.close();
    }
}
