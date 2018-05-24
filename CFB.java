//William Dahl
//001273655
//ICSI 426 Cryptography
//March 8th 2017

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

//CFB mode implementation
public class CFB extends AES{
	public static void main(String[] args) throws IOException{
		File input = new File(args[0]);
		File encrypted = new File("CFBencrypted.jpg");
		File decrypted = new File("CFBdecrypted.jpg");
		BufferedImage encryptedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		BufferedImage decryptedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		encryptedImage = ImageIO.read(input);
		decryptedImage = ImageIO.read(input);
		byte[] plain;
		byte[] cipher;
		byte[] key = {15, 1, 12, 6};
		byte[] iv = {2, 7, 10, 13}; 
		byte[] s;// encrypted iv
		for(int i=0;i<encryptedImage.getWidth();i++){
			for(int j=0;j<encryptedImage.getHeight();j++){
				int rgb = encryptedImage.getRGB(i,j);
				plain = intToByteArray(rgb);
				s = encrypt(iv, key); // encrypts the iv and puts it into s
				cipher = xor_func(plain, s); // xors the plain text and the encrypted iv to get the cipher text
				plain = xor_func(cipher, s); // xors the cipher text and the encrpyted iv to get the plaint text
				iv = cipher; // makes the cipher text the new iv for the next encryption process
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