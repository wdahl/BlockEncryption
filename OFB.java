//William Dahl
//ICSI 426 Cryptography
//001273655
//March 8th 2018

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

//OFB mode implementation
public class OFB extends AES{
	public static void main(String[] args) throws IOException{
		File input = new File(args[0]);
		File encrypted = new File("OFBencrypted.jpg");
		File decrypted = new File("OFBdecrypted.jpg");
		BufferedImage encryptedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		BufferedImage decryptedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		encryptedImage = ImageIO.read(input);
		decryptedImage = ImageIO.read(input);
		byte[] plain;
		byte[] cipher;
		byte[] key = {15, 1, 12, 6};
		byte[] iv = {2, 7, 10, 13}; 
		for(int i=0;i<encryptedImage.getWidth();i++){
			for(int j=0;j<encryptedImage.getHeight();j++){
				int rgb = encryptedImage.getRGB(i,j);
				plain = intToByteArray(rgb);
				iv = encrypt(iv, key); // encrpts the iv for the next encryption process
				cipher = xor_func(plain, iv); // xors the plain text and the encrypted iv to get the cipher text
				plain = xor_func(cipher, iv); // xors the cipher text and the encrypted iv to get the plain text
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