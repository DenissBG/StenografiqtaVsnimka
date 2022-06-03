package main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

public class Main {
	public static void main(String[] args) {
		System.out.println("Vyvedi pytqt do .JPG snimkata naprimer C:\\Users\\Denis\\Desktop\\pic.jpg ");//pyt do snimkata C:\Users\Denis\Desktop\pic.jpg
		System.out.println("Path:");//pyt do snimkata C:\Users\Denis\Desktop\pic.jpg
		
		Scanner scanner = new Scanner(System.in);
		String pathname = scanner.nextLine();
		
		byte[] encryptedImageByte = null;// kriptriani bitove na snimkata
		int imageLength = 0;//daljina na snimkata
		
		try {
			BufferedImage bImage = ImageIO.read(new File(pathname));//chete snimkata
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			
			ImageIO.write(bImage, "jpg", byteArrayOutputStream);//zapisva
			encryptedImageByte = byteArrayOutputStream.toByteArray();
			
			imageLength = encryptedImageByte.length;//daljinata
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.print("Napishi tvoeto syobshtenie: ");
		String message = "";
		
		while(scanner.hasNextLine()) {//dokato ima sledvashta liniq
			message = scanner.nextLine();
			break;
		}
		
		byte[] messageBytes = message.getBytes();
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(encryptedImageByte.length + messageBytes.length);
		byteBuffer.put(encryptedImageByte);
		byteBuffer.put(messageBytes);
		
		byte[] resultantEncryptedMessage = byteBuffer.array();
		
		
		InputStream is = new ByteArrayInputStream(resultantEncryptedMessage);
		try {
			BufferedImage newEncryptImg = ImageIO.read(is);
			Path savePath = Paths.get("encryptedImg.jpg");// novata snimka
			ImageIO.write(newEncryptImg, "jpg", savePath.toFile());//zapisva tesksta v novata snimka
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Snimkata e kriptirana");
		System.out.println("Molq proveri kriptiranata snimka v papkata na proekta(naprimer C:\\Users\\Denis\\eclipse\\StenografiqSnimka\\src ) i obnovete proekta .");
		
		
		try {
			System.out.println("Dekreptirane procesa zapochna .... sled 5 sekundi shte se pokaje skritiqt tekst ");
			Thread.sleep(5000);// 5 sekundi zakysnenie 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// kod za dekriptirane 
		byte[] retraceMessageBytes = Arrays.copyOfRange(resultantEncryptedMessage, imageLength, resultantEncryptedMessage.length);
		String messageRetrace = new String(retraceMessageBytes);
		
		System.out.println(message);
	}
}
