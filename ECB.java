//William Dahl
//001273655
//ICSI 426 Cryptogrpahy
//March 8th 2018

//Import the required modules
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

//ECB mode implemntation
public class ECB extends AES{
	public static void main(String[] args) throws IOException{
		File input = new File(args[0]);// turns the argument into a file object
		// creates new file objects for the encrypted and decrypted images
		File encrypted = new File("ECBencrypted.jpg");
		File decrypted = new File("ECBdecrypted.jpg");
		// creates BufferedImages for the encrpyted and decrypted images
		BufferedImage encryptedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		BufferedImage decryptedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		//reads the input image 
		encryptedImage = ImageIO.read(input);
		decryptedImage = ImageIO.read(input);
		byte[] plain;// plaintext block
		byte[] cipher; // cipher text block
		byte[] key = {15, 1, 12, 6};//encryption key
		//loops throught each pixle in the image
		for(int i=0;i<encryptedImage.getWidth();i++){
			for(int j=0;j<encryptedImage.getHeight();j++){
				int rgb = encryptedImage.getRGB(i,j);// gets the integer representation of the current pixle
				plain = intToByteArray(rgb); // turns the interger representation of the pixle to a byte array
				cipher = encrypt(plain, key); // encrypts the plaintext 
				plain = decrypt(cipher, key); //decrypts the cipher text
				//changes the byte block into an integer to be repersente for the RGB color
				int encryptedRGB = byteArrayToInt(cipher); 
				int decryptedRGB = byteArrayToInt(plain);
				//sets the new color at the pixle location
				encryptedImage.setRGB(i, j, encryptedRGB);
				decryptedImage.setRGB(i, j, decryptedRGB);
			}
		}

		//writes the data to a new image
		ImageIO.write(encryptedImage, "jpg", encrypted);
		ImageIO.write(decryptedImage, "jpg", decrypted);
	}

	//turns an integer value to a byte array
	//@parama: value - int vlaue to be turn to byte array
	//@return: byte array 
	public static final byte[] intToByteArray(int value) {
		return new byte[] { (byte)(value >>> 24), (byte)(value >>> 16), (byte)(value >>> 8), (byte)value};
	}

	//turns a byte array to an integer value
	//@parama: b = byte array to be turned into integer value
	//@retrun: int 
	public static final int byteArrayToInt(byte [] b) {
 		return (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8) + (b[3] & 0xFF);
	}
}