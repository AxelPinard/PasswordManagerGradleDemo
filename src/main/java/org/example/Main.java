package org.example;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InvalidCipherTextException {

        int input = 0;

        while(input != 5) {

            //Options available for user
            System.out.println("Password Manager Menu");
            System.out.println("1. Create Password");
            System.out.println("2. Show Password");
            System.out.println("3. Hack Axel's Passwords :(");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            input = Integer.parseInt(System.console().readLine());
            System.out.println();

            //BouncyCastle Cryptography setup
            CBCBlockCipher blockCipher = new CBCBlockCipher(AESEngine.newInstance());
            PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(blockCipher);
            String xiv = "1234567891234567";
            byte[] iv =  xiv.getBytes("UTF-8");
            String keyString = "Olmy9iqsILwWblwe";
            KeyParameter keyParam = new KeyParameter(keyString.getBytes());
            ParametersWithIV keyParamWithIV = new ParametersWithIV(keyParam, iv,0,16);

            //1.Create Password
            if(input == 1) {
                System.out.print("Enter the name of the password: ");
                String PasswordName = System.console().readLine();
                System.out.print("Enter the password: ");
                String Password = System.console().readLine();

                //Encrypt the name and password and save to a file
                Encryption encrypt = new Encryption(cipher, keyParamWithIV,Password,Password.getBytes("UTF-8"), PasswordName);
            }

            //2.Retrieve Password
            if(input == 2) {
                //read in Password.txt and ask which one to decode.
                String jaja = "Passwords.txt";
                Scanner scanner = new Scanner(Paths.get(jaja));
                String[] Options = new String[10];
                for (int i = 0; i<Options.length; i++) {
                    if (scanner.hasNextLine()) {
                        String output = scanner.nextLine();
                        if (output == ""){
                            continue;
                        }
                        System.out.println(Integer.toString(i) + ": " + output);
                        output = output.split(" ")[1];
                        Options[i] = output;
                    }
                }
                System.out.print("\nEnter which password you want to access (by its number): ");
                int lookfor = Integer.parseInt(System.console().readLine());

                Decrypt decrypt = new Decrypt(cipher, keyParamWithIV, Options[lookfor]);
                input = 0;
            }

            //Hack Axel's Passwords that you can decrypt
            if(input == 3) {
                getAxelsPasswords nopleasedont = new getAxelsPasswords();
                String response = nopleasedont.run("https://raw.githubusercontent.com/AxelPinard/PasswordManagerMaven/refs/heads/master/AxelsPasswords.txt");
                FileWriter myWriter = new FileWriter("Passwords.txt",true);
                myWriter.append("\n" + response);
                myWriter.close();
            }
        }
    }
}