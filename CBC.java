//William Dahl
//001273655
//ICSI 426 Cryptography
//Macrh 8th 2018

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

//CBC mode implimentaion
public class CBC extends AES{
	public static void main(String[] args) throws IOException{
		File input = new File(args[0]);
		File encrypted = new File("CBCencrypted.jpg");
		File decrypted = new File("CBCdecrypted.jpg");
		BufferedImage encryptedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		BufferedImage decryptedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		encryptedImage = ImageIO.read(input);
		decryptedImage = ImageIO.read(input);
		byte[] plain;
		byte[] cipher;
		byte[] key = {15, 1, 12, 6};
		byte[] iv = {2, 7, 10, 13}; // Intial vecor to start encryption
		for(int i=0;i<encryptedImage.getWidth();i++){
			for(int j=0;j<encryptedImage.getHeight();j++){
				int rgb = encryptedImage.getRGB(i,j);
				plain = intToByteArray(rgb);
				plain = xor_func(plain, iv); // xors the plaintext with the intial vector for encryption
				cipher = encrypt(plain, key);
				plain = decrypt(cipher, key);
				plain = xor_func(plain, iv); //xors the plaint text with the intial vector for decryption
				iv = cipher; // sets the cipher block to the intial vector for next encryption process
				int encryptedRGB = byteArrayToInt(cipher);
				int decryptedRGB = byteArrayToInt(plain);
				encryptedImage.setRGB(i, j, encryptedRGB);
				decryptedImage.setRGB(i, j, decryptedRGB);
			}
		}

		ImageIO.write(encryptedImage, "jpg", encrypted);
		ImageIO.write(decryptedImage, "jpg", decrypted);
	}

	public static final byte[] intToByteArray(int value) {
		return new byte[] { (byte)(value >>> 24), (byte)(value >>> 16), (byte)(value >>> 8), (byte)value};
	}

	public static final int byteArrayToInt(byte [] b) {
 		return (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8) + (b[3] & 0xFF);
	}
}